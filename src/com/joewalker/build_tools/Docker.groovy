package com.joewalker.build_tools

class Docker {
    public String JarFileLocation;
    public String DockerImageOutPut;
    public String DockerImageBuildVersion = "latest";

    def steps;

    void BuildJava() {
        boolean isJarFileNotEmpty = this.JarFileLocation != "" && this.JarFileLocation != null
        String dockerRegistryHost = System.getenv("DOCKER_PRIVATE_REGISTRY")

        if (isJarFileNotEmpty) {
            this.steps.sh(
                    """
                        cp ${this.JarFileLocation} app.jar
                        
                        docker \
                            build \
                            -t ${dockerRegistryHost}/${this.DockerImageOutPut}:${this.DockerImageBuildVersion}
                    """
            )
        } else {
            this.steps.sh(
                    """
                        docker \
                            build \
                            -t ${dockerRegistryHost}/${this.DockerImageOutPut}:${this.DockerImageBuildVersion}
                    """
            )
        }
    }
}
