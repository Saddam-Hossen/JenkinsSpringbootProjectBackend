pipeline {
    agent any

    environment {
        PROD_HOST  = credentials('DO_HOST')
        PROD_USER  = credentials('DO_USER')
        DEPLOY_DIR = '/www/wwwroot/CITSNVN/jenkins/sscSpringbootBackend'
        JAR_NAME   = 'quizbackend-1.0.jar'
        PORT       = '3086'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Saddam-Hossen/JenkinsSpringbootProjectBackend.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy JAR to DigitalOcean') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'DO_SSH_KEY', keyFileVariable: 'SSH_KEY')]) {
                    script {
                        def scpCommand = '''"C:/Program Files/Git/bin/bash.exe" -c "scp -o StrictHostKeyChecking=no -i '${SSH_KEY}' target/${JAR_NAME} ${PROD_USER}@${PROD_HOST}:${DEPLOY_DIR}/${JAR_NAME}"'''
                        bat(script: scpCommand)
                    }
                }
            }
        }

        stage('Start Spring Boot App (Remote)') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'DO_SSH_KEY', keyFileVariable: 'SSH_KEY')]) {
                    script {
                        def sshCommand = '''"C:/Program Files/Git/bin/bash.exe" -c "ssh -o StrictHostKeyChecking=no -i '${SSH_KEY}' ${PROD_USER}@${PROD_HOST} '
                            cd ${DEPLOY_DIR};
                            PID=\\$(lsof -t -i:${PORT}) && kill -9 \\$PID || echo Not running;
                            nohup java -Xms32m -Xmx64m -jar ${JAR_NAME} --server.port=${PORT} > app.log 2>&1 &
                            echo Spring Boot App started on port ${PORT}
                        '"'''
                        bat(script: sshCommand)
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Spring Boot app deployed and started successfully!'
        }
        failure {
            echo '❌ Spring Boot deployment failed.'
        }
    }
}
