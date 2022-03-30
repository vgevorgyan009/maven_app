def gv

pipeline {
    agent any
    parameters{
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }
    environment {
        NEW_VERSION = '1.3.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
    }

    stages {

        stage("init"){
            steps{
                script{
                    gv = load "script.groovy"
                }
            }
        }
        stage('build') {
            steps {
                scrpit{
                    gv.buildApp()
                }
            }
        }
        
        stage('test') {
            steps {
                scrpit{
                    gv.testApp()
                }
            }
        }
        
        stage('deploy') {
            steps {
                scrpit{
                    gv.deployApp()
                }
            }
        }
    }
}