package io.spring.start.site.extension.dependency.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.start.site.custom.CommonUtil;

public class MysqlORMProjectContributor implements ProjectContributor {

	@Override
	public void contribute(Path projectRoot) throws IOException {
		String targetStr = "src/main/resources/application.xml";
		String srcStr = "src/main/resources/config/db/orm-db.yml";
		Path targetFilepath = null;
		targetFilepath = CommonUtil.createFile(projectRoot, targetStr);
		if (!CommonUtil.isDBConfigurationExists(targetFilepath)) {
			CommonUtil.writeTargetFileFromSrc(projectRoot, targetFilepath, srcStr);
		}
	}
}
