package io.spring.start.site.extension.dependency.swagger;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class RedisBuildCustomizer implements BuildCustomizer<Build> {

	@Override
	public void customize(Build build) {
//		DependencyContainer dependencies = build.dependencies();
//		dependencies.add("swagger-id-swagger2", "io.springfox", "springfox-swagger2", DependencyScope.COMPILE, "2.9.2");
//		dependencies.remove("swagger-id");
//		dependencies.add("swagger-id-swagger-ui", "io.springfox", "springfox-swagger-ui", DependencyScope.COMPILE,
//				"2.9.2");
//		dependencies.add("web");
	}
}
