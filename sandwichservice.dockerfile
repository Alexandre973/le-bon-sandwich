FROM openjdk:8
VOLUME /tmp
ADD target/le-bon-sandwich-1.0-SNAPSHOT.jar le-bon-sandwich.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/le-bon-sandwich.jar"]