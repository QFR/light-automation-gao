package up.light;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import up.light.platform.PlatformManager;
import up.light.utils.PropertiesFile;

/**
 * Setting of the execution
 * 
 * @version 1.0
 */
public class Setting {
	public static final String SEPARATOR = File.separator;
	public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

	private static final Map<String, String> properties = new HashMap<>();
	private static final String CONFIG_FILE = "test.properties";
	private static final String LOG_TAG = "[Setting] ";
	private static final String KEY_PLATFORM = "run.platform";

	/**
	 * Get the running platform
	 * 
	 * @return name of platform
	 */
	public static String getPlatform() {
		return properties.get(KEY_PLATFORM);
	}

	/**
	 * Get property with specified name
	 * 
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		return properties.get(name);
	}

	/**
	 * Set property with specified name
	 * 
	 * @param name
	 * @param value
	 */
	public static void setProperty(String name, String value) {
		properties.put(name, value);
	}

	/**
	 * Get all properties
	 * 
	 * @return
	 */
	public static Set<Map.Entry<String, String>> getProperties() {
		return properties.entrySet();
	}

	/**
	 * Get the path of folder
	 * 
	 * @param folder
	 *            type of folder
	 * @return the path of folder
	 */
	public static String getPath(FolderType folder) {
		String value = properties.get(folder.getKeyName());

		if (value == null) {
			value = folder.defaultValue();
			properties.put(folder.getKeyName(), value);
		}

		return value;
	}

	static void initialize(String platform) {
		// avoid warning of common-logging
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		if (StringUtils.isBlank(platform)) {
			// command argument is null, read properties file
			String root = System.getProperty("user.dir");
			platform = readProperties(root + SEPARATOR + CONFIG_FILE).getProperty(KEY_PLATFORM);
		}

		// put platform into map if it is valid
		processPlatformStr(platform);

		// read configuration from config folder
		readFile();
	}

	private static void raiseError(String msg, Throwable t) {
		String m = LOG_TAG + msg;
		throw new RuntimeException(m, t);
	}

	private static void processPlatformStr(String platform) {
		if (!PlatformManager.isSupported(platform)) {
			raiseError("unsupport platform: " + platform, null);
		}

		properties.put(KEY_PLATFORM, platform);
	}

	private static void readFile() {
		String file = getPath(FolderType.CONFIGURATION) + "config.properties";
		File f = new File(file);

		if (!f.exists())
			return;

		Properties p = readProperties(file);
		String key = null, value = null;

		for (Map.Entry<Object, Object> e : p.entrySet()) {
			key = (String) e.getKey();
			value = handleVariables((String) e.getValue());
			properties.put(key, value);
		}
	}

	private static Properties readProperties(String file) {
		return new PropertiesFile(file).getProperties();
	}

	private static String handleVariables(String str) {
		int i;
		String result = str, name = null, separatorRegex = SEPARATOR;

		if ("\\".equals(separatorRegex)) {
			separatorRegex = "\\\\";
		}

		for (FolderType ft : FolderType.values()) {
			name = ft.name();
			i = str.indexOf(name);

			if (i != -1) {
				result = str.replace("${" + name + "}", getPath(ft));
				result = result.replaceAll("[\\\\/]{1,}", separatorRegex);
			}
		}

		return result;
	}
}
