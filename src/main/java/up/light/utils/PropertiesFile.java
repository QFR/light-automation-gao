package up.light.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesFile {
	private Properties p;

	public PropertiesFile(String file) {
		this(file, true);
	}

	public PropertiesFile(String file, boolean throwException) {
		p = new Properties();
		FileInputStream fins = null;

		try {
			fins = new FileInputStream(file);
			p.load(fins);
		} catch (Exception e) {
			throw new RuntimeException("read file '" + file + "' failed", e);
		} finally {
			if (fins != null) {
				try {
					fins.close();
				} catch (Exception e) {
					throw new RuntimeException("close file '" + file + "' failed", e);
				}
			}
		}
	}

	public Properties getProperties() {
		return p;
	}
}
