FROM openjdk:25-ea-25-jdk-slim
COPY ./target/dtdemo-usecase-1.0-SNAPSHOT.jar dtdemo-usecase-1.0-SNAPSHOT.jar

EXPOSE 8080
# CMD ["java", "-Xms256m", "-Xmx1907m", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "dtdemo-usecase-1.0-SNAPSHOT.jar"]

# Command to run the application
CMD ["java", "-jar", "dtdemo-usecase-1.0-SNAPSHOT.jar"]
