package up.light.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import up.light.FolderType;
import up.light.Setting;

/**
 * <table>
 * <tr>
 * <td>DEFAULT LEVEL</td>
 * <td>INFO</td>
 * </tr>
 * <tr>
 * <td>DEFAULT PATTERN</td>
 * <td>[%d{yyyy-MM-dd HH:mm:ss,SSS}][%p] %C{1}:%L - %m%n</td>
 * </tr>
 * <tr>
 * <td>DEFAULT FILENAME PATTERN</td>
 * <td>'log.'yyyy.MM.dd-HH.mm.ss'.txt'</td>
 * </tr>
 * </table>
 * 
 * @version 1.0
 */
public class LogUtil {
	private static final String DEFAULT_LOG_PATTERN = "[%d{yyyy-MM-dd HH:mm:ss,SSS}][%p] %C{1}:%L - %m%n";
	public static Logger log;

	public static void initialize() {
		String configPath = Setting.getPath(FolderType.CONFIGURATION);
		String configFile = getConfigFile(configPath);

		if (configFile != null) {
			String config = configPath + configFile;

			if (configFile.endsWith(".xml")) {
				// log4j.xml
				DOMConfigurator.configure(config);
			} else {
				// log4j.properties
				PropertyConfigurator.configure(config);
			}
			// get logger
			log = Logger.getLogger(LogUtil.class);
		} else {
			// use default values
			setDefault();
		}
	}

	private static String getConfigFile(String folder) {
		File f = new File(folder);

		if (f.exists() && f.isDirectory()) {
			for (String s : f.list()) {
				if ("log4j.properties".equals(s) || "log4j.xml".equals(s)) {
					return s;
				}
			}
		}

		return null;
	}

	private static void setDefault() {
		log = Logger.getLogger(LogUtil.class);
		log.setLevel(Level.INFO);
		Layout layout = new PatternLayout(DEFAULT_LOG_PATTERN);

		try {
			log.addAppender(new LightFileAppender(layout, LightFileAppender.DEFAULT_FILENAME_PATTERN));
		} catch (IOException e) {
			throw new RuntimeException("[LogUtil] can't create log file", e);
		}
	}
}
