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
         	credentialsId: '79:13:c0:cc:e2:5f:44:56:10:8f:08:14:a7:21:14:4b',
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
