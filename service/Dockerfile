FROM adoptopenjdk/openjdk8
MAINTAINER baeldung.com
COPY target/service-0.0.1-SNAPSHOT.jar service-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/service-1.0.0.jar"]