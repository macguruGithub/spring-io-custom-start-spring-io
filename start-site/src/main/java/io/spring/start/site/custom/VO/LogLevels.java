package io.spring.start.site.custom.VO;

import java.util.List;

public class LogLevels {

	private String contextPath;
	private List<String> logLevel;

	public LogLevels setContextPath(String contextPath) {
		this.contextPath = contextPath;
		return this;
	}

	public LogLevels setLogLevel(List<String> logLevel) {
		this.logLevel = logLevel;
		return this;
	}

	public List<String> getLogLevel() {
		return this.logLevel;
	}

	public String getContextPath() {
		return this.contextPath;
	}

}
