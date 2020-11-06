DESCRIPTION = "SeaBIOS"
HOMEPAGE = "http://www.coreboot.org/SeaBIOS"
LICENSE = "LGPLv3"
SECTION = "firmware"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit python3native

SRC_URI = " \
    git://github.com/cloud-hypervisor/seabios.git;branch=ch;protocol=https \
    file://defconfig \
    file://hostcc.patch \
    file://python3.patch \
    "
S = "${WORKDIR}/git"

SRCREV="3fcece5c0eb5135630278cbee23686397a916d36"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504         \
                    file://COPYING.LESSER;md5=6a6a8e020838b23406c81b19c1d46df6  \
                    "

SRC_URI[md5sum] = "1dc1725bac1d230bfd6b3204eed4f2f7"
SRC_URI[sha256sum] = "37673dc2d6308591b15bdb94e5bcc3e99bdb40198d2247733c43f50b55dbe703"

FILES_${PN} = "/usr/share/firmware/cloud-hypervisor/csm/bios.bin"
FILES_${PN}-dev = "/usr/share/firmware/cloud-hypervisor/csm/Csm16.bin"

DEPENDS += "util-linux-native file-native bison-native flex-native gettext-native acpica-native"

TUNE_CCARGS = ""
EXTRA_OEMAKE += "HOSTCC='${BUILD_CC}'"
EXTRA_OEMAKE += "CROSS_PREFIX=${TARGET_PREFIX}"
EXTRA_OEMAKE += "CONFIG_CSM=y CONFIG_QEMU_HARDWARE=y"

COMPATIBLE_HOST = "(i.86|x86_64).*-linux"

do_configure() {
    cp ${WORKDIR}/defconfig ${S}/.config
#    oe_runmake defconfig
}

do_compile() {
    unset CPP
    unset CPPFLAGS
    oe_runmake
}

do_install() {
    oe_runmake
    install -d ${D}/usr/share/firmware/cloud-hypervisor/csm
    install -m 0644 out/bios.bin ${D}/usr/share/firmware/cloud-hypervisor/csm/
    install -m 0644 out/Csm16.bin ${D}/usr/share/firmware/cloud-hypervisor/csm/
}

