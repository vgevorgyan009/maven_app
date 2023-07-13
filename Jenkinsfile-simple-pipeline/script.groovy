def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'DockerHub_Auth', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t surendradockerhub/practice:jma-2.0 -f ../Dockerfile .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push surendradockerhub/practice:jma-2.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
