FROM openjdk:20
ADD target/TaskManager.jar TaskManager.jar
ENTRYPOINT ["java", "-jar", "TaskManager.jar"]
