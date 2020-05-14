package io.spring.start.site.custom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.spring.start.site.custom.VO.DBTypeRequest;

public class CommonUtil {
	public static boolean isDBConfigurationExists(Path targetFilepath) throws FileNotFoundException, IOException {
		RandomAccessFile aFile = new RandomAccessFile(targetFilepath.toString(), "r");
		FileChannel inChannel = aFile.getChannel();
		MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
		buffer.load();
		String fileData = "";
		for (int i = 0; i < buffer.limit(); i++) {

			char c = (char) buffer.get();
			System.out.print(c);
			fileData = fileData + c;
		}
		if (fileData.contains("spring.datasource")) {
			buffer.clear();
			inChannel.close();
			aFile.close();
			return true;
		}
		buffer.clear(); // do something with the data clear/compact it.
		inChannel.close();
		aFile.close();
		return false;
	}

	public static boolean isDirectoryExists(String targetStr, Path projectRoot) {
		String[] data = targetStr.split("/");
		return Files.exists(projectRoot.resolve(targetStr.replaceAll("/" + data[data.length - 1], "")));
	}

	public static boolean isFileExists(String targetStr, Path projectRoot) {
		return Files.exists(projectRoot.resolve(targetStr));
	}

	public static void writeTargetFileFromSrc(Path projectRoot, Path targetFilepath, String src)
			throws FileNotFoundException, IOException {
		File file = new File(src);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				System.out.println("read " + readNum + " bytes,");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		byte[] bytes = bos.toByteArray();
		if (new File(targetFilepath.toString()).length() != 0)
			Files.write(targetFilepath, "\n\n".getBytes(), StandardOpenOption.APPEND);
		Files.write(targetFilepath, bytes, StandardOpenOption.APPEND);
	}

	public static void writeFileForDB(DBTypeRequest typeRequest) throws FileNotFoundException, IOException {
		File file = new File("src/main/resources/config/nexus.yml");
		FileWriter writer = new FileWriter(file);
		if (typeRequest.getDbType().equals("mysql")) {
			writer.write("spring.datasource.url: jdbc:mysql://${MYSQL_HOST:" + typeRequest.getHostName() + "}:3306/"
					+ typeRequest.getDbName() + "\r\n");
		} else if (typeRequest.getDbType().equals("mssql")) {
			writer.write("spring.datasource.url: jdbc:sqlserver://" + typeRequest.getHostName() + ";databaseName="
					+ typeRequest.getDbName() + "\r\n");
		} else if (typeRequest.getDbType().equals("oracle")) {
			writer.write("spring.datasource.url: jdbc:oracle:thin:@" + typeRequest.getHostName() + "\r\n");
		}
		String username = typeRequest.getUsername() != null ? typeRequest.getUsername() : "username";
		String password = typeRequest.getPassword() != null ? typeRequest.getPassword() : "password";
		writer.write("spring.datasource.username: " + username + "\r\n");
		writer.write("spring.datasource.password: " + password + "\r\n");

		if (typeRequest.getIsHibernate() != null && typeRequest.getIsHibernate().equals("yes")) {
			writer.write("spring.jpa.show-sql: " + typeRequest.getShowsql() + "\r\n");
			writer.write("spring.jpa.hibernate.ddl-auto: " + typeRequest.getDdlauto() + "\r\n");
			writer.write("spring.jpa.hibernate.dialect: org.hibernate.dialect." + typeRequest.getDialect() + "\r\n");
		}

		writer.close();

	}

	public static Path createFile(Path projectRoot, String targetStr) throws IOException {
		Path targetFilepath;
		if (!CommonUtil.isFileExists(targetStr, projectRoot)) {
			if (!CommonUtil.isDirectoryExists(targetStr, projectRoot)) {
				String[] data = targetStr.split("/");
				Files.createDirectories(projectRoot.resolve(targetStr.replaceAll("/" + data[data.length - 1], "")));
			}
			targetFilepath = projectRoot.resolve(targetStr);

			Files.createFile(targetFilepath);
		} else {
			targetFilepath = projectRoot.resolve(targetStr);
		}
		return targetFilepath;
	}

	public static Node getLogLevel(Document doc, String name, String value) {

		Element logger = doc.createElement("logger");
		Element level = doc.createElement("level");
		level.setAttribute("name", name);
		logger.setAttribute("value", value);
		logger.appendChild(level);
		return logger;
	}

	public static Node createElementWithAttr(Document doc, String eleName, String key, String value) {
		Element element = doc.createElement(eleName);
		element.setAttribute(key, value);

		return element;

	}

	public static Node getAppenderReference(Document doc, String value) {

		Element appender_ref = doc.createElement("appender-ref");
		appender_ref.setAttribute("ref", value);
		return appender_ref;

	}

	public static Node createElement(Document doc) {
		Element config = doc.createElement("contextListener");
		config.setAttribute("class", "ch.qos.logback.clasic.jul.LevelChangePropagator");
		Element resetJul = doc.createElement("resetJUL");
		resetJul.appendChild(doc.createTextNode("true"));
		config.appendChild(resetJul);
		return config;
	}

}
