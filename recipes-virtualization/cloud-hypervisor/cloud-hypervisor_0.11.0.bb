SUMMARY = "Open source Virtual Machine Monitor (VMM) that runs on top of KVM"
HOMEPAGE = "https://github.com/cloud-hypervisor/cloud-hypervisor"
LICENSE = "Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE-APACHE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
		file://LICENSE-BSD-3-Clause;md5=b1ed361f9fc790c1054d81a7ef041a34"

inherit cargo

# If this is git based prefer versioned ones if they exist
# DEFAULT_PREFERENCE = "-1"

# how to get cloud-hypervisor could be as easy as but default to a git checkout:
# SRC_URI += "crate://crates.io/cloud-hypervisor/0.11.0"
SRC_URI += "git://github.com/cloud-hypervisor/cloud-hypervisor.git;protocol=ssh;nobranch=1"
SRCREV = "7dcd3aff05d6d70b73f3804cfb4d588a631172c0"
S = "${WORKDIR}/git"
CARGO_SRC_DIR = ""

SRC_URI += " \
    crate://crates.io/addr2line/0.13.0 \
    crate://crates.io/adler/0.2.3 \
    crate://crates.io/aho-corasick/0.7.14 \
    crate://crates.io/ansi_term/0.11.0 \
    crate://crates.io/anyhow/1.0.33 \
    crate://crates.io/arc-swap/0.4.7 \
    crate://crates.io/arrayref/0.3.6 \
    crate://crates.io/arrayvec/0.5.2 \
    crate://crates.io/atty/0.2.14 \
    crate://crates.io/autocfg/1.0.1 \
    crate://crates.io/backtrace/0.3.53 \
    crate://crates.io/base64/0.12.3 \
    crate://crates.io/bitflags/0.5.0 \
    crate://crates.io/bitflags/1.2.1 \
    crate://crates.io/blake2b_simd/0.5.10 \
    crate://crates.io/byteorder/1.3.4 \
    crate://crates.io/cc/1.0.61 \
    crate://crates.io/cfg-if/0.1.10 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/clap/2.33.3 \
    crate://crates.io/cloudabi/0.0.3 \
    crate://crates.io/constant_time_eq/0.1.5 \
    crate://crates.io/credibility/0.1.3 \
    crate://crates.io/crossbeam-utils/0.7.2 \
    crate://crates.io/dirs-sys/0.3.5 \
    crate://crates.io/dirs/3.0.1 \
    crate://crates.io/epoll/4.3.1 \
    crate://crates.io/failure/0.1.8 \
    crate://crates.io/failure_derive/0.1.8 \
    crate://crates.io/fuchsia-cprng/0.1.1 \
    crate://crates.io/futures-channel/0.3.7 \
    crate://crates.io/futures-core/0.3.7 \
    crate://crates.io/futures-executor/0.3.7 \
    crate://crates.io/futures-io/0.3.7 \
    crate://crates.io/futures-macro/0.3.7 \
    crate://crates.io/futures-sink/0.3.7 \
    crate://crates.io/futures-task/0.3.7 \
    crate://crates.io/futures-util/0.3.7 \
    crate://crates.io/futures/0.3.7 \
    crate://crates.io/getrandom/0.1.15 \
    crate://crates.io/gimli/0.22.0 \
    crate://crates.io/glob/0.2.11 \
    crate://crates.io/hermit-abi/0.1.17 \
    crate://crates.io/idna/0.2.0 \
    crate://crates.io/io-uring/0.4.0 \
    crate://crates.io/ipnetwork/0.16.0 \
    crate://crates.io/itoa/0.4.6 \
    crate://crates.io/kernel32-sys/0.2.2 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/libc/0.2.80 \
    crate://crates.io/libssh2-sys/0.2.19 \
    crate://crates.io/libz-sys/1.1.2 \
    crate://crates.io/lock_api/0.3.4 \
    crate://crates.io/log/0.3.9 \
    crate://crates.io/log/0.4.11 \
    crate://crates.io/matches/0.1.8 \
    crate://crates.io/memchr/2.3.4 \
    crate://crates.io/miniz_oxide/0.4.3 \
    crate://crates.io/num_cpus/1.13.0 \
    crate://crates.io/object/0.21.1 \
    crate://crates.io/once_cell/1.4.1 \
    crate://crates.io/openssl-sys/0.9.58 \
    crate://crates.io/parking_lot/0.10.2 \
    crate://crates.io/parking_lot_core/0.7.2 \
    crate://crates.io/percent-encoding/2.1.0 \
    crate://crates.io/pin-project-internal/1.0.1 \
    crate://crates.io/pin-project/1.0.1 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/pkg-config/0.3.19 \
    crate://crates.io/pnet/0.26.0 \
    crate://crates.io/pnet_base/0.26.0 \
    crate://crates.io/pnet_datalink/0.26.0 \
    crate://crates.io/pnet_macros/0.26.0 \
    crate://crates.io/pnet_macros_support/0.26.0 \
    crate://crates.io/pnet_packet/0.26.0 \
    crate://crates.io/pnet_sys/0.26.0 \
    crate://crates.io/pnet_transport/0.26.0 \
    crate://crates.io/ppv-lite86/0.2.9 \
    crate://crates.io/proc-macro-hack/0.5.19 \
    crate://crates.io/proc-macro-nested/0.1.6 \
    crate://crates.io/proc-macro2/1.0.24 \
    crate://crates.io/quote/1.0.7 \
    crate://crates.io/rand/0.4.6 \
    crate://crates.io/rand/0.7.3 \
    crate://crates.io/rand_chacha/0.2.2 \
    crate://crates.io/rand_core/0.3.1 \
    crate://crates.io/rand_core/0.4.2 \
    crate://crates.io/rand_core/0.5.1 \
    crate://crates.io/rand_hc/0.2.0 \
    crate://crates.io/rdrand/0.4.0 \
    crate://crates.io/redox_syscall/0.1.57 \
    crate://crates.io/redox_users/0.3.5 \
    crate://crates.io/regex-syntax/0.6.20 \
    crate://crates.io/regex/1.4.1 \
    crate://crates.io/remain/0.2.2 \
    crate://crates.io/remove_dir_all/0.5.3 \
    crate://crates.io/rust-argon2/0.8.2 \
    crate://crates.io/rustc-demangle/0.1.18 \
    crate://crates.io/rustc-serialize/0.3.24 \
    crate://crates.io/ryu/1.0.5 \
    crate://crates.io/scopeguard/1.1.0 \
    crate://crates.io/serde/1.0.117 \
    crate://crates.io/serde_derive/1.0.117 \
    crate://crates.io/serde_json/1.0.59 \
    crate://crates.io/signal-hook-registry/1.2.1 \
    crate://crates.io/signal-hook/0.1.16 \
    crate://crates.io/slab/0.4.2 \
    crate://crates.io/smallvec/1.4.2 \
    crate://crates.io/ssh2/0.8.2 \
    crate://crates.io/strsim/0.8.0 \
    crate://crates.io/syn/1.0.48 \
    crate://crates.io/synstructure/0.12.4 \
    crate://crates.io/syntex/0.42.2 \
    crate://crates.io/syntex_errors/0.42.0 \
    crate://crates.io/syntex_pos/0.42.0 \
    crate://crates.io/syntex_syntax/0.42.0 \
    crate://crates.io/tempdir/0.3.7 \
    crate://crates.io/tempfile/3.1.0 \
    crate://crates.io/term/0.4.6 \
    crate://crates.io/term_size/0.3.2 \
    crate://crates.io/textwrap/0.11.0 \
    crate://crates.io/thiserror-impl/1.0.21 \
    crate://crates.io/thiserror/1.0.21 \
    crate://crates.io/thread_local/1.0.1 \
    crate://crates.io/tinyvec/0.3.4 \
    crate://crates.io/unicode-bidi/0.3.4 \
    crate://crates.io/unicode-normalization/0.1.13 \
    crate://crates.io/unicode-width/0.1.8 \
    crate://crates.io/unicode-xid/0.0.3 \
    crate://crates.io/unicode-xid/0.2.1 \
    crate://crates.io/url/2.1.1 \
    crate://crates.io/vcpkg/0.2.10 \
    crate://crates.io/vec_map/0.8.2 \
    crate://crates.io/vfio-bindings/0.2.0 \
    crate://crates.io/virtio-bindings/0.1.0 \
    crate://crates.io/vmm-sys-util/0.6.1 \
    crate://crates.io/wait-timeout/0.2.0 \
    crate://crates.io/wasi/0.9.0+wasi-snapshot-preview1 \
    crate://crates.io/winapi-build/0.1.1 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.2.8 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/ws2_32-sys/0.2.1 \
    crate://crates.io/vm-memory/0.3.0 \
    git://github.com/cloud-hypervisor/kvm-bindings;protocol=https;nobranch=1;name=kvm-bindings;destsuffix=kvm-bindings \
    git://github.com/cloud-hypervisor/kvm-ioctls;protocol=https;nobranch=1;name=kvm-ioctls;destsuffix=kvm-ioctls \
    git://github.com/cloud-hypervisor/vfio-ioctls;protocol=https;nobranch=1;name=vfio-ioctls;destsuffix=vfio-ioctls \
    git://github.com/cloud-hypervisor/vhost;protocol=https;nobranch=1;name=vhost;destsuffix=vhost \
    git://github.com/cloud-hypervisor/vm-memory;protocol=https;nobranch=1;name=vm-memory;destsuffix=vm-memory \
    git://github.com/firecracker-microvm/firecracker;protocol=https;nobranch=1;name=seccomp;destsuffix=seccomp;subpath=src/seccomp \
    git://github.com/firecracker-microvm/micro-http;protocol=https;nobranch=1;name=micro_http;destsuffix=micro_http \
    git://github.com/rust-vmm/linux-loader;protocol=https;nobranch=1;name=linux-loader;destsuffix=linux-loader \
    file://deps-${PV}.patch \
    file://config-${PV} \
"

