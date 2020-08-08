def DependencyCheck(project = null, projectPath = null) {
    sh """
        /opt/dependency-check/bin/dependency-check.sh \
        --project ${project} \
        --scan ${projectPath} \
        --failOnCVSS 0
    """

    def reportFile = "${projectPath}/dependency-check-report.html"
    publishHTML(reportFiles: reportFile)
}