package io.spring.start.site.custom.VO;

import java.util.Map;

public class EnvironmentTypeRequest {
	// private List<String> envTypeList;
	private Map<String, LogLevels> envTypeList;
	private String applicationName;

	public EnvironmentTypeRequest setEnvTypeList(Map<String, LogLevels> envTypeList) {

		this.envTypeList = envTypeList;
		return this;

	}

	public Map<String, LogLevels> getEnvTypeList() {
		return this.envTypeList;
	}

	public EnvironmentTypeRequest setApplicationName(String applicationName) {
		this.applicationName = applicationName;
		return this;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

}
