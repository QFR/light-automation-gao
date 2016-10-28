package up.light.platform.ios;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import up.light.Setting;
import up.light.platform.IDriverGenerator;

/**
 * @version 1.0
 */
public class GenForIOS implements IDriverGenerator {

	@Override
	public WebDriver generate() {
		IOSDriver<IOSElement> driver = null;
		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, Setting.getProperty("dc.devices"));
		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, Setting.getProperty("dc.platform_version"));
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		dc.setCapability(MobileCapabilityType.APP, Setting.getProperty("dc.app"));
		dc.setCapability(MobileCapabilityType.NO_RESET, "true");
		dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");

		try {
			driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		return driver;
	}

}
