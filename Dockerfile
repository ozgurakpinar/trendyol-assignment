FROM openjdk:8
ADD build/libs/assignment.jar assignment.jar
EXPOSE 8989
ENTRYPOINT ["java", "-jar", "assignment.jar"]