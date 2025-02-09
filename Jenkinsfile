pipeline {
    agent any

    environment {
        // Add any environment variables you need here
        RAILWAY_API_TOKEN = credentials('bde6b295-de09-4e62-927f-9016a4cf0171')  // Jenkins credential for Railway API token
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the latest code from the repository
                git 'https://github.com/OmkarTerbhai/consistent-hashing.git'
            }
        }

        stage('Build') {
            steps {
                // Run Gradle build to compile your project
                sh './gradlew clean'
                sh './gradlew bootJar'
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploy to Railway (use the Railway CLI)
                    sh 'echo $RAILWAY_API_TOKEN | railway login'
                    sh 'railway up --project a0f5a56b-6ec6-4c2e-a94d-773a6727a218'  // Use your Railway project ID here
                }
            }
        }
    }

    post {
        always {
            // Clean up, notify, etc.
        }
    }
}
