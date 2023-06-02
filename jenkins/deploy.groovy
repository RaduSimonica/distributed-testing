pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    cleanWs()
                    checkout scm
                }
            }
        }
        stage('TODO: Add tests!!!') {
            steps {
                script {
                    sh "echo Still no tests? :("
                }
            }
        }
        stage('Build jar') {
            steps {
                script {
                    sh "chmod +x gradlew"
                    sh "./gradlew fatJar"
                }
            }
        }
        stage('Build Docker image') {
            steps {
                script {
                    def imageTag = "${env.BRANCH_NAME}".toString() == "master" ? "$BUILD_NUMBER" : "SNAPSHOT_$BUILD_NUMBER"
                    sh "docker build -t radusimonica/test_distributor:$imageTag ."
                }
            }
        }
        stage('Publish to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "docker login -u $USERNAME -p $PASSWORD"

                        def imageTag = "${env.BRANCH_NAME}".toString() == "master" ? "$BUILD_NUMBER" : "SNAPSHOT_$BUILD_NUMBER"
                        sh "docker push radusimonica/test_distributor:$imageTag"
                    }
                }
            }
        }
    }
}