package com.joewalker.build_tools

class Maven implements Serializable {
    def steps
    Maven(steps) {
        this.steps = steps
    }

    void Build() {
        steps.sh "echo '--call maven build---'"
        steps.sh "mvn clean package"
    }

    void InstallDependency() {
        sh "mvn clean install"
    }
}
