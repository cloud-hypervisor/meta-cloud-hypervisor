# meta-cloud-hypervisor

This Yocto layer provides build definitions for [Cloud Hypervisor](https://github.com/cloud-hypervisor/cloud-hypervisor) and accompanying tooling.

# Dependencies

* URI: https://github.com/meta-rust/meta-rust/
* branch: thud|warrior|zeus|dunfell

# TODO 

* Add patched edk2 firmware suitable for CH
* Add recipe for static CH build
* Add meta-virtualization dependency for 
  * Better KVM support
  * libvirt
* Configure CI builds (Travis-CI?)
* Improve offline build. Most likely requires some change to `meta-rust` cargo and other bbclasses
* Improve support for git based sources.
