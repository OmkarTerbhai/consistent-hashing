pipeline {
    agent any

    environment {
        OPENSHIFT_SERVER = 'https://console-openshift-console.apps.rm1.0a51.p1.openshiftapps.com/'
        OPENSHIFT_TOKEN = credentials('sha256~OPVvo1Mo2A8mznqaEOxzlTpMZdKy3-UoZ6ZZ9uhmlEo') // Jenkins credential for OpenShift token
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
                sh './gradlew bootJar'
            }
        }

        stage('Push to OpenShift') {
            steps {
                script {
                    def appName = 'gradle-app'
                    def namespace = '<your-namespace>'
                    def jarFile = 'build/libs/*.jar'

                    // Login to OpenShift
                    sh "oc login ${OPENSHIFT_SERVER} --token=${OPENSHIFT_TOKEN}"

                    // Create a new application or update an existing one
                    sh "oc new-app openjdk-11~. --name=${appName} -n ${namespace} || true"
                    sh "oc start-build ${appName} --from-file=${jarFile} --follow -n ${namespace}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def appName = 'gradle-app'
                    def namespace = '<your-namespace>'

                    // Expose the application
                    sh "oc expose svc/${appName} -n ${namespace} || true"
                }
            }
        }
    }
}
