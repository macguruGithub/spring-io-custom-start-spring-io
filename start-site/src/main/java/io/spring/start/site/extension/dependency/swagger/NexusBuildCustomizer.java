package io.spring.start.site.extension.dependency.swagger;

import org.springframework.stereotype.Service;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

@Service
public class NexusBuildCustomizer implements BuildCustomizer<Build> {

//	@Autowired
//	HttpServletRequest request;
	
	@Override
	public void customize(Build build) {
//		HttpSession session = request.getSession(false);
//		if(session != null) {
//		String test = session.getAttribute("name").toString();
//		System.out.println("Session Name got Here ---> "+test);
//		}
		DependencyContainer dependencies = build.dependencies();
		dependencies.remove("custom-id-nexus");
		//dependencies.add("redis-id-jedis", "redis.clients", "jedis", DependencyScope.COMPILE);
	}
}
