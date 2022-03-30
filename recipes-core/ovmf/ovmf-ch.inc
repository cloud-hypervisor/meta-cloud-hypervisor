SUMMARY = "UEFI firmware for Cloud Hypervisor"
DESCRIPTION ?= "OVMF is an EDK II based project to enable UEFI support for \
Virtual Machines. This package produces UEFI firmware for Cloud Hypervisor."
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://OvmfPkg/License.txt;md5=06357ddc23f46577c2aeaeaf7b776d65"

# NOTE This recipe is based on the upstream ovmf_git.bb and patches from Dunfell. The main difference
#  - Pulling the patched source suitable for CH
#  - Secure boot build is cut off for now, as it's not supported yet
#  - EFI image build is cut off
#  - The target firmware is installed under /usr/share/cloud-hypervisor

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/cloud-hypervisor/edk2.git;branch=ch;name=edk2;protocol=git \
	git://github.com/openssl/openssl;branch=OpenSSL_1_1_1-stable;protocol=git;name=openssl;destsuffix=git/CryptoPkg/Library/OpensslLib/openssl \
	git://github.com/ucb-bar/berkeley-softfloat-3.git;protocol=git;name=softfloat;destsuffix=git/ArmPkg/Library/ArmSoftFloatLib/berkeley-softfloat-3 \
	git://git.cryptomilk.org/projects/cmocka.git;protocol=git;name=cmocka;destsuffix=git/UnitTestFrameworkPkg/Library/CmockaLib/cmocka \
	git://github.com/kkos/oniguruma;protocol=git;name=oniguruma;destsuffix=git/MdeModulePkg/Universal/RegularExpressionDxe/oniguruma \
        git://github.com/google/brotli.git;protocol=git;name=brotli;destsuffix=git/MdeModulePkg/Library/BrotliCustomDecompressLib/brotli \
        git://github.com/google/brotli.git;protocol=git;name=brotli;destsuffix=git/BaseTools/Source/C/BrotliCompress/brotli \
        git://github.com/akheron/jansson;protocol=git;name=jansson;destsuffix=git/RedfishPkg/Library/JsonLib/jansson \
	file://0001-ovmf-update-path-to-native-BaseTools.patch  \
	file://0002-BaseTools-makefile-adjust-to-build-in-under-bitbake.patch \
	file://0003-ovmf-enable-long-path-file.patch \
	"

PV = "20210206.0"
SRCREV_FORMAT .= "_edk2"
SRCREV:edk2_pn-${PN} = "9f1d3f90efab37f09647a91e9b0d30bb4d1c3f61"
SRCREV_FORMAT .= "_openssl"
SRCREV_openssl_pn-${PN} = "869e1424438b3c117d6ef4d3fbf4545cc7dab171"
SRCREV_FORMAT .= "_softfloat"
SRCREV_softfloat_pn-${PN} = "b64af41c3276f97f0e181920400ee056b9c88037"
SRCREV_FORMAT .= "_cmocka"
SRCREV_cmocka_pn-${PN} = "1cc9cde3448cdd2e000886a26acf1caac2db7cf1"
SRCREV_FORMAT .= "_oniguruma"
SRCREV_oniguruma_pn-${PN} = "v6.9.7.1"
SRCREV_FORMAT .= "_brotli"
SRCREV_brotli_pn-${PN} = "v1.0.7"
SRCREV_FORMAT .= "_jansson"
SRCREV_jansson_pn-${PN} = "2.13"

PARALLEL_MAKE = ""

S = "${WORKDIR}/git"

DEPENDS = "nasm-native acpica-native ovmf-ch-native util-linux-native"

EDK_TOOLS_DIR="edk2_basetools"

# OVMF has trouble building with the default optimization of -O2.
BUILD_OPTIMIZATION="-pipe"

# OVMF supports IA only, although it could conceivably support ARM someday.
COMPATIBLE_HOST='(i.86|x86_64).*'

EDK_GCCVER = "GCC5"
EDK_BUILD_TYPE = "RELEASE"

export PYTHON_COMMAND = "${HOSTTOOLS_DIR}/python3"

do_patch[postfuncs] += "fix_basetools_location"
fix_basetools_location () {
}
fix_basetools_location_class-target() {
    # Replaces the fake path inserted by 0002-ovmf-update-path-to-native-BaseTools.patch.
    # Necessary for finding the actual BaseTools from ovmf-native.
    sed -i -e 's#BBAKE_EDK_TOOLS_PATH#${STAGING_BINDIR_NATIVE}/${EDK_TOOLS_DIR}#' ${S}/OvmfPkg/build.sh
}

