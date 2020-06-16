def call(platform = null, app = null) {
    if (platform == "aws") {
        aws.DeployToECS(app)
    } else {
        gcp.DeployToGKE(app)
    }
}