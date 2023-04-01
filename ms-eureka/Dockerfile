FROM openjdk:17-jdk-alpine
ADD target/ms-eureka.jar app.jar
ENV JAVA_OPTS=""
CMD [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]