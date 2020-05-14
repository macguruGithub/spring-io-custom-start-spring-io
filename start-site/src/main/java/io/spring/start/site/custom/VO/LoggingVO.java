package io.spring.start.site.custom.VO;

public class LoggingVO {

	private String config;

	public LoggingVO(String config) {
		this.config = config;
	}

	public LoggingVO() {

	}

	public LoggingVO setConfig(String config) {

		this.config = config;

		return this;
	}

	public String getConfig() {
		return this.config;
	}

}
