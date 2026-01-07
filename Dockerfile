From openjdk:8-jdk-alpine
copy ./target/dtdemo-usecase-1.0-SNAPSHOT.jar dtdemo-usecase-1.0-SNAPSHOT.jar
CMD ["java", "-Xms256m", "-Xmx1907m", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "dtdemo-usecase-1.0-SNAPSHOT.jar"]