FROM gradle:jdk17-alpine
ADD . /test-distributor
WORKDIR /test-distributor
RUN gradle fatJar