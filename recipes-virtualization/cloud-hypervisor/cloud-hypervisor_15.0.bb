# Auto-Generated by cargo-bitbake 0.3.16-alpha.0
#
inherit cargo

# If this is git based prefer versioned ones if they exist
# DEFAULT_PREFERENCE = "-1"

# how to get cloud-hypervisor could be as easy as but default to a git checkout:
# SRC_URI += "crate://crates.io/cloud-hypervisor/15.0.0"
SRC_URI += "git://github.com/cloud-hypervisor/cloud-hypervisor.git;protocol=ssh;nobranch=1"
SRCREV = "30a01277655bf943a5ccdab67d9d398b0335d669"
S = "${WORKDIR}/git"
CARGO_SRC_DIR = ""


# please note if you have entries that do not begin with crate://
# you must change them to how that package can be fetched
SRC_URI += " \
    crate://crates.io/addr2line/0.14.1 \
    crate://crates.io/adler/1.0.2 \
    crate://crates.io/aho-corasick/0.7.15 \
    crate://crates.io/ansi_term/0.11.0 \
    crate://crates.io/anyhow/1.0.40 \
    crate://crates.io/arc-swap/1.2.0 \
    crate://crates.io/atty/0.2.14 \
    crate://crates.io/autocfg/1.0.1 \
    crate://crates.io/backtrace/0.3.58 \
    crate://crates.io/bitflags/0.5.0 \
    crate://crates.io/bitflags/1.2.1 \
    crate://crates.io/byteorder/1.4.3 \
    crate://crates.io/cc/1.0.67 \
    crate://crates.io/cfg-if/0.1.10 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/clap/2.33.3 \
    crate://crates.io/cloudabi/0.0.3 \
    crate://crates.io/credibility/0.1.3 \
    crate://crates.io/dirs-sys/0.3.6 \
    crate://crates.io/dirs/3.0.2 \
    crate://crates.io/env_logger/0.8.3 \
    crate://crates.io/epoll/4.3.1 \
    crate://crates.io/failure/0.1.8 \
    crate://crates.io/failure_derive/0.1.8 \
    crate://crates.io/getrandom/0.2.2 \
    crate://crates.io/gimli/0.23.0 \
    crate://crates.io/glob/0.2.11 \
    crate://crates.io/hermit-abi/0.1.18 \
    crate://crates.io/humantime/2.1.0 \
    crate://crates.io/iced-x86/1.11.1 \
    crate://crates.io/io-uring/0.5.1 \
    crate://crates.io/ipnetwork/0.17.0 \
    crate://crates.io/itoa/0.4.7 \
    crate://crates.io/kernel32-sys/0.2.2 \
    crate://crates.io/kvm-ioctls/0.8.0 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/libc/0.2.94 \
    crate://crates.io/libssh2-sys/0.2.21 \
    crate://crates.io/libz-sys/1.1.2 \
    crate://crates.io/linux-loader/0.3.0 \
    crate://crates.io/lock_api/0.3.4 \
    crate://crates.io/log/0.3.9 \
    crate://crates.io/log/0.4.14 \
    crate://crates.io/memchr/2.3.4 \
    crate://crates.io/miniz_oxide/0.4.4 \
    crate://crates.io/object/0.23.0 \
    crate://crates.io/openssl-sys/0.9.62 \
    crate://crates.io/parking_lot/0.10.2 \
    crate://crates.io/parking_lot_core/0.7.2 \
    crate://crates.io/pkg-config/0.3.19 \
    crate://crates.io/pnet/0.27.2 \
    crate://crates.io/pnet_base/0.27.2 \
    crate://crates.io/pnet_datalink/0.27.2 \
    crate://crates.io/pnet_macros/0.27.2 \
    crate://crates.io/pnet_macros_support/0.27.2 \
    crate://crates.io/pnet_packet/0.27.2 \
    crate://crates.io/pnet_sys/0.27.2 \
    crate://crates.io/pnet_transport/0.27.2 \
    crate://crates.io/proc-macro2/1.0.26 \
    crate://crates.io/quote/1.0.9 \
    crate://crates.io/redox_syscall/0.1.57 \
    crate://crates.io/redox_syscall/0.2.6 \
    crate://crates.io/redox_users/0.4.0 \
    crate://crates.io/regex-syntax/0.6.23 \
    crate://crates.io/regex/1.4.6 \
    crate://crates.io/remain/0.2.2 \
    crate://crates.io/rustc-demangle/0.1.18 \
    crate://crates.io/rustc-serialize/0.3.24 \
    crate://crates.io/ryu/1.0.5 \
    crate://crates.io/scopeguard/1.1.0 \
    crate://crates.io/serde/1.0.125 \
    crate://crates.io/serde_derive/1.0.125 \
    crate://crates.io/serde_json/1.0.64 \
    crate://crates.io/signal-hook-registry/1.3.0 \
    crate://crates.io/signal-hook/0.3.8 \
    crate://crates.io/smallvec/1.6.1 \
    crate://crates.io/ssh2/0.9.1 \
    crate://crates.io/static_assertions/1.1.0 \
    crate://crates.io/strsim/0.8.0 \
    crate://crates.io/syn/1.0.71 \
    crate://crates.io/synstructure/0.12.4 \
    crate://crates.io/syntex/0.42.2 \
    crate://crates.io/syntex_errors/0.42.0 \
    crate://crates.io/syntex_pos/0.42.0 \
    crate://crates.io/syntex_syntax/0.42.0 \
    crate://crates.io/term/0.4.6 \
    crate://crates.io/term_size/0.3.2 \
    crate://crates.io/termcolor/1.1.2 \
    crate://crates.io/textwrap/0.11.0 \
    crate://crates.io/thiserror-impl/1.0.24 \
    crate://crates.io/thiserror/1.0.24 \
    crate://crates.io/unicode-width/0.1.8 \
    crate://crates.io/unicode-xid/0.0.3 \
    crate://crates.io/unicode-xid/0.2.1 \
    crate://crates.io/vcpkg/0.2.12 \
    crate://crates.io/vec_map/0.8.2 \
    crate://crates.io/vfio-bindings/0.2.0 \
    crate://crates.io/virtio-bindings/0.1.0 \
    crate://crates.io/vm-memory/0.5.0 \
    crate://crates.io/vmm-sys-util/0.8.0 \
    crate://crates.io/wait-timeout/0.2.0 \
    crate://crates.io/wasi/0.10.2+wasi-snapshot-preview1 \
    crate://crates.io/winapi-build/0.1.1 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-util/0.1.5 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.2.8 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/zerocopy-derive/0.2.1 \
    crate://crates.io/zerocopy/0.3.0 \
    git://github.com/cloud-hypervisor/kvm-bindings;protocol=https;nobranch=1;name=kvm-bindings;destsuffix=kvm-bindings \
    git://github.com/cloud-hypervisor/mshv;protocol=https;nobranch=1;name=mshv-bindings;destsuffix=mshv-bindings;subpath=mshv-bindings \
    git://github.com/cloud-hypervisor/mshv;protocol=https;nobranch=1;name=mshv-ioctls;destsuffix=mshv-ioctls;subpath=mshv-ioctls \
    git://github.com/firecracker-microvm/firecracker;protocol=https;nobranch=1;name=seccomp;destsuffix=seccomp;subpath=src/seccomp \
    git://github.com/firecracker-microvm/micro-http;protocol=https;nobranch=1;name=micro_http;destsuffix=micro_http \
    git://github.com/rust-vmm/vfio-ioctls;protocol=https;nobranch=1;name=vfio-ioctls;destsuffix=vfio-ioctls \
    git://github.com/rust-vmm/vhost;protocol=https;nobranch=1;name=vhost;destsuffix=vhost \
    file://config-${PV} \
"

