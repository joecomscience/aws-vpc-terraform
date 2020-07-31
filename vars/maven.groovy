#!/bin/groovy

def Build(applicationId = null) {
    sh "mvn -X package"
}

def InstallDependency() {
    sh "mvn clean install"
}

def SonarScan(projectKey = null, projectName = null) {
    sh """
        mvn admin:admin \
          -Dsonar.projectKey=${projectKey} \
          -Dsonar.projectName=${projectName} \
          -Dsonar.host.url=http://localhost:9000 \
    """
}