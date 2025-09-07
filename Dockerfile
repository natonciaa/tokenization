FROM eclipse-temurin:17-jre
WORKDIR /app
COPY build/libs/tokenization-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
    aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin 437992223430.dkr.ecr.us-east-2.amazonaws.com
    docker tag mi-imagen-java:latest 437992223430.dkr.ecr.us-east-2.amazonaws.com/tokenization:latest
    docker push 437992223430.dkr.ecr.us-east-2.amazonaws.com/tokenization:latest
