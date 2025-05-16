Here is a **complete and robust deployment guide** for deploying your **Spring Boot project** to a **DigitalOcean server using Jenkins** and the pipeline you've shared.

---

## ✅ Prerequisites Checklist

| Requirement                | Description                                               |
| -------------------------- | --------------------------------------------------------- |
| Jenkins Installed          | On your local/dev machine or Jenkins server               |
| Maven Installed            | Make sure `mvn` runs successfully                         |
| GitHub Repo Ready          | Your Spring Boot project lives on GitHub (✅ already done) |
| DigitalOcean Droplet       | Ubuntu-based server with SSH key access                   |
| NGINX Installed (Optional) | Only if you're using a reverse proxy or port 80/443       |
| Firewall Rules             | Port `3086` open in DigitalOcean for external access      |

---

## 🧱 Jenkins Pipeline Explanation (Step by Step)

### ### 🔹 `environment` block

Defines variables:

```groovy
PROD_HOST   = credentials('DO_HOST')          // DigitalOcean IP or domain
PROD_USER   = credentials('DO_USER')          // Usually 'root' or a custom user
DEPLOY_DIR  = '/www/wwwroot/CITSNVN/jenkins/sscSpringbootBackend'
JAR_NAME    = 'quizbackend-1.0.jar'           // Name of your Spring Boot JAR
PORT        = '3086'                          // Port Spring Boot will run on
```

---

### ### 🧾 Full Jenkins Pipeline (Cleaned and Final Version)

```groovy
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
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/Saddam-Hossen/JenkinsSpringbootProjectBackend'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Deploy JAR to Server') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'DO_SSH_KEY', keyFileVariable: 'SSH_KEY')]) {
                    script {
                        bat """
                        "C:/Program Files/Git/bin/bash.exe" -c "scp -o StrictHostKeyChecking=no -i '${SSH_KEY}' target/${JAR_NAME} ${PROD_USER}@${PROD_HOST}:${DEPLOY_DIR}/${JAR_NAME}"
                        """
                    }
                }
            }
        }

        stage('Start Spring Boot App (Remote)') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'DO_SSH_KEY', keyFileVariable: 'SSH_KEY')]) {
                    script {
                        bat """
                        "C:/Program Files/Git/bin/bash.exe" -c "ssh -o StrictHostKeyChecking=no -i '${SSH_KEY}' ${PROD_USER}@${PROD_HOST} 'cd ${DEPLOY_DIR}; PID=\$(lsof -t -i:${PORT}); if [ ! -z \$PID ]; then kill -9 \$PID; fi; nohup java -Xms32m -Xmx64m -jar ${JAR_NAME} --server.port=${PORT} > app.log 2>&1 &'"
                        """
                    }
                }
            }
        }

    }

    post {
        failure {
            echo "❌ Spring Boot deployment failed."
        }
        success {
            echo "✅ Spring Boot deployed successfully."
        }
    }
}
```

---

## 🧪 Test Deployment

1. Open browser:
   `http://<your-server-ip>:3086/`
   or if you use a domain:
   `http://yourdomain.com:3086/`

2. Tail logs (SSH into server):

   ```bash
   tail -f /www/wwwroot/CITSNVN/jenkins/sscSpringbootBackend/app.log
   ```

---

## 🌐 (Optional) Add NGINX Reverse Proxy

To avoid exposing raw port `3086`, you can use NGINX:

```nginx
    server {
        listen 3085;
        server_name 159.89.172.251;  # Replace with your domain if you have one
    
        root /www/wwwroot/CITSNVN/jenkins/angular/browser;
        index index.html;
    
        location / {
            try_files $uri $uri/ /index.html;
        }
    
        # Proxy API requests to Spring Boot backend on port 3086
        location /api/student/ {
            proxy_pass http://localhost:3086/;
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection 'upgrade';
            proxy_set_header Host $host;
            proxy_cache_bypass $http_upgrade;
        }
    }

```

Then reload:

```bash
sudo nginx -t && sudo systemctl reload nginx
```

---

## ✅ Summary

| Task                              | Status |
| --------------------------------- | ------ |
| Jenkins pulls code from GitHub    | ✅      |
| Maven builds Spring Boot JAR      | ✅      |
| JAR is uploaded via `scp`         | ✅      |
| Remote server kills old app       | ✅      |
| Starts Spring Boot via `nohup`    | ✅      |
| Optional: reverse proxy via NGINX | ✅      |

---

Let me know if you want me to **add rollback support**, **nginx template file**, or **Spring Boot log monitoring tips**!
