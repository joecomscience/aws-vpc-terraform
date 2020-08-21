package com.joewalker.build_tools

class Maven implements Serializable {
    def steps
    Maven(steps) {
        this.steps = steps
    }

    void Build() {
        println(this.steps.getClass())
        this.steps.sh "mvn clean package"
    }

    void InstallDependency() {
        sh "mvn clean install"
    }
}
