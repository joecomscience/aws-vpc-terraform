#!/bin/groovy

def Build(applicationId = null) {
    sh "mvn -X package"
}

def InstallDependency() {
    sh "mvn clean install"
}

def SonarScan(projectKey = null, projectName = null) {
    sh """
        mvn -X sonar:sonar \
          -Dsonar.projectKey=${projectKey} \
          -Dsonar.projectName=${projectName} \
          -Dsonar.login=${SONAR_TOKEN} \
          -Dsonar.host.url=http://host.docker.internal:9000
    """
}