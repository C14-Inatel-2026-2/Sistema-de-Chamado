
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
                echo 'Compilando e empacotando o projeto...'
                bat 'mvn -B clean -DskipTests package'

                bat '''
            if not exist target\\sistema-chamados-manutencao-0.0.1-SNAPSHOT.jar (
                echo ERRO: build nao gerou o arquivo JAR esperado
                exit /b 1
            )
            echo Build concluido com sucesso: JAR gerado em target\\
        '''
            }
        }


        stage('Tests and Coverage') {
            steps {
                bat 'mvn -B test'
                bat 'mvn -B jacoco:report'
            }
        }

        stage('Package') {
            steps {
                echo 'Empacotando o projeto...'

                bat 'mvn -B package -DskipTests'

                bat '''
                    if not exist target\\sistema-chamados-manutencao-0.0.1-SNAPSHOT.jar (
                        echo ERRO: package nao gerou o arquivo JAR esperado
                        exit /b 1
                    )

                    echo Package concluido com sucesso: JAR gerado em target\\
                '''
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
