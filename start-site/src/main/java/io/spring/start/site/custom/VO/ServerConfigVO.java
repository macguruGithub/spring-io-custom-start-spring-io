package io.spring.start.site.custom.VO;

public class ServerConfigVO {

	private String port;
	private ServletConfigVo servlet;

	public ServerConfigVO setPort(String port) {

		this.port = port;

		return this;
	}

	public ServerConfigVO setServlet(ServletConfigVo servlet) {

		this.servlet = servlet;

		return this;

	}

	public String getPort() {
		return this.port;
	}

	public ServletConfigVo getServlet() {
		return this.servlet;
	}

}
