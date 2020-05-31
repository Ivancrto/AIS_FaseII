pipeline {
   tools {
     maven "M3"
   }
   agent any
   stages {
     stage("Preparation") { 
       steps {
         git(
		 url: 'https://github.com/Ivancrto/AIS_FaseII.git',
		 credentialsId: 'developer',
		 branch: 'master'
	 )       
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
	sh "kill \$(cat AIS_FaseII/target/pid)"
	archive "AIS_FaseII/target/out.log"
	}
	success {
	archive "AIS_FaseII/target/*.jar"
	}
	}
   
}
