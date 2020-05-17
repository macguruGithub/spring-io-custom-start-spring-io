package io.spring.start.site.extension.dependency.db;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class MSSQLBuildCustomizer implements BuildCustomizer<Build> {

	@Override
	public void customize(Build build) {
		build.dependencies().add("mssql-id", "com.microsoft.sqlserver", "sqljdbc4", DependencyScope.COMPILE, "4.0");
	}

}