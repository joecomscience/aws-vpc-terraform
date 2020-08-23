package com.joewalker.artifactory

class Nexus {
    public String GroupID;
    public String Version;
    public String Repository;
    public String ArtifactID;
    public String ArtifactFile;
    public String Extension = "jar";
    public String Classifire = "";
    private String CredentialId = "nexus";
    private String Host = this.global.NEXUS_HOST;
    def global;

    void UploadArtifactory() {
        this.global.nexusArtifactUploader(
                nexusVersion: "nexus3",
                protocol: "http",
                nexusUrl: this.Host,
                groupId: this.GroupID,
                version: this.Version,
                repository: this.Repository,
                credentialsId: this.CredentialId,
                artifacts: [
                        [
                                artifactId: this.ArtifactID,
                                classifier: this.Classifire,
                                file      : this.ArtifactFile,
                                type      : this.Extension,
                        ]
                ]
        )
    }

}
