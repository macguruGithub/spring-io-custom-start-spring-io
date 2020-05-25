package io.spring.start.site.extension.dependency.db;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectRequest;

public class DBProjectCustomizer implements BuildCustomizer<Build>{

	@Override
	public void customize(Build build) {
		
		ProjectRequest request = ProjectGenerationController.getZipRequest();
		String dbType = request.getDbRequest().getDbType();
		build.dependencies().remove("db-id");
		build.dependencies().add("data-jpa");
		if (dbType.equals("mysql")) {
			  build.dependencies().add("mssql-id", "com.microsoft.sqlserver", "mssql-jdbc", DependencyScope.RUNTIME);
		} else if (dbType.equals("mssql")) {
			  build.dependencies().add("mysql-id", "mysql", "mysql-connector-java", DependencyScope.RUNTIME);
		} else if (dbType.equals("oracle")) {
			  build.dependencies().add("ojdbc-id", "com.oracle", "ojdbc6", DependencyScope.RUNTIME,"11.2.0.3"); //this had been taken from private repository, if issue comes check with private repository
		}
	}

}
