package io.spring.start.site.extension.dependency.db;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class MysqlORMBuildCustomizer implements BuildCustomizer<Build>{

	@Override
	public void customize(Build build) {
		build.dependencies().add("data-jpa");
	}
}
