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
package io.spring.start.site.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import io.spring.initializr.web.VO.DependancyList;
import io.spring.start.site.custom.VO.DBValuesResponse;
import io.spring.start.site.custom.VO.DependancyResp;
import io.spring.start.site.custom.VO.DependancyValues;
import io.spring.start.site.custom.VO.NexusDependancyItems;
import io.spring.start.site.custom.VO.NexusDependancyResponse;
import io.spring.start.site.extension.dependency.nexus.NexusBuildCustomizer;

/**
 * Main Controller.
 *
 * @author Brian Clozel
 */
@Controller
@CrossOrigin(origins = "*")
public class HomeController {

	@Autowired
	HttpServletRequest request;

	@GetMapping(path = "/nexusSetup", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DependancyResp getNexusRepo() {
		final String uri = "http://localhost:8081/service/rest/v1/search?repository=maven-releases";

		RestTemplate restTemplate = new RestTemplate();
		NexusDependancyResponse result = restTemplate.getForObject(uri, NexusDependancyResponse.class);

		DependancyResp response = new DependancyResp();
		List<DependancyValues> valuesList = new ArrayList<DependancyValues>();
		DependancyValues values = new DependancyValues();
		List<DependancyList> list = new ArrayList<DependancyList>();
		DependancyList dependancyList;

		for (NexusDependancyItems items : result.getItems()) {
			
			dependancyList = new DependancyList();
			dependancyList.setVersion(items.getVersion());
			dependancyList.setName(items.getName());
			dependancyList.setId(items.getName() + "-nexus");
			dependancyList.setDescription("Taken from " + items.getRepository() + " Repository.");
			if(!"ojdbc6".equalsIgnoreCase(items.getName()))
				list.add(dependancyList);
		}

		DependancyList d = new DependancyList();
		d.setId("web");
		d.setName("Spring Web");
		d.setDescription("Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.");
		list.add(d);
		values.setName("Nexus Dependancies");
		values.setValues(list);
		valuesList.add(values);

		response.setType("hierarchical-multi-select");
		response.setValues(valuesList);

		return response;
	}
	
	@GetMapping(path = "/custom/metadata/client", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getData() {
		Map<String,Object> data = new HashMap<>();
		data.put("nexus", getNexusRepo());
		List<DependancyList> addOnList = new ArrayList<>();
		addOnList.add(new DependancyList("env-logback","Environment Configuration","Used to generate and configure environment specific applicaiton yaml and logback files on the project resource."));
		addOnList.add(new DependancyList("swagger-id","Swagger","An open-source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful web services."));
		addOnList.add(new DependancyList("redis-id","Redis Session","An in-memory data structure project implementing a distributed, in-memory key-value database with optional durability."));
		addOnList.add(new DependancyList("db-id","DataBase","Able to configure various database management system."));
		//addOnList.add(new DependancyList("exception-id","Exception Handling","One of the powerful mechanism to handle the runtime errors so that normal flow of the application can be maintained."));
		addOnList.add(new DependancyList("","Pipelines","An open-source container-orchestration system for automating application"));
		data.put("addOns", addOnList);
		data.put("dbDetails", getHibernateValues());
		return ResponseEntity.ok(data);
	}

	@GetMapping(path = "/getIdForAddOns", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,String> getIdForAddOns() {
		Map<String, String> idList = new HashMap<String, String>();
		idList.put("Swagger", "swagger-id");
		idList.put("Jenkins Scripts", "jenkins-scripts-id");
		idList.put("Kubernetes Script", "kube-script-id");
		idList.put("Redis", "redis-id");
		idList.put("Exception", "exception-id");
		idList.put("Environment Configuration", "env-logback");
		
		return idList;
		
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> getHibernateValues() {

		
		Map<String, String> ddlAuto = new HashMap<String, String>();
		ddlAuto.put("validate", "validate the schema, makes no changes to the database.");
		ddlAuto.put("update", "update the schema.");
		ddlAuto.put("create", "creates the schema, destroying previous data.");
		ddlAuto.put("create-drop",
				"drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.");
		ddlAuto.put("none", "does nothing with the schema, makes no changes to the database");
		
		
		List<DBValuesResponse> dbDataList = new ArrayList<>();
		
		
		DBValuesResponse mysql_db = new DBValuesResponse();
		Map<String, String> mysql_dialects = new HashMap<String, String>();
		mysql_db.setDb("mysql");
		mysql_dialects.put("MySQLDialect", "An SQL dialect for MySQL (prior to 5.x).");
		mysql_dialects.put("MySQLInnoDBDialect",
					"Deprecated Use “hibernate.dialect.storage_engine=innodb” environment variable or JVM system property instead.");
		mysql_dialects.put("MySQLMyISAMDialect",
					"Deprecated Use “hibernate.dialect.storage_engine=myisam” environment variable or JVM system property instead.");
		mysql_dialects.put("MySQL5Dialect", "An SQL dialect for MySQL 5.x specific features.");
		mysql_dialects.put("MySQL5InnoDBDialect",
					"Deprecated Use “hibernate.dialect.storage_engine=innodb” environment variable or JVM system property instead.");
		mysql_dialects.put("MySQL8Dialect", "An SQL dialect for MySQL 8.x specific features.");
		mysql_db.setDialects(mysql_dialects);
		dbDataList.add(mysql_db);
		
		DBValuesResponse mssql_db = new DBValuesResponse();
		Map<String, String> mssql_dialects = new HashMap<String, String>();
		mssql_db.setDb("mssql");
		mssql_dialects.put("SQLServerDialect", "A dialect for Microsoft SQL Server 2000");
		mssql_dialects.put("SQLServer2005Dialect", "A dialect for Microsoft SQL 2005.");
		mssql_dialects.put("SQLServer2008Dialect",
				"A dialect for Microsoft SQL Server 2008 with JDBC Driver 3.0 and above");
		mssql_dialects.put("SQLServer2012Dialect", "Microsoft SQL Server 2012 Dialect");
		mssql_db.setDialects(mssql_dialects);
		dbDataList.add(mssql_db);
		
		DBValuesResponse oracle_db = new DBValuesResponse();
		Map<String, String> oracle_dialects = new HashMap<String, String>();
		oracle_db.setDb("oracle");
		oracle_dialects.put("Oracle8iDialect", "A dialect for Oracle 8i.");
		oracle_dialects.put("Oracle9iDialect", "A dialect for Oracle 9i databases.");
		oracle_dialects.put("Oracle10gDialect", "A dialect specifically for use with Oracle 10g.");
		oracle_dialects.put("Oracle12cDialect", "An SQL dialect for Oracle 12c.");
		oracle_dialects.put("OracleTypesHelper", "A Helper for dealing with the OracleTypes class");
		oracle_db.setDialects(mssql_dialects);
		dbDataList.add(oracle_db);
		
		Map data = new HashMap<>();
		data.put("id", "db-id");
		data.put("dllAuto", ddlAuto);
		data.put("dbDetailList", dbDataList);

		return ResponseEntity.ok(data);
	}

	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String home() {
		return "forward:index.html";
	}

//	@RequestMapping(path = "/logback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public void generateLogbackEnvFile(@RequestBody EnvironmentTypeRequest environmentTypeRequest) {
//		System.out.println("Logback Request -> " +environmentTypeRequest);
//		LogbackProjectContributor logbackProjectContributor = new LogbackProjectContributor();
//		logbackProjectContributor.generateEnvLogBackFiles(environmentTypeRequest);
//
//	}
	
	
}
