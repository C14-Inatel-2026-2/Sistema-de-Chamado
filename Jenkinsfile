
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

        stage('Deploy Externo') {
            steps {
                echo 'Disparando deploy externo no Render...'

                withCredentials([
                        string(
                                credentialsId: 'render-deploy-hook',
                                variable: 'RENDER_DEPLOY_HOOK'
                        )
                ]) {
                    powershell '''
                $response = Invoke-WebRequest `
                    -Uri $env:RENDER_DEPLOY_HOOK `
                    -Method Post `
                    -UseBasicParsing

                if ($response.StatusCode -ne 200 -and
                    $response.StatusCode -ne 202) {
                    throw "Falha ao iniciar deploy no Render. Status: $($response.StatusCode)"
                }

                Write-Host "Deploy solicitado ao Render com sucesso."
            '''
                }
            }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'

            recordCoverage(
                    tools: [[
                                    parser: 'JACOCO',
                                    pattern: 'target/site/jacoco/jacoco.xml'
                            ]],
                    id: 'jacoco',
                    name: 'JaCoCo Coverage'
            )
        }
    }
 
}
