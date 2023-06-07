pipeline {
    agent any
    parameters {
        string(name: "DOCKER_TAG", defaultValue: "latest", description: "The tag of the Docker Image to pull")
        string(name: "SUITE", defaultValue: "suite.xml", description: "The file name of the suite to run.")
        string(name: "REQUEST_QUEUE", defaultValue: "q.request.test", description: "Change this if you need to run on a different queue")
        string(name: "RECEIVE_QUEUE", defaultValue: "q.receive.result", description: "Change this if you need to run on a different queue")
    }
    stages {
        stage("Run Suite") {
            steps {
                script {
                    sh "docker pull radusimonica/test_distributor:$DOCKER_TAG"
                    withCredentials([usernamePassword(credentialsId: 'rabbit-credentials', passwordVariable: 'RABBIT_PASS', usernameVariable: 'RABBIT_USER')]) {
                        sh "docker run -e RABBITMQ_DEFAULT_USER=$RABBIT_USER -e RABBITMQ_DEFAULT_PASS=$RABBIT_PASS radusimonica/test_distributor:$DOCKER_TAG java -jar /test-distributor/distributedTest.jar --publisher --suite $SUITE --rabbitHost 192.168.0.150 --requestQueue $REQUEST_QUEUE --receiveQueue $RECEIVE_QUEUE"
                    }
                }
            }
        }
    }
}