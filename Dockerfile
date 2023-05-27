FROM gradle:jdk17
ADD . /test-distributor
WORKDIR /test-distributor
RUN gradle fatJar