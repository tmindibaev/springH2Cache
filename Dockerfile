FROM openjdk:8-jdk
VOLUME /tmp
ENV JAVA_HOME /home/timofey/.sdkman/candidates/java/current/bin/java
ADD  target/sprngbt-0.0.1-SNAPSHOT.jar target/app.jar
RUN bash -c 'touch target/app.jar'
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","target/app.jar"]

