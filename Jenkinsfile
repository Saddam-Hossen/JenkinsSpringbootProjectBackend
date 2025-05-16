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

        stage('Build Spring Boot') {
            steps {
                bat './mvnw clean package -DskipTests'
            }
        }

        stage('Deploy JAR to DigitalOcean') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'DO_SSH_KEY', keyFileVariable: 'SSH_KEY')]) {
                    script {
                        def deployCmd = """
                            "C:\\Program Files\\Git\\bin\\bash.exe" -c \\
                            "scp -o StrictHostKeyChecking=no -i \\"$SSH_KEY\\" target/${JAR_NAME} ${PROD_USER}@${PROD_HOST}:${DEPLOY_DIR}/${JAR_NAME}"
                        """
                        bat deployCmd
                    }
                }
            }
        }

        stage('Start Spring Boot App (Remote)') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'DO_SSH_KEY', keyFileVariable: 'SSH_KEY')]) {
                    script {
                        def bashCmd = """#!/bin/bash
                            ssh -o StrictHostKeyChecking=no -i "$SSH_KEY" ${PROD_USER}@${PROD_HOST} << 'EOF'
                                echo "🔁 Stopping any running app on port ${PORT}..."
                                PID=\$(lsof -t -i:${PORT}) && kill -9 \$PID || echo "No app running on port ${PORT}"

                                echo "🚀 Starting app with nohup..."
                                cd ${DEPLOY_DIR}
                                nohup java -Xms32m -Xmx64m -jar ${JAR_NAME} --server.port=${PORT} > app.log 2>&1 &
                                echo "✅ App deployed to port ${PORT}"
                            EOF
                        """
                        writeFile file: 'start_app.sh', text: bashCmd
                        bat '"C:\\Program Files\\Git\\bin\\bash.exe" start_app.sh'
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Spring Boot deployed and started successfully on port ${PORT}!"
        }

        failure {
            echo "❌ Spring Boot deployment failed."
        }
    }
}
