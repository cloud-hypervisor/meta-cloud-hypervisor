
require ovmf-ch.inc

do_compile:append_class-target() {
    ${S}/OvmfPkg/build.sh $PARALLEL_JOBS -a $OVMF_ARCH -b ${EDK_BUILD_TYPE} -t ${EDK_GCCVER} -p OvmfPkg/CloudHv/CloudHvX64.dsc
}

do_install_class-target() {
    OVMF_TGT_DIR=${D}/usr/share/cloud-hypervisor

    install -d ${OVMF_TGT_DIR}
    install -m 644 ${S}/Build/CloudHvX64/${EDK_BUILD_TYPE}_${EDK_GCCVER}/FV/CLOUDHV.fd ${OVMF_TGT_DIR}
    install -m 644 ${S}/Build/CloudHvX64/${EDK_BUILD_TYPE}_${EDK_GCCVER}/FV/CLOUDHV_CODE.fd ${OVMF_TGT_DIR}
    install -m 644 ${S}/Build/CloudHvX64/${EDK_BUILD_TYPE}_${EDK_GCCVER}/FV/CLOUDHV_VARS.fd ${OVMF_TGT_DIR}
}

