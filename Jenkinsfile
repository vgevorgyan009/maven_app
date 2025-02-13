#!/usr/bin/env groovy

// heto grum enq
pipeline {
  agent any
  tools {
      maven 'maven-3.9'
  }
  environment {
    IMAGE_NAME = '872'
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
                withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                sh "docker build -t vgevorgyan009/demo-app:${IMAGE_NAME} ."
                sh 'echo $PASS | docker login -u $USER --password-stdin'
                sh "docker push vgevorgyan009/demo-app:${IMAGE_NAME}"
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

