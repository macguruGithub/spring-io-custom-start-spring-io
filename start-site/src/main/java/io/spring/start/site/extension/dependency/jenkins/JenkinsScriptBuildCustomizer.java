package io.spring.start.site.extension.dependency.jenkins;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class JenkinsScriptBuildCustomizer implements BuildCustomizer<Build> {

	@Override
	public void customize(Build build) {
		build.dependencies().remove("jenkins-scripts-id");
	}

}
