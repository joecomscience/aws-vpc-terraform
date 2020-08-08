#!/bin/groovy

def Scan(projectKey = null, projectName = null) {
    withSonarQubeEnv(installationName: "${SONAR_SERVER_NAME}") {
        sh """
            mvn -X sonar:sonar \
            -Dsonar.projectKey=${projectKey} \
            -Dsonar.projectName=${projectName} \
            -Dsonar.login=${SONAR_USER} \
            -Dsonar.password=${SONAR_PASS} \
            -Dsonar.host.url=${SONAR_HOST}
        """
    }

    timeout(time: 1, unit: 'HOURS') {
        def qg = waitForQualityGate(webhookSecretId: "joewalker")
        if (qg.status != 'OK') {
            error "Pipeline aborted due to quality gate failure: ${qg.status}"
        }
    }
}