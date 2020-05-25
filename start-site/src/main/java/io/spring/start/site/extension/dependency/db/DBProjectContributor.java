package io.spring.start.site.extension.dependency.db;

import java.io.IOException;
import java.nio.file.Path;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.custom.CommonUtil;

public class DBProjectContributor implements ProjectContributor  {

	@Override
	public void contribute(Path projectRoot) throws IOException {


		String targetStr = "src/main/resources/application.xml";
		
		ProjectRequest request = ProjectGenerationController.getZipRequest();
		Path targetFilepath = null;
		targetFilepath = CommonUtil.createFile(projectRoot, targetStr);
		CommonUtil.writeFileForDB(request.getDbRequest(), targetFilepath);

	
	}

}
