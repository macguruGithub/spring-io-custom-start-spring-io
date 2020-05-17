package io.spring.start.site.extension.envlogback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.start.site.custom.CommonUtil;
import io.spring.start.site.custom.VO.ApplicationYamlVO;
import io.spring.start.site.custom.VO.EnvironmentTypeRequest;
import io.spring.start.site.custom.VO.LogLevels;
import io.spring.start.site.custom.VO.LoggingVO;
import io.spring.start.site.custom.VO.ServerConfigVO;
import io.spring.start.site.custom.VO.ServletConfigVo;

public class LogbackProjectContributor implements ProjectContributor {
	public static String localFilePath = "src/main/resources/logging";
	public static String yamlFolderPath = "src/main/resources/config/envyaml";

	@Override
	public void contribute(Path projectRoot) throws IOException {
		String envLogTargetStr = "src/main/resources/logging/";
		String envLogSrcStr = "src/main/resources/logging/";

		File envLogFolder = new File(localFilePath);
		String[] envFilesList = envLogFolder.list();
		for (int i = 0; i < envFilesList.length; i++) {
			Path targetFilepath = null;
			targetFilepath = CommonUtil.createFile(projectRoot, envLogTargetStr + envFilesList[i]);
			CommonUtil.writeTargetFileFromSrc(projectRoot, targetFilepath, envLogSrcStr + envFilesList[i]);
		}

		String envYamlSrcStr = "src/main/resources/config/envyaml/";
		String envYamlTargetStr = "src/main/resources/config/";

		File envYamlFolder = new File(yamlFolderPath);
		String[] envYamlFilesList = envYamlFolder.list();
		for (int i = 0; i < envYamlFilesList.length; i++) {
			Path targetFilepath = null;
			targetFilepath = CommonUtil.createFile(projectRoot, envYamlTargetStr + envYamlFilesList[i]);
			CommonUtil.writeTargetFileFromSrc(projectRoot, targetFilepath, envYamlSrcStr + envYamlFilesList[i]);
		}

		/*
		 * deleteFolderAlongWithFiles(envLogSrcStr);  //for deleting folders along with files
		 * deleteFolderAlongWithFiles(envYamlSrcStr);
		 */

	}

	/*
	 * public void deleteFolderAlongWithFiles(String folderPath) { if (new
	 * File(folderPath).exists()) { String[] files = new File(folderPath).list();
	 * for (String file : files) { File currentFile = new File(new
	 * File(folderPath).getPath(), file); currentFile.delete(); } new
	 * File(folderPath).delete(); }
	 * 
	 * }
	 */

