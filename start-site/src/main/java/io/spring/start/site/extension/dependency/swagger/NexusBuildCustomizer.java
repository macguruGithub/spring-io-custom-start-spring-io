package io.spring.start.site.extension.dependency.swagger;

import java.util.List;

import org.springframework.stereotype.Service;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.web.VO.DependancyList;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectRequest;

@Service
public class NexusBuildCustomizer implements BuildCustomizer<Build> {

	private static List<DependancyList> list;

	@Override
	public void customize(Build build) {
		//ProjectGenerationController<ProjectRequest> conRequest = BeanUtil.getBean(ProjectGenerationController.class);
		ProjectRequest request = ProjectGenerationController.getZipRequest();
		
		DependencyContainer dependencies = build.dependencies();
		dependencies.remove("custom-id-nexus");
		request.getNexusDependencies().stream().forEach(dl ->{

			if(dl.getName().contains("ojdbc"))
				dependencies.add(dl.getId()+"-nexus", dl.getId(), dl.getName(), DependencyScope.SYSTEM, "11.2.0.3");
			else
				dependencies.add(dl.getId()+"-nexus", dl.getId(), dl.getName(), DependencyScope.COMPILE);
		
		});
	}

	public void getNexusDependancyList(List<DependancyList> depList) {
		//Getting the User selected Nexus list and setting it here.
		list = depList;
	}
}
