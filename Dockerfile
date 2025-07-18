#Base image with Amazon Corretto (Java 17)
FROM amazoncorretto:17-alpine3.18 AS build

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN
ARG WORK_DIR

# Install tar and curl for Maven Wrapper
RUN apk add --no-cache tar curl git

WORKDIR /app

# To bring your project files into the container for build
COPY ${WORK_DIR}/pom.xml ./pom.xml
COPY ${WORK_DIR}/start.sh ./start.sh
COPY ${WORK_DIR}/.mvn ./.mvn
COPY ${WORK_DIR}/mvnw ./mvnw
COPY ${WORK_DIR}/src ./src
COPY ${WORK_DIR}/version.properties ./version.properties

#Configure Maven Authentication with settings.xml
RUN mkdir -p /root/.m2 && \
    echo "<settings><servers><server><id>github</id><username>${GITHUB_USERNAME}</username><password>${GITHUB_TOKEN}</password></server></servers></settings>" > /root/.m2/settings.xml

# Makes mvnw executable and builds the app with Maven.
# -DskipTests: Skips test execution for faster build.
RUN chmod +x ./mvnw && ./mvnw -B -f ./pom.xml package -DskipTests

#Uses the same lightweight Java 17 image for runtime only (no Maven/tools here).
#✅ This reduces the final image size significantly.
FROM amazoncorretto:17-alpine3.18
WORKDIR /opt/learning

#This line copies files from the first build stage (which was named implicitly as build):
COPY --from=build /app/target/*.jar /app/start.sh /opt/learning/
RUN chmod +x /opt/learning/start.sh

CMD ["/opt/learning/start.sh"]
