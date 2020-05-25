package io.spring.start.site.extension.dependency.jenkins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

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

		Path jenkinsScriptsBuildImgFilepath_build = projectRoot.resolve("jenkins/build-img/build.sh");
		Files.createFile(jenkinsScriptsBuildImgFilepath_build);
		String build_sh_data = "# Copy the new jar to the build location\r\n" + 
				"cp -f ./target/*.jar jenkins/build-img/\r\n" + 
				"\r\n" + 
				"echo \"****************************\"\r\n" + 
				"echo \"** Building Docker Image ***\"\r\n" + 
				"echo \"****************************\"\r\n" + 
				"\r\n" + 
				"cd ./jenkins/build-img/ && docker build -t $API_NAME:$BUILD_TAG --no-cache .\r\n" + 
				"";
		Files.write(jenkinsScriptsBuildImgFilepath_build, build_sh_data.getBytes(),
				StandardOpenOption.APPEND);

		Path jenkinsScriptsBuildImgFilepath_docker = projectRoot.resolve("jenkins/build-img/Dockerfile");
		Files.createFile(jenkinsScriptsBuildImgFilepath_docker);
		String dockerFileData = "FROM openjdk:8-jre-alpine\r\n" + 
				"\r\n" + 
				"RUN mkdir app\r\n" + 
				"\r\n" + 
				"COPY *.jar /app/demo-0.0.1-SNAPSHOT.jar\r\n" + 
				"\r\n" + 
				"EXPOSE 8086\r\n" + 
				"\r\n" + 
				"ENTRYPOINT [\"java\",\"-jar\",\"/app/demo-0.0.1-SNAPSHOT.jar\"]\r\n" + 
				"";
		Files.write(jenkinsScriptsBuildImgFilepath_docker, dockerFileData.getBytes(),
				StandardOpenOption.APPEND);

		Path jenkinsScriptsBuildImgFilepath_mvm = projectRoot.resolve("jenkins/build-img/mvn.sh");
		Files.createFile(jenkinsScriptsBuildImgFilepath_mvm);
		String mvn_sh_data = "\r\n" + 
				"\r\n" + 
				"echo \"***************************\"\r\n" + 
				"echo \"** Building jar ***********\"\r\n" + 
				"echo \"***************************\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"mvn -DskipTests clean install\r\n" + 
				"";
		Files.write(jenkinsScriptsBuildImgFilepath_mvm, mvn_sh_data.getBytes(),
				StandardOpenOption.APPEND);

		Path jenkinsScriptsPushImgFilepath = projectRoot.resolve("jenkins/push-img/push.sh");
		Files.createFile(jenkinsScriptsPushImgFilepath);
		String push_sh_data = "\r\n" + 
				"\r\n" + 
				"echo \"********************\"\r\n" + 
				"echo \"** Pushing image ***\"\r\n" + 
				"echo \"********************\"\r\n" + 
				"\r\n" + 
				"IMAGE=$API_NAME\r\n" + 
				"\r\n" + 
				"echo \"** Logging in ***\"\r\n" + 
				"docker login -u $OCIR_USERNAME -p $OCIR_PASSWORD iad.ocir.io\r\n" + 
				"echo \"*** Tagging image ***\"\r\n" + 
				"docker tag $IMAGE:$BUILD_TAG $OCIR_REPOSITORY:$BUILD_TAG\r\n" + 
				"echo \"*** Pushing image ***\"\r\n" + 
				"docker push $OCIR_REPOSITORY:$BUILD_TAG\r\n" + 
				"\r\n" + 
				"";
		Files.write(jenkinsScriptsPushImgFilepath, push_sh_data.getBytes(),
				StandardOpenOption.APPEND);

	}

}
