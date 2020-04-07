package io.spring.start.site.extension.dependency.jenkins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

public class JenkinsScriptProjectContributor implements ProjectContributor {

	@Override
	public void contribute(Path projectRoot) throws IOException {

		Path jenkinsScriptsBuildImgDirectory = projectRoot.resolve("jenkins/build-img");
		Files.createDirectories(jenkinsScriptsBuildImgDirectory);
		Path jenkinsScriptsPushImgDirectory = projectRoot.resolve("jenkins/push-img");
		Files.createDirectories(jenkinsScriptsPushImgDirectory);

		Path jenkinsScriptsFilepath = projectRoot.resolve("Jenkinsfile");
		Files.createFile(jenkinsScriptsFilepath);
		Path src_jenkinsScriptsFilepath = Paths.get("src/main/resources/config/jenkinsScripts/Jenkinsfile");
		Files.copy(src_jenkinsScriptsFilepath, jenkinsScriptsFilepath, StandardCopyOption.REPLACE_EXISTING);

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
