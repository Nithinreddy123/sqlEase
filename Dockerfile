FROM maven:3.8.6-openjdk-11 as mavenBuilder
COPY . .
RUN ["mvn","clean","install"]

FROM tomcat:9.0.64-jre11 
WORKDIR /usr/local/tomcat/webapps
COPY --from=mavenBuilder /target/crudapplication-0.0.1-SNAPSHOT.war .



EXPOSE 8080

CMD ["catalina.sh", "run"]


