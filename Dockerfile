FROM gradle:jdk17
ADD ./build/libs/distributedTest.jar /test-distributor/distributedTest.jar
WORKDIR /test-distributor