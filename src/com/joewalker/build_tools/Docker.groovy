package com.joewalker.build_tools

class Docker {
    public String JarFileLocation;
    public String DockerImageOutPut;
    public String DockerImageBuildVersion = "latest";
    private String RegistryHost = this.global.DOCKER_PRIVATE_REGISTRY;
    def global;

    void BuildJava() {
        boolean isJarFileNotEmpty = this.JarFileLocation != "" && this.JarFileLocation != null

        if (isJarFileNotEmpty) {
            this.global.sh(
                    """
                        cp ${this.JarFileLocation} app.jar
                        
                        docker \
                            build \
                            -t ${this.RegistryHost}/${this.DockerImageOutPut}:${this.DockerImageBuildVersion}
                    """
            )
        } else {
            this.global.sh(
                    """
                        docker \
                            build \
                            -t ${this.RegistryHost}/${this.DockerImageOutPut}:${this.DockerImageBuildVersion}
                    """
            )
        }
    }
}
