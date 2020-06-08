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
    /* Esta parte es mas que nada para hacer la prueba*/
 /*   stage("Create jar") {
        steps {
            script{
                if(isUnix()) {
                    sh "mvn package"
                }
                else{
                    bat(/"${MAVEN_HOME}\bin\mvn" package/)
                }
            }
        }
    }*/

	stage("Test") {
		steps {
			script {
		 		if(isUnix()) {
			 		sh "./mvnw test ; clean package"
			 	} 
				else {
		 			 bat(/"${MAVEN_HOME}\bin\mvn" test; clean package/)
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
         	
   			  archiveArtifacts  "target/resultado.jar"
    		  }
 	  }
}


