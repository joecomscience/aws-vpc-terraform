class Docker {
    public String JarFileLocation;
    public String DockerImageOutPut;
    public String DockerImageBuildVersion = "latest";

    void BuildJava() {
        boolean isJarFileNotEmpty = this.JarFileLocation != "" && this.JarFileLocation != null
        String dockerRegistryHost = System.getenv("DOCKER_PRIVATE_REGISTRY")

        if (isJarFileNotEmpty) {
            sh """
                cp ${this.JarFileLocation} app.jar
                
                docker \
                    build \
                    -t ${dockerRegistryHost}/${this.DockerImageOutPut}:${this.DockerImageBuildVersion}
            """
        } else {
            sh """
                docker \
                    build \
                    -t ${dockerRegistryHost}/${this.DockerImageOutPut}:${this.DockerImageBuildVersion}
            """
        }
    }
}
