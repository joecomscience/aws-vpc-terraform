#!/bin/groovy

def Build(applicationId = null) {
    sh "mvn clean install"
}