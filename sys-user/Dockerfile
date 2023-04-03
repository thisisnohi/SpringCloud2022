FROM openjdk:17-jdk-alpine
COPY target/sys-user.jar app.jar
ENV JAVA_OPTS=""
CMD [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
