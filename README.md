# meta-cloud-hypervisor

This Yocto layer provides build definitions for [Cloud Hypervisor](https://github.com/cloud-hypervisor/cloud-hypervisor) and accompanying tooling.

# Dependencies

* URI: http://git.yoctoproject.org/clean/cgit.cgi/poky
* Branch: thud|warrior|zeus|dunfell

* URI: https://github.com/meta-rust/meta-rust/
* Branch: thud|warrior|zeus|dunfell

# TODO 

* <strike>Add patched edk2 firmware suitable for CH</strike>
* Add recipe for static CH build
* Add meta-virtualization dependency for 
  * Better KVM support
  * libvirt
* Configure CI builds (Travis-CI?)
* Improve Rust recipe offline build. Most likely requires some change to `meta-rust` cargo and other bbclasses
* Improve Rust recipe support for git based sources.
