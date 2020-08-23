package com.joewalker.security

class SonarQube {
    public String ProjectKey;
    public String ProjectName;
    public String Token = this.steps.SONAR_TOEN;
    public String Host = this.steps.SONAR_HOST;
    public String CredentialID = "sonar";

    def steps;

    void Scan() {
        this.steps.withCredentials([
                [
                        $class          : "UsernamePasswordMultiBinding",
                        credentialsId   : this.CredentialID,
                        usernameVariable: "username",
                        passwordVariable: "password"
                ]
        ]) {
            this.steps.sh(
                """
                    mvn -X \${username}:\${password} \
                      -Dsonar.projectKey=${this.ProjectKey} \
                      -Dsonar.projectName=${this.ProjectName} \
                      -Dsonar.login=${this.Token} \
                      -Dsonar.host.url=${this.Host}
                """
            )
        }
    }
}
