package io.spring.start.site.extension.dependency.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

public class MysqlORMProjectContributor implements ProjectContributor {

	@Override
	public void contribute(Path projectRoot) throws IOException {
		Path migrationDirectory = projectRoot.resolve("src/main/resources");
		Files.createDirectories(migrationDirectory);
		Path targetFilepath = projectRoot.resolve("src/main/resources/application.yml");
		Files.createFile(targetFilepath);
		Path srcFilepath = Paths.get("src/main/resources/config/db/orm-db.yml");
		Files.copy(srcFilepath, targetFilepath,StandardCopyOption.REPLACE_EXISTING);
		
	}

}
