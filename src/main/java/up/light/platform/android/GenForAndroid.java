package up.light.platform.android;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import up.light.FolderType;
import up.light.Setting;
import up.light.platform.IDriverGenerator;
import up.light.utils.DateUtil;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
public class GenForAndroid implements IDriverGenerator {

	@Override
	public WebDriver generate() {
		AndroidDriver<AndroidElement> driver = null;
		String nodeJS = Setting.getProperty("nodejs");
		String appiumJS = Setting.getProperty("appium");

		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.usingDriverExecutable(new File(nodeJS));
		builder.withAppiumJS(new File(appiumJS));
		builder.withLogFile(new File(
				Setting.getPath(FolderType.LOG) + DateUtil.getDateString("'appium.'yyyy.MM.dd-HH.mm.ss'.txt'")));
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "warn:debug");
		AppiumDriverLocalService service = builder.build();
		driver = new AndroidDriver<>(service, getDc());

		LogUtil.log.info("[Driver] generate driver " + driver);

		return driver;
	}

	private DesiredCapabilities getDc() {
		DesiredCapabilities dc = new DesiredCapabilities();
		String key, value;

		for (Map.Entry<String, String> e : Setting.getProperties()) {
			key = e.getKey();
			if (key.startsWith("dc.")) {
				value = e.getValue();
				dc.setCapability(key.substring(3), value);
			}
		}

		return dc;
	}
}
