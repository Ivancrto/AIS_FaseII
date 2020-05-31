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
	       // Run the maven build
	      withEnv(["MVN_HOME=$mvnHome"]) {
	         if (isUnix()) {
	            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
	         } 
	         else {
	            bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
	         }
	      } 
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
}
