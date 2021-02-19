FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/core-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 443
ENTRYPOINT ["java","-jar","/app.jar"]