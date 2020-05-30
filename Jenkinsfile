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
          sh "cd tema1_4_ejem2  ; mvn package"
       }
     }
   } 
   
}
