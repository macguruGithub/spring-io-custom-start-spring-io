package io.spring.start.site.extension.dependency.nexus;

import org.springframework.stereotype.Service;

import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectRequest;

@Service
public class NexusBuildCustomizer implements BuildCustomizer<MavenBuild> {


	@Override
	public void customize(MavenBuild build) {
		ProjectRequest request = ProjectGenerationController.getZipRequest();
		
		DependencyContainer dependencies = build.dependencies();
		dependencies.remove("custom-id-nexus");
		request.getNexusDependencies().stream().forEach(dl -> {
			dependencies.add(dl.getId() + "-nexus", dl.getId(), dl.getName(), DependencyScope.COMPILE, dl
					.getVersion());
		});
		//dependencies.add("web");
		
//		Builder<?> withCoordinates = Dependency.withCoordinates("org.springframework.boot", "spring-boot-starter-test","","test");
//		withCoordinates.exclusions(new Exclusion("org.junit.vintage","junit-vintage-engine"));
//		dependencies.add("web", withCoordinates);
//		build.plugins().add("org.apache.maven.plugins", "maven-dependency-plugin");
		
		/*
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
		});*/
	}

}
