package up.light.platform.ios;

import java.util.ArrayList;

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

	@Override
	public void handleContext(String context) {
		LogUtil.log.debug("[Switch context] begin: " + context + ", current context: " + currentContext);
		
		if ("".equals(context) || context.equals(currentContext)) {
			return;
		}

		@SuppressWarnings("unchecked")
		IOSDriver<IOSElement> driver = (IOSDriver<IOSElement>) DriverFactory.getDriver();
		String real = null;

		if ("WEBVIEW_LAST".equals(context)) {
			// WEBVIEW_LAST
			ArrayList<String> arr = new ArrayList<>();
			arr.addAll(driver.getContextHandles());
			real = arr.get(arr.size() - 1);

			if (real.equals(currentContext)) {
				return;
			}
		} else {
			// NATIVE_APP
			real = context;
		}

		currentContext = real;
		driver.context(real);
		
		LogUtil.log.debug("[Switch context] switched to: " + real);
		
		if(!"NATIVE_APP".equals(real)) {
			LogUtil.log.debug("[Switch context] url: " + real);
		}
	}

}
