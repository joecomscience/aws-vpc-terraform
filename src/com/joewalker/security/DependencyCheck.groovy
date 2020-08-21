package com.joewalker.security

class DependencyCheck {
    public String ApplicationID;
    public String ProjectDir;
    public boolean EnableProxy;

    def steps;

    void Scan() {
        String proxyServer = System.getenv("PROXY_SERVER");
        String proxyport = System.getenv("PROXY_PORT");
        String reportFile = this.ProjectDir + "/dependency-check-report.html"

        if (this.EnableProxy) {
            this.steps.sh(
                    """
                        /home/dependency-check/bin/dependency-check.sh \
                        --artifactoryUseProxy true \
                        --proxyserver ${proxyServer} \
                        --proxyport ${proxyport} \
                        --project ${this.ApplicationID} \
                        --scan ${this.ProjectDir} \
                        --failOnCVSS 0
                    """
            )
        }

        publishHTML(reportFiles: reportFile)
    }
}