do_patch[postfuncs] += "fix_iasl"
fix_iasl() {
}
fix_iasl_class-native() {
    # iasl is not installed under /usr/bin when building with OE.
    sed -i -e 's#/usr/bin/iasl#${STAGING_BINDIR_NATIVE}/iasl#' ${S}/BaseTools/Conf/tools_def.template
}

# Inject CC and friends into the build. LINKER already is in GNUmakefile.
# Must be idempotent and thus remove old assignments that were inserted
# earlier.
do_patch[postfuncs] += "fix_toolchain"
fix_toolchain() {
    sed -i \
        -e '/^\(CC\|CXX\|AS\|AR\|LD\|LINKER\) =/d' \
        -e '/^APPLICATION/a CC = ${CC}\nCXX = ${CXX}\nAS = ${AS}\nAR = ${AR}\nLD = ${LD}\nLINKER = $(CC)' \
        ${S}/BaseTools/Source/C/Makefiles/app.makefile
    sed -i \
        -e '/^\(CC\|CXX\|AS\|AR\|LD\)/d' \
        -e '/^VFR_CPPFLAGS/a CC = ${CC}\nCXX = ${CXX}\nAS = ${AS}\nAR = ${AR}\nLD = ${LD}' \
        ${S}/BaseTools/Source/C/VfrCompile/GNUmakefile
}
fix_toolchain:append_class-native() {
    # This tools_def.template is going to be used by the target ovmf and
    # defines which compilers to use. For the GCC toolchain definitions,
    # that will be ${HOST_PREFIX}gcc. However, "make" doesn't need that
    # prefix.
    #
    # Injecting ENV(HOST_PREFIX) matches exporting that value as env
    # variable in do_compile_class-target.
    sed -i \
        -e 's#\(ENV\|DEF\)(GCC.*_PREFIX)#ENV(HOST_PREFIX)#' \
        -e 's#ENV(HOST_PREFIX)make#make#' \
        ${S}/BaseTools/Conf/tools_def.template
    sed -i \
        -e '/^\(LFLAGS\|CFLAGS\) +=/d' \
        -e '/^LINKER/a LFLAGS += ${BUILD_LDFLAGS}\nCFLAGS += ${BUILD_CFLAGS}' \
        ${S}/BaseTools/Source/C/Makefiles/app.makefile \
        ${S}/BaseTools/Source/C/VfrCompile/GNUmakefile
    # Linking with gold fails:
    # internal error in do_layout, at ../../gold/object.cc:1821
    # make: *** [.../OUTPUT/Facs.acpi] Error 1
    # We intentionally hard-code the use of ld.bfd regardless of DISTRO_FEATURES
    # to make ovmf-native reusable across distros.
    sed -i \
        -e 's#^\(DEFINE GCC.*DLINK.*FLAGS  *=\)#\1 -fuse-ld=bfd#' \
        ${S}/BaseTools/Conf/tools_def.template
}

do_compile_class-native() {
    oe_runmake -C ${S}/BaseTools
}

do_compile_class-target() {
    export LFLAGS="${LDFLAGS}"
    PARALLEL_JOBS="${@oe.utils.parallel_make_argument(d, '-n %d')}"
    OVMF_ARCH="X64"

    # The build for the target uses BaseTools/Conf/tools_def.template
    # from ovmf-native to find the compiler, which depends on
    # exporting HOST_PREFIX.
    export HOST_PREFIX="${HOST_PREFIX}"

    # BaseTools/Conf gets copied to Conf, but only if that does not
    # exist yet. To ensure that an updated template gets used during
    # incremental builds, we need to remove the copy before we start.
    rm -f `ls ${S}/Conf/*.txt | grep -v ReadMe.txt`
}

do_install_class-native() {
    install -d ${D}/${bindir}/${EDK_TOOLS_DIR}
    cp -r ${S}/BaseTools ${D}/${bindir}/${EDK_TOOLS_DIR}
}

FILES_${PN} = "/usr/share/cloud-hypervisor"
INSANE_SKIP_${PN} += "arch"

BBCLASSEXTEND = "native"
TOOLCHAIN = "gcc"
