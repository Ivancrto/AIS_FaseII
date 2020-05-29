pipeline {
   tools {
     maven "M3"
   }
   agent any
   stages {
     stage("Preparation") { 
       steps {
         git(
         	url:  'https://github.com/Ivancrto/AIS_FaseII.git',
         	credentialsId: '2e:80:6e:40:36:91:0a:b8:57:82:b5:fb:7c:22:20:c7',
         	branch: 'master'
         )
       }
     }
     stage("Test") {
       steps {
         script{
         if(isUnix()) {
			sh "./mvnw test"
		} else {
			bat(/mvnw.cmd test/)
		}
		}
       }
     }
   } 
   post {
      always {
	    junit "AIS_FaseII/**/target/surefire-reports/TEST-*.xml"
      }
   }
}
