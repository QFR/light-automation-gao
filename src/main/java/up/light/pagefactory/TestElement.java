package up.light.pagefactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import up.light.DriverFactory;
import up.light.platform.IContextHandler;
import up.light.utils.LogUtil;

public class TestElement {
	private static IContextHandler handler;
	private By by;
	private String in;

	public static void initialize(IContextHandler handler) {
		TestElement.handler = handler;
	}

	public TestElement(By by, String in) {
		this.by = by;
		this.in = in;
	}

	public By getBy() {
		return by;
	}

	public <T extends WebElement> T e() {
		handler.handleContext(in);
		T real = DriverFactory.getDriver().findElement(by);

		LogUtil.log.debug("[Find element] id: " + getId(real) + " ,by: " + by);

		return real;
	}

	public <T extends WebElement> List<T> es() {
		handler.handleContext(in);
		List<T> ls = DriverFactory.getDriver().findElements(by);

		LogUtil.log.debug("[Find elements] total: " + ls.size() + ", by: " + by);

		return ls;
	}

	@Override
	public String toString() {
		return "TestElement [by=" + by + ", in=" + in + "]";
	}

	private String getId(WebElement e) {
		if (e instanceof RemoteWebElement) {
			RemoteWebElement re = (RemoteWebElement) e;
			return re.getId();
		}

		return "--";
	}
}
