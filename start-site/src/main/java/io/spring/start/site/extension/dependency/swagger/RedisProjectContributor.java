package io.spring.start.site.extension.dependency.swagger;

import java.io.IOException;
import java.nio.file.Path;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.start.site.custom.CommonUtil;

public class RedisProjectContributor implements ProjectContributor {

	@Override
	public void contribute(Path projectRoot) throws IOException {
		
		String targetStr = "src/main/resources/application.xml";
		String srcStr = "src/main/resources/config/redisFile/redis.yml";
		Path targetFilepath = null;
		targetFilepath = CommonUtil.createFile(projectRoot, targetStr);
			CommonUtil.writeTargetFileFromSrc(projectRoot, targetFilepath, srcStr);

	}
}
