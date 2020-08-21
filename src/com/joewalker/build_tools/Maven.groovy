package com.joewalker.build_tools

class Maven implements Serializable {
    def steps;

    void Build() {
        this.steps.sh("mvn clean package");
    }

    void InstallDependency() {
        this.steps.sh("mvn clean install")
    }
}
