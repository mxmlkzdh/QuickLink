FROM eclipse-temurin:21-jre
WORKDIR /service
COPY build/libs/*.jar service-analytics.jar
ENTRYPOINT ["java","-jar","service-analytics.jar", "--logging.file.name=analytics.log"]

EXPOSE 9003
