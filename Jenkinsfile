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
			 		sh "mvn test"
			 	} 
				else {
		 			 bat("mvn test")
				}
	    
			}
       	}
     }
   }
	post {
      		always {
	  		  junit "target/surefire-reports/TEST-*.xml"
	      }	
     		 success {
         	
   	
   			  archiveArtifacts "log.txt"
    		  }
 	  }
}
