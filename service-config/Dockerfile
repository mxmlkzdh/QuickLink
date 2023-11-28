FROM eclipse-temurin:17-jre
WORKDIR /service
COPY build/libs/*.jar service-config.jar
ENTRYPOINT ["java","-jar","service-config.jar", "--logging.file.name=config.log"]

EXPOSE 8888
