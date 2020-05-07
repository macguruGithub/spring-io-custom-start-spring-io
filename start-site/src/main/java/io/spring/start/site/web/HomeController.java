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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spring.start.site.custom.CommonUtil;
import io.spring.start.site.custom.VO.DBTypeRequest;
import io.spring.start.site.custom.VO.DBValuesResponse;

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

	@GetMapping(path = "/nexusSetup", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getNexusRepo() {
		// @RequestParam (value = "name") String name
		String name = "Sampleeeeeeeeee";
//		HttpSession session = request.getSession(true);
//		session.setAttribute("name", name);
		return name;
	}

	@RequestMapping(path = "/writeDBValues", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void writeDBValuesInYml(@RequestBody DBTypeRequest typeRequest) {
		try {
			CommonUtil.writeFileForDB(typeRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping(path = "/getHibernateValues", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DBValuesResponse getHibernateValues(@RequestParam(value = "dbType") String dbType) {

		Map<String,String> dialects;
		DBValuesResponse dbVal = new DBValuesResponse();
		if (dbType.equals("mysql")) {
			dialects = new HashMap<String, String>();
			dialects.put("MySQLDialect", "An SQL dialect for MySQL (prior to 5.x).");
			dialects.put("MySQLInnoDBDialect", "Deprecated Use “hibernate.dialect.storage_engine=innodb” environment variable or JVM system property instead.");
			dialects.put("MySQLMyISAMDialect", "Deprecated Use “hibernate.dialect.storage_engine=myisam” environment variable or JVM system property instead.");
			dialects.put("MySQL5Dialect", "An SQL dialect for MySQL 5.x specific features.");
			dialects.put("MySQL5InnoDBDialect", "Deprecated Use “hibernate.dialect.storage_engine=innodb” environment variable or JVM system property instead.");
			dialects.put("MySQL8Dialect", "An SQL dialect for MySQL 8.x specific features.");

			dbVal.setDialects(dialects);
			dbVal.setId("mysql-orm-id");
		}
		if (dbType.equals("mssql")) {
			dialects = new HashMap<String, String>();
			dialects.put("SQLServerDialect", "A dialect for Microsoft SQL Server 2000");
			dialects.put("SQLServer2005Dialect", "A dialect for Microsoft SQL 2005.");
			dialects.put("SQLServer2008Dialect", "A dialect for Microsoft SQL Server 2008 with JDBC Driver 3.0 and above");
			dialects.put("SQLServer2012Dialect", "Microsoft SQL Server 2012 Dialect");

			dbVal.setDialects(dialects);
			dbVal.setId("mssql-id");
		}
		if (dbType.equals("oracle")) {
			dialects = new HashMap<String, String>();
			dialects.put("Oracle8iDialect", "A dialect for Oracle 8i.");
			dialects.put("Oracle9iDialect", "A dialect for Oracle 9i databases.");
			dialects.put("Oracle10gDialect", "A dialect specifically for use with Oracle 10g.");
			dialects.put("Oracle12cDialect", "An SQL dialect for Oracle 12c.");
			dialects.put("OracleTypesHelper", "A Helper for dealing with the OracleTypes class");

			dbVal.setDialects(dialects);
			dbVal.setId("ojdbc-id");
		}

		Map<String,String> ddlAuto = new HashMap<String, String>();
		ddlAuto.put("validate", "validate the schema, makes no changes to the database.");
		ddlAuto.put("update", "update the schema.");
		ddlAuto.put("create", "creates the schema, destroying previous data.");
		ddlAuto.put("create-drop", "drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.");
		ddlAuto.put("none", "does nothing with the schema, makes no changes to the database");

		dbVal.setDdlAuto(ddlAuto);

		return dbVal;
	}

	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String home() {
		return "forward:index.html";
	}

}
