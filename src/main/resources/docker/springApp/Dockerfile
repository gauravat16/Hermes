FROM openjdk:8u171-jre

ADD rest-lambda-1.2.jar  rest-lambda-1.2.jar
CMD mkdir -p /usr/local/var/Restify/Configuration
COPY Restify_Rest_jobs.xml /usr/local/var/Restify/Configuration/

CMD ["java", "-Dserver.port=9090", "-Dspring.profiles.active=dev","-jar", "rest-lambda-1.2.jar"]


