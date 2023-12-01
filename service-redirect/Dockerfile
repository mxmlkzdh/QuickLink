FROM eclipse-temurin:21-jre
WORKDIR /service
COPY build/libs/*.jar service-redirect.jar
ENTRYPOINT ["java","-jar","service-redirect.jar", "--logging.file.name=redirect.log"]

EXPOSE 9002
