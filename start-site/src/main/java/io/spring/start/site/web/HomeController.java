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
import java.util.ArrayList;
import java.util.List;

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

		List<String> dialects;
		DBValuesResponse dbVal = new DBValuesResponse();
		if (dbType.equals("mysql")) {
			dialects = new ArrayList<String>();
			dialects.add("MySQLDialect");
			dialects.add("MySQLInnoDBDialect");
			dialects.add("MySQLMyISAMDialect");
			dialects.add("MySQL5Dialect");
			dialects.add("MySQL5InnoDBDialect");
			dialects.add("MySQL8Dialect");

			dbVal.setDialects(dialects);
			dbVal.setId("mysql-orm-id");
		}
		if (dbType.equals("mssql")) {
			dialects = new ArrayList<String>();
			dialects.add("SQLServerDialect");
			dialects.add("SQLServer2005Dialect");
			dialects.add("SQLServer2008Dialect");
			dialects.add("SQLServer2012Dialect");

			dbVal.setDialects(dialects);
			dbVal.setId("mssql-id");
		}
		if (dbType.equals("oracle")) {
			dialects = new ArrayList<String>();
			dialects.add("Oracle8iDialect");
			dialects.add("Oracle9iDialect");
			dialects.add("Oracle10gDialect");
			dialects.add("Oracle12cDialect");
			dialects.add("OracleTypesHelper");

			dbVal.setDialects(dialects);
			dbVal.setId("ojdbc-id");
		}

		List<String> ddlAuto = new ArrayList<String>();
		ddlAuto.add("validate");
		ddlAuto.add("update");
		ddlAuto.add("create");
		ddlAuto.add("create-drop");
		ddlAuto.add("none");

		dbVal.setDdlAuto(ddlAuto);

		return dbVal;
	}

	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String home() {
		return "forward:index.html";
	}

}
