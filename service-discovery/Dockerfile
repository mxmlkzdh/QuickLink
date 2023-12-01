FROM eclipse-temurin:21-jre
WORKDIR /service
COPY build/libs/*.jar service-discovery.jar
ENTRYPOINT ["java","-jar","service-discovery.jar", "--logging.file.name=discovery.log"]

EXPOSE 8761
