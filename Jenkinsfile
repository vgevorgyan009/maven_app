#!/usr/bin/env groovy

// jenkinsfile to build and deploy java-maven-apps docker image to aws eks cluster
pipeline {
  agent any
  tools {
      maven 'maven-3.9'
  }
  environment {
    IMAGE_NAME = '872'
    DOCKER_REPO_SERVER = "891376912861.dkr.ecr.eu-central-1.amazonaws.com"
    DOCKER_REPO = "${DOCKER_REPO_SERVER}/java-maven-app"
  }
  stages {
    stage("build app") {      
        steps {
            script {
              echo "building the application..."
              sh 'mvn clean package'
          }
      }
    }
    stage('build image') {
        steps {
            script {
                echo 'building the docker image...'
                withCredentials([usernamePassword(credentialsId: 'ecr-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                sh "docker build -t ${DOCKER_REPO}:${IMAGE_NAME} ."
                sh 'echo $PASS | docker login -u $USER --password-stdin ${DOCKER_REPO_SERVER}'
                sh "docker push ${DOCKER_REPO}:${IMAGE_NAME}"
    }
            }
        }
    }
    stage("deploy") {
        environment {
            AWS_ACCESS_KEY_ID = credentials('my_secret_login')
            AWS_SECRET_ACCESS_KEY = credentials('my_secret_pass')
            APP_NAME = 'java-maven-app'
        }
        steps {
            script {
                echo "deploying the application..."
                sh 'envsubst < kubernetes/deployment.yaml | kubectl apply -f -'
                sh 'envsubst < kubernetes/service.yaml | kubectl apply -f -'
            }
        } 
     } 
   }
}

