package io.spring.start.site.extension.dependency.jenkins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.web.VO.JenkinsRequest;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.custom.CommonUtil;

public class JenkinsScriptProjectContributor implements ProjectContributor {

	@Override
	public void contribute(Path projectRoot) throws IOException {

		ProjectRequest request = ProjectGenerationController.getZipRequest();
		Path jenkinsScriptsBuildImgDirectory = projectRoot.resolve("jenkins/build-img");
		Files.createDirectories(jenkinsScriptsBuildImgDirectory);
		Path jenkinsScriptsPushImgDirectory = projectRoot.resolve("jenkins/push-img");
		Files.createDirectories(jenkinsScriptsPushImgDirectory);

		Path jenkinsScriptsFilepath = projectRoot.resolve("Jenkinsfile");
		Files.createFile(jenkinsScriptsFilepath);
		String set1 = "pipeline {\r\n" + 
				"\r\n" + 
				"    agent any\r\n" + 
				"    \r\n" + 
				"    environment {";
		
		JenkinsRequest jenkinsRequest = request.getJenkinRequest();
		String dynamicData = "    environment {\r\n" + 
				"		API_NAME = '"+jenkinsRequest.getApiName()+"'\r\n" + 
				"        OCIR_PASSWORD = credentials('"+jenkinsRequest.getOcirPassword()+"') \r\n" + 
				"		OCIR_USERNAME = '"+jenkinsRequest.getOcirUsername()+"'\r\n" + 
				"		OCIR_REPOSITORY = '"+jenkinsRequest.getOcirRepository()+"'\r\n" + 
				"		OCIR_REGISTRY = '"+jenkinsRequest.getOcirRegistry()+"'\r\n" + 
				"		BUILD_TAG = '"+jenkinsRequest.getBuildTag()+"'";
		
		String set2 = "}\r\n" + 
				"	\r\n" + 
				"    stages {\r\n" + 
				"\r\n" + 
				"        stage('Build') {\r\n" + 
				"            steps {\r\n" + 
				"                sh '''\r\n" + 
				"					 echo '********************************'\r\n" + 
				"					 echo \"Building $API_NAME jar file\"\r\n" + 
				"					 echo '********************************'\r\n" + 
				"                    ./jenkins/build-img/mvn.sh\r\n" + 
				"					 echo '***********************************************'\r\n" + 
				"					 echo \"Success: Building $API_NAME jar file\"\r\n" + 
				"					 echo '***********************************************'\r\n" + 
				"					 echo '***********************************************************'\r\n" + 
				"					 echo \"Building and tag $API_NAME image using docker compose\"\r\n" + 
				"					 echo '***********************************************************'\r\n" + 
				"                    ./jenkins/build-img/build.sh\r\n" + 
				"					 echo '**************************************************************************'\r\n" + 
				"					 echo \"Success: Building and tag $API_NAME image using docker compose\"\r\n" + 
				"					 echo '**************************************************************************'\r\n" + 
				"                '''\r\n" + 
				"            }\r\n" + 
				"            post {\r\n" + 
				"                success {\r\n" + 
				"                   archiveArtifacts artifacts: 'target/*.jar', fingerprint: true\r\n" + 
				"                }\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        stage('Test') {\r\n" + 
				"            steps {\r\n" + 
				"               echo 'Test stage'\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        stage('Push-To-OCI-Registry') {\r\n" + 
				"            steps {\r\n" + 
				"				echo '********************************'\r\n" + 
				"				echo \"Login to OCIR and push the image\"\r\n" + 
				"				echo '********************************'\r\n" + 
				"                sh './jenkins/push-img/push.sh'\r\n" + 
				"				echo '*********************************************'\r\n" + 
				"				echo \"Success: Pushing the image to OCI is complete\"\r\n" + 
				"				echo '*********************************************'\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"		\r\n" + 
				"    }\r\n" + 
				"}\r\n" + 
				"";
		
		CommonUtil.writeToFile(set1, jenkinsScriptsFilepath);
		CommonUtil.writeToFile(dynamicData, jenkinsScriptsFilepath);
		CommonUtil.writeToFile(set2, jenkinsScriptsFilepath);
		//Path src_jenkinsScriptsFilepath = Paths.get("src/main/resources/config/jenkinsScripts/Jenkinsfile");
		//Files.copy(src_jenkinsScriptsFilepath, jenkinsScriptsFilepath, StandardCopyOption.REPLACE_EXISTING);

		Path jenkinsScriptsBuildImgFilepath_build = projectRoot.resolve("jenkins/build-img/build.sh");
		Files.createFile(jenkinsScriptsBuildImgFilepath_build);
		Path src_jenkinsScriptsBuildImgFilepath_build = Paths
				.get("src/main/resources/config/jenkinsScripts/build-img/build.sh");
		Files.copy(src_jenkinsScriptsBuildImgFilepath_build, jenkinsScriptsBuildImgFilepath_build,
				StandardCopyOption.REPLACE_EXISTING);

		Path jenkinsScriptsBuildImgFilepath_docker = projectRoot.resolve("jenkins/build-img/Dockerfile");
		Files.createFile(jenkinsScriptsBuildImgFilepath_docker);
		Path src_jenkinsScriptsBuildImgFilepath_docker = Paths
				.get("src/main/resources/config/jenkinsScripts/build-img/Dockerfile");
		Files.copy(src_jenkinsScriptsBuildImgFilepath_docker, jenkinsScriptsBuildImgFilepath_docker,
				StandardCopyOption.REPLACE_EXISTING);

		Path jenkinsScriptsBuildImgFilepath_mvm = projectRoot.resolve("jenkins/build-img/mvn.sh");
		Files.createFile(jenkinsScriptsBuildImgFilepath_mvm);
		Path src_jenkinsScriptsBuildImgFilepath_mvm = Paths
				.get("src/main/resources/config/jenkinsScripts/build-img/mvn.sh");
		Files.copy(src_jenkinsScriptsBuildImgFilepath_mvm, jenkinsScriptsBuildImgFilepath_mvm,
				StandardCopyOption.REPLACE_EXISTING);

		Path jenkinsScriptsPushImgFilepath = projectRoot.resolve("jenkins/push-img/push.sh");
		Files.createFile(jenkinsScriptsPushImgFilepath);
		Path src_jenkinsScriptsPushImgFilepath = Paths.get("src/main/resources/config/jenkinsScripts/push-img/push.sh");
		Files.copy(src_jenkinsScriptsPushImgFilepath, jenkinsScriptsPushImgFilepath,
				StandardCopyOption.REPLACE_EXISTING);

	}

}
