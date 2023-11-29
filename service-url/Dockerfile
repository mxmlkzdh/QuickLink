FROM eclipse-temurin:17-jre
WORKDIR /service
COPY build/libs/*.jar service-url.jar
ENTRYPOINT ["java","-jar","service-url.jar", "--logging.file.name=url.log", "--spring.profiles.active=prod"]

EXPOSE 8765
