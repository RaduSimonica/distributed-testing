FROM openjdk:17-jdk
RUN useradd -ms /bin/bash jdkUsr
WORKDIR /test-distributor
COPY ./build/libs/distributedTest.jar .
RUN chown jdkUsr:jdkUsr distributedTest.jar
RUN chmod +x distributedTest.jar
USER jdkUsr
CMD ["java", "-jar", "myJar.jar"]