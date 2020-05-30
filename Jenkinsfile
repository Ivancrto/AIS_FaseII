pipeline {
   tools {
     maven "M3"
   }
   agent any
   stages {
     stage("Preparation") { 
       steps {
         git 'https://github.com/Ivancrto/AIS_FaseII.git'
	    
       }
     }
     stage("Test") {
       steps {     
	   script {
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
