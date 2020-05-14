package io.spring.start.site.custom.VO;

public class ServletConfigVo {

	private String contextPath;

	public ServletConfigVo setContextPath(String contextPath) {

		this.contextPath = contextPath;
		return this;
	}

	public String getContextPath() {
		return this.contextPath;
	}

}
