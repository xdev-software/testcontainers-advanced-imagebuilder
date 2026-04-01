# syntax=docker/dockerfile:1-labs
# Stage 1: Build the dummy app
ARG JAVA_VERSION=25
FROM eclipse-temurin:$JAVA_VERSION-jdk-alpine AS build-env

RUN apk add --no-cache bash

WORKDIR /build

# Copy & Cache wrapper
COPY --parents mvnw .mvn/** ./
RUN ./mvnw --version

# Copy & Cache poms/dependencies
COPY --parents **/pom.xml ./
# Resolve jars so that they can be cached and don't need to be downloaded when a Java file changes
ARG MAVEN_GO_OFFLINE_COMMAND='./mvnw -B dependency:go-offline -pl "testcontainers-advanced-imagebuilder-dummy-app" -am -DincludeScope=runtime -T2C'
RUN echo "Executing '$MAVEN_GO_OFFLINE_COMMAND'"
RUN ${MAVEN_GO_OFFLINE_COMMAND}

# Copying all other files
COPY . ./

ARG MAVEN_BUILD_COMMAND='./mvnw -B clean package -pl "testcontainers-advanced-imagebuilder-dummy-app" -am -T2C -Dmaven.test.skip'
RUN echo "Executing '$MAVEN_BUILD_COMMAND'"
RUN ${MAVEN_BUILD_COMMAND}

# Stage 2: Build the executable image
FROM eclipse-temurin:$JAVA_VERSION-jre-alpine

ARG user=dummy-app
ARG group=dummy-app
ARG uid=1000
ARG gid=1000
ARG APP_DIR=/opt/dummy-app

# Create user + group + home
RUN mkdir -p ${APP_DIR} \
  && chown ${uid}:${gid} ${APP_DIR} \
  && addgroup -g ${gid} ${group} \
  && adduser -h "$APP_DIR" -u ${uid} -G ${group} -s /bin/bash -D ${user}

EXPOSE 8080

USER ${user}

COPY --from=build-env --chown=${user}:${group} build/testcontainers-advanced-imagebuilder-dummy-app/target/dummy-app.jar ${APP_DIR}/dummy-app.jar

CMD java ${JAVA_OPTS} -jar /opt/dummy-app/dummy-app.jar
