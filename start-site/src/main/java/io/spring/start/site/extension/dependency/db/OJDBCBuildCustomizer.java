package io.spring.start.site.extension.dependency.db;

import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class OJDBCBuildCustomizer implements BuildCustomizer<MavenBuild> {

	@Override
	public void customize(MavenBuild build) {
		build.dependencies().add("data-jpa");
		//build.dependencies().add("db-driver", "com.db", "oracle-driver", DependencyScope.COMPILE, "11.1.0.7");
		build.plugins().add("org.apache.maven.plugins", "maven-dependency-plugin",plugin->{
			plugin.execution("id", executions ->{
				executions.goal("copy");
				executions.phase("package");
			});
			plugin.configuration(configuration->{
				configuration.add("overWriteReleases", "false").add("overWriteSnapshots", "true");
				configuration.configure("artifactItems", artifactItems->{
					artifactItems.configure("artifactItem", artifactItem->{
						artifactItem.add("groupId", "com.oracle");
						artifactItem.add("artifactId", "ojdbc6");
						artifactItem.add("version", "11.2.0.3");
						artifactItem.add("type", "jar");
						artifactItem.add("overWrite", "false");
						artifactItem.add("outputDirectory", "${project.basedir}/lib");
						artifactItem.add("destFileName", "ojdbc6-11.2.0.3.jar");
					});
				});
			});
			plugin.version("10.2.2");
		});
	}
	
}
