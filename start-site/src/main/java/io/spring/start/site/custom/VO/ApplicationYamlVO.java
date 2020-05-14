package io.spring.start.site.custom.VO;

public class ApplicationYamlVO {

	private LoggingVO logging;
	private ServerConfigVO server;

	public ApplicationYamlVO(LoggingVO logging, ServerConfigVO server) {
		this.logging = logging;
		this.server = server;
	}

	public ApplicationYamlVO setLoggign(LoggingVO logging) {
		this.logging = logging;
		return this;
	}

	public LoggingVO getLogging() {
		return this.logging;
	}

	public ApplicationYamlVO setServer(ServerConfigVO server) {

		this.server = server;
		return this;
	}

	public ServerConfigVO getServer() {

		return this.server;
	}

}