# NOTE vm-memory crate is downloaded, but it's source is overridden to the github checkout. Keep in mind for the upgrades.

SRCREV_FORMAT .= "_kvm-bindings"
SRCREV_kvm-bindings = "2164129ff03bb4d7a4243de5e2078845064d922c"
SRCREV_FORMAT .= "_kvm-ioctls"
SRCREV_kvm-ioctls = "64427395b60e00403aed3026362a1b19873230e6"
SRCREV_FORMAT .= "_linux-loader"
SRCREV_linux-loader = "3cf96c53ed6bfed9c7ca7dcfdbbcd04c86b6dd09"
SRCREV_FORMAT .= "_micro_http"
SRCREV_micro_http = "59ab64440a95f7b16253fef67b5201c2ec4adaf7"
SRCREV_FORMAT .= "_seccomp"
SRCREV_seccomp = "b68a78beabed1c2417acfe73e63df0ef7916f0cc"
SRCREV_FORMAT .= "_vfio-ioctls"
SRCREV_vfio-ioctls = "49cc3626f6787e2075493a511675c6851a13bbc0"
SRCREV_FORMAT .= "_vhost"
SRCREV_vhost = "6fc980bf5083d67586ab91d6a965c3a593c33a78"
SRCREV_FORMAT .= "_vm-memory"
SRCREV_vm-memory = "6f2f7107f29a8f2b66b64a849aeace20783ec8f3"

CARGO_BUILD_FLAGS_append += "${@bb.utils.contains('BB_NO_NETWORK', '1', '--offline', '', d)}"

do_compile_prepend() {
	if [ ! -d ${S}/.cargo ]; then  mkdir -p ${S}/.cargo; fi
	cat "${WORKDIR}/config-${PV}" | sed "s,VENDOR_PATH,${WORKDIR},g" > "${S}/.cargo/config"

	echo '{"files":{},"package":null}' > "${S}/.cargo-checksum.json"
	for DEP in kvm-bindings kvm-ioctls linux-loader micro_http seccomp vfio-ioctls vhost vm-memory; do
		echo '{"files":{},"package":null}' > "${WORKDIR}/${DEP}/.cargo-checksum.json"
	done 
}
