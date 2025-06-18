# 2.0.0
* Changed ignore backend to utilize [JGit](https://github.com/eclipse-jgit/jgit) 
    * This should now behave exactly like a ``.gitignore``
    * Overall performance should be a lot faster
* Make it possible to modify transferred files
* Provide an option to emulate [``COPY --parents``](https://docs.docker.com/reference/dockerfile/#copy---parents) (which is currently not supported by Docker out of the box)
    * This option is required to utilize Docker's cache properly
    ```docker
    # ...
    
    # Copy & Cache wrapper
    COPY --parents mvnw .mvn/** ./
    RUN ./mvnw --version

    # Copy & Cache poms/dependencies
    COPY --parents **/pom.xml ./
    # Resolve jars so that they can be cached and don't need to be downloaded when a Java file changes
    RUN ./mvnw -B dependency:go-offline -pl app -am -DincludeScope=runtime -T2C

    # Copying all other files
    COPY . ./
    # Run the actual build
    RUN ./mvnw -B clean package -pl app -am -T2C -Dmaven.test.skip
    ```
* At ton of minor optimizations and improvements

# 1.2.0
* Provide an option to always transfer specific paths
* Always transfer Dockerfile - as it is required for building - by default

# 1.1.1
* Migrated deployment to _Sonatype Maven Central Portal_ [#155](https://github.com/xdev-software/standard-maven-template/issues/155)

# 1.1.0
* Remove testcontainer's dependency [onto JUnit 4](https://github.com/xdev-software/testcontainers-junit4-mock/?tab=readme-ov-file)
* Updated dependencies

# 1.0.2
* Do not pull images that are declared inside the Dockerfile (Multi-Stage build)

# 1.0.1
* Minor improvements in cleanup behavior (backported from upstream)

# 1.0.0
_Initial release_
