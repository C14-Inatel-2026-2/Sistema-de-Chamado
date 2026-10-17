
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {
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

        stage('Deploy Local') {
            steps {
                echo 'Iniciando deploy local do Sistema de Chamados'

                bat '''
            if not exist target\\sistema-chamados-manutencao-0.0.1-SNAPSHOT.jar (
                echo ERRO: arquivo JAR nao encontrado
                exit /b 1
            )

            start "" /B java -jar target\\sistema-chamados-manutencao-0.0.1-SNAPSHOT.jar
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