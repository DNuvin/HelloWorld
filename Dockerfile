FROM eclipse-temurin:17-jdk-jammy

ENV APP_NAME=assignment

WORKDIR /app

COPY target/hello-world-app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]