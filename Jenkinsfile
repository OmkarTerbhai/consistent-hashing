pipeline {
    agent any

    environment {
        OPENSHIFT_SERVER = 'https://console-openshift-console.apps.rm1.0a51.p1.openshiftapps.com/' // Replace with your OpenShift Sandbox URL
        OPENSHIFT_TOKEN = credentials('sha256~OPVvo1Mo2A8mznqaEOxzlTpMZdKy3-UoZ6ZZ9uhmlEo') //  // Add OpenShift token as a Jenkins credential
        PROJECT_NAME = 'omkarterbhai.dev'                // Replace with your namespace
        APP_NAME = 'my-gradle-app'
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone the Git repository
                git url: 'https://github.com/OmkarTerbhai/consistent-hashing.git', branch: 'main'
            }
        }

        stage('Build Gradle Project') {
            steps {
                // Run Gradle build
                sh './gradlew clean build'
                sh './gradlew bootJar'
            }
        }

        stage('Login to OpenShift') {
            steps {
                // Log in to OpenShift using the token
                sh '''
                    oc login $OPENSHIFT_SERVER --token=$OPENSHIFT_TOKEN
                    oc project $PROJECT_NAME
                '''
            }
        }

        stage('Deploy Application') {
            steps {
                // Deploy the application using the oc new-app command
                sh '''
                    oc new-app java:11~https://github.com/OmkarTerbhai/consistent-hashing.git \
                        --name=$APP_NAME --build-env GRADLE_OPTS=-Dorg.gradle.daemon=false
                '''
            }
        }

        stage('Expose Application') {
            steps {
                // Expose the application to create a route
                sh 'oc expose svc/$APP_NAME'
            }
        }

        stage('Verify Deployment') {
            steps {
                // Verify the application is deployed and available
                sh 'oc get route $APP_NAME'
            }
        }
    }
}
