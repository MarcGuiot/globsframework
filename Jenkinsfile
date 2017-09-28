node {
    stage('Checkout') {
      checkout scm
    }

    stage('Build') {
      build = docker.image("maven").inside("--network=host") {

        stage('Environment') {
           withCredentials([[$class: "FileBinding", credentialsId: 'Nexus', variable: 'NEXUS_SETTINGS']]) {
             sh "mkdir -p ~/.m2"
             sh "cp -f ${NEXUS_SETTINGS} ~/.m2/settings.xml"
           }
        }

        stage('Test') {
          sh "mvn test"
          junit 'target/surefire-reports/*.xml'
        }

        if (env.BRANCH_NAME == 'master') {
            stage('Deploy') {
              sh "mvn deploy -Dmaven.test.skip=true"
            }
        }
      }
    }
}
