import groovy.json.JsonSlurperClassic
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    environment {
        NEXUS_REPOSITORY="repository-lab-mod4"
        NEXUS_CREDENTIALS=credentials('nexus-connect-lab-mod4')
    }
    stages {
        stage("Step 1: Compile Code"){
            steps {
                script {
                sh "echo 'Compile Code'"
                sh "./mvnw clean compile -e"
                }
            }
        }
        stage("Step 2: Testing"){
            steps {
                script {
                sh "echo 'Test Code!'"
                sh "./mvnw clean test -e"
                }
            }
        }
        stage("Step 3: Build .Jar"){
            steps {
                script {
                sh "echo 'Build .Jar!'"
                sh "./mvnw clean package -e"
                }
            }
        }
        stage("Step 4: SonarQube Analysis"){
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh "echo 'Calling sonar Service in another docker container!'"
                    // Run Maven on a Unix agent to execute Sonar.
                    sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=project-laboratorio-mod4 -Dsonar.projectName=project-laboratorio-mod4'
                }
            }
        }
        stage("Step 5: Upload Artifact to Nexus"){
            steps {
                script{
                    nexusPublisher nexusInstanceId: 'nexus',
                        nexusRepositoryId: '${NEXUS_REPOSITORY}',
                        packages: [
                            [$class: 'MavenPackage',
                                mavenAssetList: [
                                    [classifier: '',
                                    extension: 'jar',
                                    filePath: 'build/DevOpsUsach2020-0.0.1.jar'
                                ]
                            ],
                                mavenCoordinate: [
                                    artifactId: 'DevOpsUsach2020',
                                    groupId: 'com.devopsusach2020',
                                    packaging: 'jar',
                                    version: '0.0.1'
                                ]
                            ]
                        ]
                }
            }
        }
        stage("Step 6: Download from Nexus"){
            steps {
                script{
                    sh 'curl -X GET -u ${NEXUS_CREDENTIALS} "http://nexus:8081/repository/repository-lab-mod4/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar" -O'
                }
            }
        }
         stage("Step 7: Raise Artifact Jar on Jenkins server"){
            steps {
                script{
                    sh 'nohup java -jar DevOpsUsach2020-0.0.1.jar & >/dev/null'
                }
            }
        }
          stage("Step 8: Test Artifact - Sleep(Wait 20s)"){
            steps {
                script{
                    sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
                }
            }
        }
    }
    post {
        always {
            sh "echo 'fase always executed post'"
        }
        success {
            sh "echo 'fase success'"
        }
        failure {
            sh "echo 'fase failure'"
        }
    }
}