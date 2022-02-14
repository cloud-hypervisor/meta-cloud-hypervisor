
CARGO_BUILD_FLAGS_append += "${@bb.utils.contains('BB_NO_NETWORK', '1', '--offline', '', d)}"

do_compile_prepend() {
	if [ ! -d ${S}/.cargo ]; then  mkdir -p ${S}/.cargo; fi
	cat "${WORKDIR}/config-${PV}" | sed "s,VENDOR_PATH,${WORKDIR},g" > "${S}/.cargo/config"

	echo '{"files":{},"package":null}' > "${S}/.cargo-checksum.json"
	for DEP in kvm-bindings kvm-ioctls vfio-ioctls versionize versionize_derive micro_http mshv-bindings mshv-ioctls vm-fdt; do
		echo '{"files":{},"package":null}' > "${WORKDIR}/${DEP}/.cargo-checksum.json"
	done 
}

