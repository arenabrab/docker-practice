FROM openjdk:14-jdk-alpine
COPY docker/id_rsa /root/.ssh/
COPY docker/config /root/.ssh/
RUN apk update; apk --no-cache add curl; apk add openssh; chmod 600 /root/.ssh/id_rsa
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
