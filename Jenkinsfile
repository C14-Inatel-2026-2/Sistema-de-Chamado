
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {
        stage('Validate') {
            steps {
                echo 'Validando o projeto Maven...'
                bat 'mvn -B validate'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B -DskipTests package'
            }
        }

        stage('Tests and Coverage') {
            steps {
                bat 'mvn -B test'
                bat 'mvn -B jacoco:report'
            }
        }

        stage('Deploy local demo') {
            steps {
                bat '''
                    for %%F in (target\\sistema-chamados-manutencao-0.0.1-SNAPSHOT.jar) do (
                        start "" /B cmd /c "java -jar %%F > target\\deploy.log 2>&1"
                    )
                '''
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml',
                  allowEmptyResults: true

            jacoco(
                execPattern: '**/target/jacoco.exec',
                classPattern: '**/target/classes',
                sourcePattern: '**/src/main/java'
            )

            archiveArtifacts(
                artifacts: 'target/site/jacoco/**,target/surefire-reports/*.xml,target/*.jar,target/deploy.log',
                allowEmptyArchive: true
            )
        }
    }
}