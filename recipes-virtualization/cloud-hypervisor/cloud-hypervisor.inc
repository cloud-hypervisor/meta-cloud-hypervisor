
CARGO_BUILD_FLAGS:append += "${@bb.utils.contains('BB_NO_NETWORK', '1', '--offline', '', d)}"

do_compile:prepend() {
	if [ ! -d ${S}/.cargo ]; then  mkdir -p ${S}/.cargo; fi
	cat "${WORKDIR}/config-${PV}" | sed "s,VENDOR_PATH,${WORKDIR},g" > "${S}/.cargo/config"

	echo '{"files":{},"package":null}' > "${S}/.cargo-checksum.json"
	for DEP in kvm-bindings micro_http seccomp vfio-ioctls vhost mshv-bindings mshv-ioctls; do
		echo '{"files":{},"package":null}' > "${WORKDIR}/${DEP}/.cargo-checksum.json"
	done 
}

