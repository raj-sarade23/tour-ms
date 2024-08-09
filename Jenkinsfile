pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = "767397972509"
        REGION = "us-east-1"
        ECR_URL = "${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com"
        IMAGE_NAME = "rajashri23/tour-ms:tour-ms-v.1.${env.BUILD_NUMBER}"
        ECR_IMAGE_NAME = "${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/rajashri23/tour-ms:tour-ms-v.1.${env.BUILD_NUMBER}"
        NEXUS_IMAGE_NAME = "13.201.68.13:8085/tour-ms:dev-tour-ms-v.1.${env.BUILD_NUMBER}"
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    tools {
        maven 'maven_3.9.4'
        // sonarqubeScanner 'sonarqube-scanner'
    }

    stages {
        stage('Code Compilation') {
            steps {
                echo 'Code Compilation is In Progress!'
                sh 'mvn clean compile'
                echo 'Code Compilation is Completed Successfully!'
            }
        }

        stage('Code Package') {
            steps {
                echo 'Creating WAR Artifact'
                sh 'mvn clean package'
                echo 'Artifact Creation Completed'
            }
        }
        stage('Building & Tag Docker Image') {
            steps {
                echo "Starting Building Docker Image: ${env.IMAGE_NAME}"
                sh "docker build -t ${env.IMAGE_NAME} ."
                echo 'Docker Image Build Completed'
            }
        }
         stage('Docker Image Scanning') {
                    steps {
                        echo 'Docker Image Scanning Started'
                        sh 'java -version'
                        echo 'Docker Image Scanning Started'
                    }
                }



         stage('Upload the Docker Image to Nexus') {
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                sh 'docker login http://13.201.68.13:8085/repository/tour-ms/ -u admin -p ${PASSWORD}'
                                echo "Push Docker Image to Nexus: In Progress"
                                sh "docker tag ${env.IMAGE_NAME} ${env.NEXUS_IMAGE_NAME}"
                                sh "docker push ${env.NEXUS_IMAGE_NAME}"
                                echo "Push Docker Image to Nexus: Completed"
                            }
                        }
                    }
                }

                  stage('Delete Local Docker Images') {
                            steps {
                                echo "Deleting Local Docker Images: ${env.IMAGE_NAME} ${env.ECR_IMAGE_NAME} ${env.NEXUS_IMAGE_NAME}"
                                sh "docker rmi ${env.IMAGE_NAME} ${env.ECR_IMAGE_NAME} ${env.NEXUS_IMAGE_NAME}"
                                echo "Local Docker Images Deletion Completed"
                            }
                        }

    }
}
