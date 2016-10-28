package up.light;

import org.openqa.selenium.WebDriver;

import up.light.platform.IContextHandler;
import up.light.platform.IPlatform;
import up.light.platform.PlatformManager;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
public abstract class DriverFactory {
	private static WebDriver driver;
	private static IPlatform platform;

	public static WebDriver getDriver() {
		if (driver == null) {
			driver = platform.getGenerator().generate();
		}

		return driver;
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			LogUtil.log.debug("driver quit");
		}
	}

	public static void resetDriver() {
		driver = platform.getGenerator().generate();
	}

	public static IContextHandler getContextHandler() {
		return platform.getContextHandler();
	}

	static void initialize() {
		platform = PlatformManager.getPlatform(Setting.getPlatform());
	}
}
