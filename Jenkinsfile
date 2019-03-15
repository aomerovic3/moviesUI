pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script{
                if (isUnix()) {
                    sh './gradlew clean build'
                     } else {
                        bat 'gradlew clean build'
                      }
                  }

            }

        }
        stage ('Docker test') {
            steps {
                script {
                    bat 'gradle buildDocker'
                }
            }
        }
        stage ('Unit test') {
        // returnStatus: true here will ensure the build stays yellow
        // when test cases are failing
            steps {
                script {
                     if (isUnix()) {
                        sh './gradlew test'
                     } else {
                        bat 'gradlew test'
                      }
                  }
            }
        }

        stage('Packaging') {
            steps{
                script{
                    if(isUnix()) {
                        sh './gradlew bootWar -Pprod'
                     } else {
                        bat 'gradlew bootWar -Pprod'
                        }
                    }
                  }
        }
        stage('Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/build/libs/*.war'
            }
        }
        stage('Deploy') {
            steps {
                script {
                    bat 'copy /y "C:\\Program Files (x86)\\Jenkins\\workspace\\restservice-pipeline\\build\\libs\\movies.war" "C:\\Program Files\\wildfly-11.0.0.Final\\standalone\\deployments"'
                    }
            }
        }
    }

}
