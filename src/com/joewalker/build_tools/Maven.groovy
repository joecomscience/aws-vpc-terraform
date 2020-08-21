package com.joewalker.build_tools

class Maven {

    void Build() {
        sh "mvn clean package"
    }

    void InstallDependency() {
        sh "mvn clean install"
    }
}
