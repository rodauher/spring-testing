pipeline {
    agent any
    options {
            ansiColor('xterm')
            timestamps()
            disableConcurrentBuilds()
            buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        }
    stages {
        stage('Build') {
            steps {
                //git branch: 'main', url: 'https://github.com/rodauher/Hello-Springboot.git'
                //sh "./gradlew test assemble"
                withGradle {
                sh "./gradlew test assemble"
                }
            }
            post {
                success {
                    junit 'build/test-results/test/*.xml'
                    archiveArtifacts 'build/libs/*jar'
                    jacoco 'build/reports/jacoco/test/*'
                }
            }
        }
        stage('Publish'){
            steps {
                sshagent(['github-ssh']) {
                sh 'git tag BUILD-1.0.${BUILD_NUMBER}'
                sh 'git push --tags'
                }
            }
        }
    }
}