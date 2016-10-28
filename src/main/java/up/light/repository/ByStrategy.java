package up.light.repository;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
public class ByStrategy {

	public static By getBy(String name, String value) {
		Strategy s = Strategy.fromString(name);
		return s.getBy(value);
	}

}

enum Strategy {
	ID {
		@Override
		public By getBy(String value) {
			return By.id(value);
		}
	},
	LINKTEXT {
		@Override
		public By getBy(String value) {
			return By.linkText(value);
		}
	},
	PARTIALLINKTEXT {
		@Override
		public By getBy(String value) {
			return By.partialLinkText(value);
		}
	},
	NAME {
		@Override
		public By getBy(String value) {
			return By.name(value);
		}
	},
	TAGNAME {
		@Override
		public By getBy(String value) {
			return By.tagName(value);
		}
	},
	XPATH {
		@Override
		public By getBy(String value) {
			return By.xpath(value);
		}
	},
	CLASSNAME {
		@Override
		public By getBy(String value) {
			return By.className(value);
		}
	},
	CSSSELECTOR {
		@Override
		public By getBy(String value) {
			return By.cssSelector(value);
		}
	},
	ACCESSIBILITYID{
		@Override
		public By getBy(String value) {
			return MobileBy.AccessibilityId(value);
		}
	},
	UIAUTOMATOR {
		@Override
		public By getBy(String value) {
			return MobileBy.AndroidUIAutomator(value);
		}
	},
	UIAUTOMATION{
		@Override
		public By getBy(String value) {
			return MobileBy.IosUIAutomation(value);
		}
	};

	public abstract By getBy(String value);

	public static Strategy fromString(String name) {
		Strategy s = null;

		try {
			s = Strategy.valueOf(name.toUpperCase());
		} catch (Exception e) {
			String msg = "unknown strategy: " + name;
			LogUtil.log.error(msg, e);
			throw new RuntimeException(msg, e);
		}

		return s;
	}
}
