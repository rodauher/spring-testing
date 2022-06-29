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
                withCredentials([usernamePassword(credentialsId: 'DockerGHCR', passwordVariable: 'TOKEN', usernameVariable: 'USERNAME')]){
                sh "./gradlew test assemble check pitest publish"
                jacoco execPattern: 'build/jacoco/*.exec'
                recordIssues(tools: [pmdParser(pattern: 'build/reports/pmd/*.xml')])
                recordIssues(tools: [pit(pattern: 'build/reports/pitest/*.xml')])
                }
            }
            }
            post {
                success {
                    junit 'build/test-results/test/*.xml'
                    archiveArtifacts 'build/libs/*jar'
                }
            }
        }
        stage('Publish'){
            steps {
            withGradle {
                wthCredentials([usernamePassword(credentialsId: 'DockerGHCR', passwordVariable: 'TOKEN', usernameVariable: 'USERNAME')]){
                sh "./gradlew publish"
                            }}
                sshagent(['github-ssh']) {
                sh 'git tag BUILD-1.0.${BUILD_NUMBER}'
                sh 'git push --tags'
                }
            }
        }
    }
}