SRCREV_FORMAT .= "_kvm-bindings"
SRCREV_kvm-bindings = "ch-v0.4.0"
SRCREV_FORMAT .= "_micro_http"
SRCREV_micro_http = "9b605a8b61df602cda62076d30d9f70307ea336f"
SRCREV_FORMAT .= "_mshv-bindings"
SRCREV_mshv-bindings = "936e2e34af0af50f4383ab8a208e03dfba7ed206"
SRCREV_FORMAT .= "_mshv-ioctls"
SRCREV_mshv-ioctls = "936e2e34af0af50f4383ab8a208e03dfba7ed206"
SRCREV_FORMAT .= "_seccomp"
SRCREV_seccomp = "v0.24.2"
SRCREV_FORMAT .= "_vfio-ioctls"
SRCREV_vfio-ioctls = "ed8d2534576371000361e615eeb91b4266c55713"
SRCREV_FORMAT .= "_vhost"
SRCREV_vhost = "c4cd1d375e386069d0b781174de9f62ef41812e2"

LIC_FILES_CHKSUM = "file://LICENSE-APACHE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
		file://LICENSE-BSD-3-Clause;md5=b1ed361f9fc790c1054d81a7ef041a34"

SUMMARY = "Open source Virtual Machine Monitor (VMM) that runs on top of KVM"
HOMEPAGE = "https://github.com/cloud-hypervisor/cloud-hypervisor"
LICENSE = "Apache-2.0 & BSD-3-Clause"

# includes this file if it exists but does not fail
# this is useful for anything you may want to override from
# what cargo-bitbake generates.
include cloud-hypervisor-${PV}.inc
include cloud-hypervisor.inc
