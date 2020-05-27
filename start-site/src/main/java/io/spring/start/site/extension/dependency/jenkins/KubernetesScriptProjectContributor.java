package io.spring.start.site.extension.dependency.jenkins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.web.VO.KubernetesRequest;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectRequest;

public class KubernetesScriptProjectContributor implements ProjectContributor {

	@Override
	public void contribute(Path projectRoot) throws IOException {

		Path migrationDirectory = projectRoot.resolve("src/main/resources");
		Files.createDirectories(migrationDirectory);
		Path targetFilepath = projectRoot.resolve("src/main/resources/kube-manifest.yml");
		Files.createFile(targetFilepath);
		ProjectRequest request = ProjectGenerationController.getZipRequest();
		KubernetesRequest kubernetesRequest = request.getKubernetesRequest();
		String name = kubernetesRequest.getName();
		String namespace = kubernetesRequest.getNamespace();
		String noOfrepicas = kubernetesRequest.getNoOfReplicas();
		String imageUrl = kubernetesRequest.getImageUrl();
		String port = kubernetesRequest.getPort();
		String targetPort = kubernetesRequest.getTargetPort();
		String data = "apiVersion: apps/v1\r\n" + 
				"kind: ReplicaSet\r\n" + 
				"metadata:\r\n" + 
				"  labels:\r\n" + 
				"    name: "+name+"\r\n" + 
				"  name: "+name+"\r\n" + 
				"  namespace: "+namespace+"\r\n" + 
				"spec:\r\n" + 
				"  replicas: "+noOfrepicas+"\r\n" + 
				"  selector:\r\n" + 
				"    matchLabels:\r\n" + 
				"      name: "+name+"\r\n" + 
				"  template:\r\n" + 
				"    metadata:\r\n" + 
				"      labels:\r\n" + 
				"        name: "+name+"\r\n" + 
				"    spec:\r\n" + 
				"      containers:\r\n" + 
				"        - image: '"+imageUrl+"'\r\n" + 
				"          imagePullPolicy: Always\r\n" + 
				"          name: "+name+"\r\n" + 
				"          ports:\r\n" + 
				"            - containerPort: 8086\r\n" + 
				"      imagePullSecrets:\r\n" + 
				"        - name: ocirsecret\r\n" + 
				"      nodeSelector:\r\n" + 
				"        node-pool: app-node-pool\r\n" + 
				"      restartPolicy: Always\r\n" + 
				"\r\n" + 
				"apiVersion: v1\r\n" + 
				"kind: Service\r\n" + 
				"metadata:\r\n" + 
				"  labels:\r\n" + 
				"    name: "+name+"\r\n" + 
				"  name: "+name+"\r\n" + 
				"  namespace: "+namespace+"\r\n" + 
				"spec:\r\n" + 
				"  ports:\r\n" + 
				"    - port: "+port+"\r\n" + 
				"      protocol: TCP\r\n" + 
				"      targetPort: "+targetPort+"\r\n" + 
				"  selector:\r\n" + 
				"    name: "+name+"\r\n" + 
				"  type: ClusterIP\r\n" + 
				"";
		Files.write(targetFilepath, data.getBytes(),
				StandardOpenOption.APPEND);
	
	}

}
