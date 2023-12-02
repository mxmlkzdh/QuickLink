FROM eclipse-temurin:21-jre
WORKDIR /service
COPY build/libs/*.jar service-gateway.jar
ENTRYPOINT ["java","-jar","service-gateway.jar", "--logging.file.name=gateway.log"]

EXPOSE 8080
