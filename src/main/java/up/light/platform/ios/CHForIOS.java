package up.light.platform.ios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import up.light.DriverFactory;
import up.light.platform.IContextHandler;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
class CHForIOS implements IContextHandler {
	private String currentContext;
	private String namePTJY;

	@Override
	public void handleContext(String context) {
		LogUtil.log.debug("[Switch context] begin: " + context + ", current context: " + currentContext);

		if ("".equals(context)) {
			return;
		}

		@SuppressWarnings("unchecked")
		IOSDriver<IOSElement> driver = (IOSDriver<IOSElement>) DriverFactory.getDriver();
		String real = null;

		if ("WEBVIEW_LAST".equals(context)) {
			// WEBVIEW_LAST
			real = getLastWebviewName(driver);
		} else if ("WEBVIEW_PTJY".equals(context)) {
			// WEBVIEW_PTJY
			real = getPTJYName(driver);
		} else {
			// NATIVE_APP
			real = context;
		}

		if (real.equals(currentContext)) {
			return;
		}

		driver.context(real);
		currentContext = real;

		LogUtil.log.debug("[Switch context] switched to: " + real);
	}

	private String getLastWebviewName(IOSDriver<IOSElement> driver) {
		ArrayList<String> arr = new ArrayList<>();
		arr.addAll(driver.getContextHandles());
		return arr.get(arr.size() - 1);
	}

	private String getPTJYName(IOSDriver<IOSElement> driver) {
		if (namePTJY == null) {
			List<String> views = new ArrayList<>();
			views.addAll(driver.getContextHandles());
			views.remove("NATIVE_APP");
			Collections.sort(views, new Comparator<String>() {
				// 从大到小
				@Override
				public int compare(String o1, String o2) {
					Integer i1 = null, i2 = null;
					try {
						i1 = Integer.valueOf(o1.substring(8));
						i2 = Integer.valueOf(o2.substring(8));
					} catch (NumberFormatException e) {
					}

					if (i1 != null && i2 != null) {
						return i2 - i1;
					}

					return o2.compareTo(o1);
				}
			});

			for (String v : views) {
				driver.context(v);
				currentContext = v;

				if (driver.getCurrentUrl().indexOf("ptjy_header") != -1) {
					namePTJY = v;
					break;
				}
			}
		}

		return namePTJY;
	}
}