	public void generateEnvLogBackFiles(EnvironmentTypeRequest environmentTypeRequest) {

		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;

		try {
			icBuilder = icFactory.newDocumentBuilder();
			File folder = new File(localFilePath);
			File yamlFolder = new File(yamlFolderPath);
			folder.mkdir();
			yamlFolder.mkdir();
			environmentTypeRequest.getEnvTypeList().entrySet().stream().forEach(action -> {

				String env = action.getKey(); // env name
				LogLevels logLevels = action.getValue();
				String contextPath = logLevels.getContextPath(); // conetxtpath
				Iterator<String> loggerLevel = logLevels.getLogLevel().listIterator(); // logger levels

				// while (envList.hasNext()) {
				// String logLevel=envList.next();
				Document doc = icBuilder.newDocument();
				Element mainRootElement = doc.createElement("configuration");
				mainRootElement.setAttribute("scan", "true");
				mainRootElement.setAttribute("scanPeriod", "30 seconds");
				doc.appendChild(mainRootElement);

				// append child elements to root element
				mainRootElement.appendChild(CommonUtil.createElement(doc));
				Element property = doc.createElement("property");
				property.setAttribute("name", "logpath");
				property.setAttribute("value", "/app/logs/" + environmentTypeRequest.getApplicationName() + "/");

				mainRootElement.appendChild(property);

				// Comment comment=doc.createComment("<!-- Console appender -->\r\n");
				Element conAppender = doc.createElement("appender");
				conAppender.setAttribute("name", "console");
				conAppender.setAttribute("class", "ch.qos.logback.core.ConsoleAppender");
				Element encoder = doc.createElement("encoder");
				// doc.createComment("<!-- <pattern>%date [%thread] |-%-5level %logger{0} ::
				// %msg%n</pattern> -->\r\n");
				Element pattern = doc.createElement("pattern");
				pattern.appendChild(doc.createTextNode(
						"%date [%thread] |-%-5level %logger{0} :: %msg, TraceId:= %X{X-B3-TraceId:-}, SpanId:= %X{X-B3-SpanId:-}%n"));
				encoder.appendChild(pattern);
				conAppender.appendChild(encoder);
				mainRootElement.appendChild(conAppender);

				/*
				 * COMMONROLLER
				 */

				Element COMMON_ROLLER = doc.createElement("appender");
				COMMON_ROLLER.setAttribute("name", "COMMON_ROLLER");
				COMMON_ROLLER.setAttribute("class", "ch.qos.logback.core.rolling.RollingFileAppender");

				Element file = doc.createElement("file");
				file.appendChild(
						doc.createTextNode("${logpath}/" + environmentTypeRequest.getApplicationName() + ".log"));
				COMMON_ROLLER.appendChild(file);
				Element rollingPolicy = doc.createElement("rollingPolicy");
				rollingPolicy.setAttribute("class", "ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
				Element fileNamePattern = doc.createElement("fileNamePattern");
				fileNamePattern.appendChild(
						doc.createTextNode("${logpath}/" + environmentTypeRequest.getApplicationName() + "-%d.zip"));
				Element maxHistory = doc.createElement("maxHistory");
				maxHistory.appendChild(doc.createTextNode("15"));
				Element totalSizeCap = doc.createElement("totalSizeCap");
				totalSizeCap.appendChild(doc.createTextNode("10MB"));

				rollingPolicy.appendChild(fileNamePattern);
				rollingPolicy.appendChild(maxHistory);
				rollingPolicy.appendChild(totalSizeCap);

				COMMON_ROLLER.appendChild(rollingPolicy);
				Element layout = doc.createElement("layout");
				layout.setAttribute("class", "ch.qos.logback.classic.PatternLayout");

				// doc.createComment("<!-- <pattern>%date [%thread] |-%-5level %logger{0} ::
				// %msg%n</pattern> -->\r\n");

				Element pattern1 = doc.createElement("pattern");
				pattern1.appendChild(doc.createTextNode(
						"%date [%thread] |-%-5level %logger{0} :: %msg, TraceId:= %X{X-B3-TraceId:-}, SpanId:= %X{X-B3-SpanId:-}%n"));
				layout.appendChild(pattern1);
				COMMON_ROLLER.appendChild(layout);
				mainRootElement.appendChild(COMMON_ROLLER);

				/*
				 * HTTP ROLLER
				 */

				Element HTTP_ROLLER = doc.createElement("appender");
				HTTP_ROLLER.setAttribute("name", "HTTP_ROLLER");
				HTTP_ROLLER.setAttribute("class", "ch.qos.logback.core.rolling.RollingFileAppender");
				Element file1 = doc.createElement("file");
				file1.appendChild(
						doc.createTextNode("${logpath}/" + environmentTypeRequest.getApplicationName() + "-http.log"));
				HTTP_ROLLER.appendChild(file1);
				Element rollingPolicy1 = doc.createElement("rollingPolicy");
				rollingPolicy1.setAttribute("class", "ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
				Element fileNamePattern1 = doc.createElement("fileNamePattern");
				fileNamePattern1.appendChild(doc
						.createTextNode("${logpath}/" + environmentTypeRequest.getApplicationName() + "-http-%d.zip"));
				Element maxHistory1 = doc.createElement("maxHistory");
				maxHistory1.appendChild(doc.createTextNode("15"));
				Element totalSizeCap1 = doc.createElement("totalSizeCap");
				totalSizeCap1.appendChild(doc.createTextNode("1MB"));
				rollingPolicy1.appendChild(fileNamePattern1);
				rollingPolicy1.appendChild(maxHistory1);
				rollingPolicy1.appendChild(totalSizeCap1);
				HTTP_ROLLER.appendChild(rollingPolicy1);

				Element layout1 = doc.createElement("layout");
				layout1.setAttribute("class", "ch.qos.logback.classic.PatternLayout");

				doc.createComment("<!-- <pattern>%date [%thread] |-%-5level %logger{0} :: %msg%n</pattern> -->\r\n");
				Element pattern2 = doc.createElement("pattern");
				pattern2.appendChild(doc.createTextNode(
						"%date [%thread] |-%-5level %logger{0} :: %msg, TraceId:= %X{X-B3-TraceId:-}, SpanId:= %X{X-B3-SpanId:-}%n"));
				layout1.appendChild(pattern2);
				HTTP_ROLLER.appendChild(layout1);
				mainRootElement.appendChild(HTTP_ROLLER);
				while (loggerLevel.hasNext()) {
					String logLevel = loggerLevel.next();

					mainRootElement.appendChild(CommonUtil.getLogLevel(doc, "org.springframework.core", logLevel));
					mainRootElement.appendChild(CommonUtil.getLogLevel(doc, "org.apache.http.wire", logLevel));
					mainRootElement.appendChild(CommonUtil.getLogLevel(doc, "org.springframework.beans", logLevel));
					mainRootElement.appendChild(CommonUtil.getLogLevel(doc, "org.springframework.context", logLevel));
					mainRootElement.appendChild(CommonUtil.getLogLevel(doc, "org.springframework.web", logLevel));
					Element root = doc.createElement("root");
					root.setAttribute("level", logLevel);
					root.appendChild(CommonUtil.getAppenderReference(doc, "COMMON_ROLLER"));
					root.appendChild(CommonUtil.getAppenderReference(doc, "CONSOLE"));
					root.appendChild(CommonUtil.getAppenderReference(doc, "HTTP_ROLLER"));
					mainRootElement.appendChild(root);
				}
				// Element root = CommonUtil.createElementWithAttr(doc, "root", "level",
				// "info");
//					Element root = doc.createElement("root");
//					root.setAttribute("level", "info");
//					root.appendChild(CommonUtil.getAppenderReference(doc, "COMMON_ROLLER"));
//					root.appendChild(CommonUtil.getAppenderReference(doc, "CONSOLE"));
//					root.appendChild(CommonUtil.getAppenderReference(doc, "HTTP_ROLLER"));
//					mainRootElement.appendChild(root);
				try {
					Transformer transformer = TransformerFactory.newInstance().newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(doc);
					File filePath = new File(localFilePath + "/logback-" + env + ".xml");

					File[] logbackFileList = folder.listFiles();
					if (logbackFileList.length == 0) {
						filePath.createNewFile();
						System.out.println("New File creation");
						StreamResult console = new StreamResult(filePath);
						transformer.transform(source, console);
						System.out.println(env + "\nXML DOM Created Successfully..");

					} else {
						for (File item : logbackFileList) {
							if (item.isFile()) {
								filePath.createNewFile();
								System.out.println("New File creation");
								StreamResult console = new StreamResult(filePath);
								transformer.transform(source, console);
								System.out.println(env + "\nXML DOM Created Successfully..");

							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				ApplicationYamlVO applicationYamlVO = new ApplicationYamlVO(
						new LoggingVO().setConfig("classpath:logging/logback-" + env + ".xml"), new ServerConfigVO()
								.setPort("8080").setServlet(new ServletConfigVo().setContextPath("/" + contextPath)));
				File yamlFilePath = new File(yamlFolderPath + "/application-" + env + ".yaml");
				ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
				try {
					File[] yamlFilesList = yamlFolder.listFiles();
					if (yamlFilesList.length == 0) {
						yamlFilePath.createNewFile();
						mapper.writeValue(yamlFilePath, applicationYamlVO);
						System.out.println(env + "yaml file Created Successfully..");
					} else {
						for (File item : yamlFilesList) {
							yamlFilePath.createNewFile();
							mapper.writeValue(yamlFilePath, applicationYamlVO);
							System.out.println(env + "yaml file Created Successfully..");
						}
					}
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// }

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}