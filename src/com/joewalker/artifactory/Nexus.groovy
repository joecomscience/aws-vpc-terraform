package com.joewalker.artifactory

class Nexus {
    public String GroupID;
    public String Version;
    public String Repository;
    public String ArtifactID;
    public String ArtifactFile;
    public String Extension = "jar";

    def steps;

    void UploadArtifactory() {
        String nexusHost = NEXUS_HOST;
        String nexusCredential = "nexus";
        String classifier = "";

        this.steps.nexusArtifactUploader(
                nexusVersion: "nexus3",
                protocol: "http",
                nexusUrl: nexusHost,
                groupId: this.GroupID,
                version: this.Version,
                repository: this.Repository,
                credentialsId: nexusCredential,
                artifacts: [
                        [
                                artifactId: this.ArtifactID,
                                classifier: classifier,
                                file      : this.ArtifactFile,
                                type      : this.Extension,
                        ]
                ]
        )
    }

}
