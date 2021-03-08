pipeline {

  agent any

  options {
    timeout(time: 30, unit: 'MINUTES')
    buildDiscarder(logRotator(numToKeepStr: '2'))
  }
  
  environment {

    SOURCE_REPOSITORY_URL = 'https://github.com/MitraInnovationRepo/customer.git'
    GIT_CREDINTIALS_ID = '3a1de958-c693-4386-ba4a-de976655e3c1'
    BRANCH = 'develop'

    SONAR_URL = 'http://10.37.25.3:9090'
    SONAR_LOGIN = '2164584212ee96a925146dca18f3906d316a65f3'

    DOCKER_REGISTRY_URL = 'dr-ob-nexus.com.vn:8080'
    DOCKER_CREDINTIALS_ID = 'b4b32ec6-434b-4d3e-991b-99e98addd66a'
    dockerImage = ''

  }

  stages {

    stage ('Checkout source') {
      steps {
        echo "Checkout SCM"
        git branch: "${BRANCH}",credentialsId: "${GIT_CREDINTIALS_ID}",url: "${SOURCE_REPOSITORY_URL}"
      }
    }

    stage ('Gather facts') {
      steps {
      script {

      version = sh (script: "./gradlew properties -q | grep \"version:\" | awk '{print \$2}'",returnStdout: true).trim();
      groupid = sh (script: "./gradlew properties -q | grep \"group:\" | awk '{print \$2}'",returnStdout: true).trim();
      artifactId = sh (script: "./gradlew properties -q | grep \"name:\" | awk '{print \$2}'",returnStdout: true).trim();

      }
      echo "Building project in version: $version , groupid: $groupid , artifactId : $artifactId";
  }
    }

     stage ('Build JAR') {
       steps {
        echo "Building version ${version}"
        sh (script: "./gradlew clean build -x test",returnStdout: true)
       }
     }

     stage ('Unit Tests') {
       steps {
         echo "Running Unit Tests"
         sh (script: "./gradlew test",returnStdout: true)
       }
     }


     stage ('Code Analysis') {
       steps {
        echo "Running Code Analysis"

        sh (script: "./gradlew sonarqube -Dsonar.host.url=$SONAR_URL -Dsonar.login=$SONAR_LOGIN -Dsonar.sources=./src/main -Dsonar.projectKey=$artifactId  -Dsonar.projectVersion=$version",returnStdout: true)

       }
     }
        
     stage('Docker Build') {
        steps {
         script {
          dockerImage = docker.build "$DOCKER_REGISTRY_URL" + "/$artifactId:$version"
          }
        }
      }

    stage('Docker Push') {
        steps {
         script {
        docker.withRegistry( "http://" + "$DOCKER_REGISTRY_URL", "$DOCKER_CREDINTIALS_ID" ) {
            dockerImage.push()
          }
          }
        }
      }           
  }
}
