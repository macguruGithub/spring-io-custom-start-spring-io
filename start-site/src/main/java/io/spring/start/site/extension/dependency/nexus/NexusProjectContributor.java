package io.spring.start.site.extension.dependency.nexus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

public class NexusProjectContributor implements ProjectContributor  {

	@Override
	public void contribute(Path projectRoot) throws IOException {


		Path targetFilePath = projectRoot.resolve("nexus_settings.xml");
		Files.createFile(targetFilePath);
		String privateRepoConfigData = "<settings>\r\n" + 
				"  <!-- the path to the local repository - defaults to ~/.m2/repository\r\n" + 
				"  -->\r\n" + 
				"  <!-- <localRepository>/path/to/local/repo</localRepository>\r\n" + 
				"  -->\r\n" + 
				"    <mirrors>\r\n" + 
				"    <mirror> <!--Send all requests to the public group -->\r\n" + 
				"      <id>nexus</id>\r\n" + 
				"      <mirrorOf>*</mirrorOf>\r\n" + 
				"      <url>http://192.168.99.1:8081/repository/maven-public/</url>\r\n" + 
				"    </mirror>\r\n" + 
				"  </mirrors>\r\n" + 
				"  <activeProfiles>\r\n" + 
				"    <!--make the profile active all the time -->\r\n" + 
				"    <activeProfile>nexus</activeProfile>\r\n" + 
				"  </activeProfiles>\r\n" + 
				"  <profiles>\r\n" + 
				"    <profile>\r\n" + 
				"      <id>nexus</id>\r\n" + 
				"      <!--Override the repository (and pluginRepository) \"central\" from the Maven Super POM\r\n" + 
				"          to activate snapshots for both! -->\r\n" + 
				"      <repositories>\r\n" + 
				"        <repository>\r\n" + 
				"          <id>central</id>\r\n" + 
				"          <url>http://central</url>\r\n" + 
				"          <releases>\r\n" + 
				"            <enabled>true</enabled>\r\n" + 
				"          </releases>\r\n" + 
				"          <snapshots>\r\n" + 
				"            <enabled>true</enabled>\r\n" + 
				"          </snapshots>\r\n" + 
				"        </repository>\r\n" + 
				"      </repositories>\r\n" + 
				"      <pluginRepositories>\r\n" + 
				"        <pluginRepository>\r\n" + 
				"          <id>central</id>\r\n" + 
				"          <url>http://central</url>\r\n" + 
				"          <releases>\r\n" + 
				"            <enabled>true</enabled>\r\n" + 
				"          </releases>\r\n" + 
				"          <snapshots>\r\n" + 
				"            <enabled>true</enabled>\r\n" + 
				"          </snapshots>\r\n" + 
				"        </pluginRepository>\r\n" + 
				"      </pluginRepositories>\r\n" + 
				"    </profile>\r\n" + 
				"  </profiles>\r\n" + 
				"\r\n" + 
				"  <pluginGroups>\r\n" + 
				"    <pluginGroup>org.sonatype.plugins</pluginGroup>\r\n" + 
				"  </pluginGroups>\r\n" + 
				"\r\n" + 
				"  <servers>\r\n" + 
				"    <server>\r\n" + 
				"      <id>nexus</id>\r\n" + 
				"      <username>admin</username>\r\n" + 
				"      <password>admin123</password>\r\n" + 
				"    </server>\r\n" + 
				"  </servers>\r\n" + 
				"</settings>";
		Files.write(targetFilePath, privateRepoConfigData.getBytes(),
				StandardOpenOption.APPEND);

	
	}

}
