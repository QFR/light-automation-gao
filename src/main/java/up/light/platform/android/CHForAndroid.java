package up.light.platform.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import up.light.DriverFactory;
import up.light.platform.IContextHandler;
import up.light.utils.LogUtil;

class CHForAndroid implements IContextHandler {
	private static final String KEY_RZRQ = "RZRQ";
	private static final String KEY_PTJY = "PTJY";

	private Map<String, String> winmap = new HashMap<>();
	private String webviewName;
	private boolean isInWebview;
	private String currentWindow;

	public CHForAndroid() {
		winmap.put(KEY_PTJY, null);
		winmap.put(KEY_RZRQ, null);
	}

	@Override
	public void handleContext(String context) {
		LogUtil.log.debug("[Switch context] begin: " + context + ", isInWebview: " + isInWebview);

		if ("".equals(context)) {
			return;
		}

		@SuppressWarnings("unchecked")
		AndroidDriver<AndroidElement> driver = (AndroidDriver<AndroidElement>) DriverFactory.getDriver();

		if (context.startsWith("WEBVIEW")) {
			// WEBVIEW_PTJY, WEBVIEW_RZRQ, WEBVIEW_LAST
			if (!isInWebview) {
				switchToWebview(driver);
				isInWebview = true;
			}
			switchToWindow(driver, context.substring(context.indexOf("_") + 1));
		} else {
			// NATIVE_APP
			if (isInWebview) {
				driver.context(context);
				isInWebview = false;
				// clean current window
				currentWindow = null;
				LogUtil.log.debug("[Switch context] swithed to: " + context);
			}
		}

		LogUtil.log.debug("[Switch context] end");
	}

	private void switchToWebview(AndroidDriver<AndroidElement> driver) {
		if (webviewName == null) {
			for (String c : driver.getContextHandles()) {
				if (c.startsWith("WEBVIEW")) {
					webviewName = c;
					break;
				}
			}
		}

		driver.context(webviewName);

		LogUtil.log.debug("[Switch webview] switched to: " + webviewName);
	}

	private void switchToWindow(AndroidDriver<AndroidElement> driver, String win) {
		LogUtil.log.debug("[Switch window] begin: " + win + ", current window: " + currentWindow);

		String to = null;

		if (winmap.containsKey(win)) {
			// PTJY, RZRQ
			to = winmap.get(win);

			if (to == null) {
				to = cacheWindow(driver, win);
			}
		} else {
			// LAST
			to = getLastWindow(driver);
		}

		if (!to.equals(currentWindow)) {
			driver.switchTo().window(to);
			currentWindow = to;

			LogUtil.log.debug("[Switch window] switched to: " + to);
			LogUtil.log.debug("[Switch window] url: " + driver.getCurrentUrl());
		}

		LogUtil.log.debug("[Switch window] end");
	}

	private String cacheWindow(AndroidDriver<AndroidElement> driver, String win) {
		String url = null;
		int i = 0;

		for (String w : driver.getWindowHandles()) {
			driver.switchTo().window(w);
			url = driver.getCurrentUrl();

			if (url.indexOf("ptjy_header") >= 0) {
				winmap.put(KEY_PTJY, w);
				++i;
			} else if (url.indexOf("rzrq_header") >= 0) {
				winmap.put(KEY_RZRQ, w);
				++i;
			}

			if (i == 2) {
				break;
			}
		}

		LogUtil.log.debug("[Cached window] " + winmap + ", i: " + i);
		return winmap.get(win);
	}

	private String getLastWindow(AndroidDriver<AndroidElement> driver) {
		ArrayList<String> arr = new ArrayList<>();
		arr.addAll(driver.getWindowHandles());
		return arr.get(arr.size() - 1);
	}
}
