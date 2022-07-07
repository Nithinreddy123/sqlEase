FROM openjdk:8-jdk-alpine as mavenBuilder
COPY . .
RUN ["mvn","clean","install"]

FROM tomcat 
WORKDIR /webapps
COPY --from=mavenBuilder /target/crudapplication-0.0.1-SNAPSHOT.jar .



EXPOSE 8080

CMD ["catalina.sh", "run"]


