/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.start.site.extension.dependency.swagger;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * {@link BuildCustomizer} for Spring Session that provides explicit handling for the
 * modules introduced in Spring Session 2.
 *
 * @author Stephane Nicoll
 * @author Madhura Bhave
 */
public class SwaggerBuildCustomizer implements BuildCustomizer<Build> {

	@Override
	public void customize(Build build) {
		DependencyContainer dependencies = build.dependencies();
		dependencies.add("swagger-id-swagger2", "io.springfox", "springfox-swagger2", DependencyScope.COMPILE, "2.9.2");
		dependencies.remove("swagger-id");
		dependencies.add("swagger-id-swagger-ui", "io.springfox", "springfox-swagger-ui", DependencyScope.COMPILE,
				"2.9.2");
		dependencies.add("web");
	}

}
