# Stage 1: Build the dummy app
ARG JAVA_VERSION=21
FROM eclipse-temurin:$JAVA_VERSION-jdk-alpine AS build-env

RUN apk add --no-cache git bash

# Create non root user
ARG userName=limitedbuild
ARG groupName=limitedbuild
ARG userId=1000

RUN addgroup --system ${groupName} \
	&& adduser --uid ${userId} --system --disabled-password --shell /bin/bash ${userName} \
	&& adduser ${userName} ${groupName}

# Create build dir
RUN mkdir /build \
    && chown ${userName}:${groupName} /build
WORKDIR /build

USER ${userName}

# Copying context is prepared by Testcontainers
COPY --chown=${userName}:${groupName} . ./

# RUN chmod +x ./mvnw
ARG mvncmd='clean package -pl "testcontainers-advanced-imagebuilder-dummy-app" -am -T2C -Dmaven.test.skip'

RUN echo "Executing '$mvncmd'"
RUN chmod +x ./mvnw \
  && ./mvnw -B $mvncmd

# Stage 2: Build the executable image
FROM eclipse-temurin:21-jre-alpine

ARG user=dummy-app
ARG group=dummy-app
ARG uid=1000
ARG gid=1000
ARG APP_DIR=/opt/dummy-app

# Create user + group + home
RUN mkdir -p $APP_DIR \
  && chown ${uid}:${gid} $APP_DIR \
  && addgroup -g ${gid} ${group} \
  && adduser -h "$APP_DIR" -u ${uid} -G ${group} -s /bin/bash -D ${user}

EXPOSE 8080

USER ${user}

COPY --from=build-env --chown=${user}:${group} build/testcontainers-advanced-imagebuilder-dummy-app/target/dummy-app.jar ${APP_DIR}/dummy-app.jar

CMD java $JAVA_OPTS -jar /opt/dummy-app/dummy-app.jar
