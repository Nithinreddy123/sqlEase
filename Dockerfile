FROM maven as mavenBuilder
COPY . .
RUN ["mvn","clean","install"]

FROM tomcat 
WORKDIR /usr/local/tomcat/webapps
COPY --from=mavenBuilder /target/crudapplication-0.0.1-SNAPSHOT.war .



EXPOSE 8080

CMD ["catalina.sh", "run"]


