package io.spring.start.site.extension.dependency.swagger;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class RedisBuildCustomizer implements BuildCustomizer<Build> {

	@Override
	public void customize(Build build) {
		DependencyContainer dependencies = build.dependencies();
		dependencies.add("data-redis");
		dependencies.remove("redis-id");
		dependencies.add("redis-id-jedis", "redis.clients", "jedis", DependencyScope.COMPILE);
	}
}
