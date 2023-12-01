FROM eclipse-temurin:21-jre
WORKDIR /service
COPY build/libs/*.jar service-url.jar
ENTRYPOINT ["java","-jar","service-url.jar", "--logging.file.name=url.log"]

EXPOSE 9001
