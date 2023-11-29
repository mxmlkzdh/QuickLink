FROM eclipse-temurin:17-jre
WORKDIR /service
COPY build/libs/*.jar service-redirect.jar
ENTRYPOINT ["java","-jar","service-redirect.jar", "--logging.file.name=redirect.log", "--spring.profiles.active=prod"]

EXPOSE 8766
