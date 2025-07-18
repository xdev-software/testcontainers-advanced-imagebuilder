[![Latest version](https://img.shields.io/maven-central/v/software.xdev/testcontainers-advanced-imagebuilder?logo=apache%20maven)](https://mvnrepository.com/artifact/software.xdev/testcontainers-advanced-imagebuilder)
[![Build](https://img.shields.io/github/actions/workflow/status/xdev-software/testcontainers-advanced-imagebuilder/check-build.yml?branch=develop)](https://github.com/xdev-software/testcontainers-advanced-imagebuilder/actions/workflows/check-build.yml?query=branch%3Adevelop)

# Advanced Image-Builder for Testcontainers Java

A re-implementation of [Testcontainers Image-Builder](https://java.testcontainers.org/features/creating_images/) with the following improvements:
* Allows passing a custom logger to the image build - [testcontainers-java#3093](https://github.com/testcontainers/testcontainers-java/issues/3093)
* Allows using ``ARG``s for ``FROM`` - [testcontainers-java#3238](https://github.com/testcontainers/testcontainers-java/issues/3238)
* Brings a custom [build-context](https://docs.docker.com/build/building/context/) processor
  * Works more efficient and reliable than the default implementation because it utilizes [JGit](https://github.com/eclipse-jgit/jgit)
  * uses the ``.gitignore`` if available
  * Allows adding custom ignores
    * This way the build-context can be fine tuned in a way that the build cache works very efficiently (e.g. only re-built when actual code that matters changes)
  * Makes it possible to modify files that are transferred
* Provide a compatibility layer to emulate [``COPY --parents``](https://docs.docker.com/reference/dockerfile/#copy---parents) (which is currently not supported by Docker out of the box)
* Do not pull images that are declared inside the Dockerfile
* Makes logger non generic and therefore controllable
* Some general code cleanup and performance improvements

For more details have a look at [the demo](./testcontainers-advanced-imagebuilder-demo/src/main/java/software/xdev/Application.java).<br/>The demo showcases how an image for another application in the same repo can be built.

## Installation
[Installation guide for the latest release](https://github.com/xdev-software/testcontainers-advanced-imagebuilder/releases/latest#Installation)

## Support
If you need support as soon as possible and you can't wait for any pull request, feel free to use [our support](https://xdev.software/en/services/support).

## Contributing
See the [contributing guide](./CONTRIBUTING.md) for detailed instructions on how to get started with our project.

## Dependencies and Licenses
View the [license of the current project](LICENSE) or the [summary including all dependencies](https://xdev-software.github.io/testcontainers-advanced-imagebuilder/dependencies)

<sub>Disclaimer: This is not an official Testcontainers product and not associated</sub>
