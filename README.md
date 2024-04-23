[![Latest version](https://img.shields.io/maven-central/v/software.xdev/testcontainers-java-advanced-imagebuilder?logo=apache%20maven)](https://mvnrepository.com/artifact/software.xdev/testcontainers-java-advanced-imagebuilder)
[![Build](https://img.shields.io/github/actions/workflow/status/xdev-software/testcontainers-java-advanced-imagebuilder/checkBuild.yml?branch=develop)](https://github.com/xdev-software/testcontainers-java-advanced-imagebuilder/actions/workflows/checkBuild.yml?query=branch%3Adevelop)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=xdev-software_testcontainers-java-advanced-imagebuilder&metric=alert_status)](https://sonarcloud.io/dashboard?id=xdev-software_testcontainers-java-advanced-imagebuilder)

# Advanced Image-Builder for Testcontainers Java

A re-implementation of [Testcontainers Image-Builder](https://java.testcontainers.org/features/creating_images/) with the following improvements:
* Allows passing a custom logger to the image build - [testcontainers-java#3093](https://github.com/testcontainers/testcontainers-java/issues/3093)
* Allows using ``ARG``s for ``FROM`` - [testcontainers-java#3238](https://github.com/testcontainers/testcontainers-java/issues/3238)
* Brings a custom [build-context](https://docs.docker.com/build/building/context/) processor
  * Works more efficient and reliable than the default implementation (however likely still not perfect)
  * uses ``.gitignore`` if available
  * Allows to adding custom ignores
    * This way the build-context can be fine tuned in a way that the build cache works very efficiently (e.g. only re-built when actual code that matters changes)
* Makes logger non generic and therefore controllable
* Cleaned up some code

A common use case - that can also be seen [inside the demo](./testcontainers-java-advanced-imagebuilder-demo/src/main/java/software/xdev/Application.java) - is for creating an image - used in e.g. Integration tests - for an application that is also inside the same repo.

## Installation
[Installation guide for the latest release](https://github.com/xdev-software/testcontainers-java-advanced-imagebuilder/releases/latest#Installation)

## Support
If you need support as soon as possible and you can't wait for any pull request, feel free to use [our support](https://xdev.software/en/services/support).

## Contributing
See the [contributing guide](./CONTRIBUTING.md) for detailed instructions on how to get started with our project.

## Dependencies and Licenses
View the [license of the current project](LICENSE) or the [summary including all dependencies](https://xdev-software.github.io/testcontainers-java-advanced-imagebuilder/dependencies)
