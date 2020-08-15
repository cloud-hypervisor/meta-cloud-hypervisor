SUMMARY = "UEFI firmware for Cloud Hypervisor"
DESCRIPTION = "OVMF is an EDK II based project to enable UEFI support for \
Virtual Machines. This package produces UEFI firmware for Cloud Hypervisor."
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://OvmfPkg/License.txt;md5=06357ddc23f46577c2aeaeaf7b776d65"

# NOTE This recipe is based on the upstream ovmf_git.bb and patches from Dunfell. The main difference
#  - Pulling the patched source suitable for CH
#  - Secure boot build is cut off for now, as it's not supported yet
#  - EFI image build is cut off
#  - The target firmware is installed under /usr/share/cloud-hypervisor

SRC_URI = "gitsm://github.com/cloud-hypervisor/edk2.git;branch=ch;protocol=git \
           file://0001-ovmf-update-path-to-native-BaseTools.patch  \
           file://0002-BaseTools-makefile-adjust-to-build-in-under-bitbake.patch \
           file://0003-ovmf-enable-long-path-file.patch \
           file://0004-ovmf-Update-to-latest.patch \
           "

PV = "20202906.0"
SRCREV = "9071987a5ab702d2bbe7d7be51312256bd11b34e"

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
fix_toolchain_append_class-native() {
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

    ${S}/OvmfPkg/build.sh $PARALLEL_JOBS -a $OVMF_ARCH -b ${EDK_BUILD_TYPE} -t ${EDK_GCCVER} -p OvmfPkg/OvmfCh.dsc
}

do_install_class-native() {
    install -d ${D}/${bindir}/${EDK_TOOLS_DIR}
    cp -r ${S}/BaseTools ${D}/${bindir}/${EDK_TOOLS_DIR}
}

do_install_class-target() {
    OVMF_TGT_DIR=${D}/usr/share/cloud-hypervisor

    install -d ${OVMF_TGT_DIR}
    install -m 644 ${S}/Build/OvmfCh/${EDK_BUILD_TYPE}_${EDK_GCCVER}/FV/OVMF.fd ${OVMF_TGT_DIR}
    install -m 644 ${S}/Build/OvmfCh/${EDK_BUILD_TYPE}_${EDK_GCCVER}/FV/OVMF_CODE.fd ${OVMF_TGT_DIR}
    install -m 644 ${S}/Build/OvmfCh/${EDK_BUILD_TYPE}_${EDK_GCCVER}/FV/OVMF_VARS.fd ${OVMF_TGT_DIR}
}

FILES_${PN} = "/usr/share/cloud-hypervisor"
INSANE_SKIP_${PN} += "arch"

BBCLASSEXTEND = "native"
TOOLCHAIN = "gcc"
