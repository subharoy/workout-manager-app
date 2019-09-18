FROM java:8
LABEL maintainer="1subharoy"
VOLUME /tmp
EXPOSE 8080
ADD target/workout-manager-exec.jar workout-manager-exec.jar
ENTRYPOINT ["java", "-jar", "workout-manager-exec.jar"]
