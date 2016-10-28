package up.light.wait;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import up.light.pagefactory.TestElement;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
public abstract class WaitUtil {
	public static final int WAIT_SHORT = 1;
	public static final int WAIT_MEDIUM = 5;
	public static final int WAIT_LONG = 10;

	public static boolean exists(final WebDriver driver, final TestElement e, int seconds) {
		LogUtil.log.debug("[WaitUtil exists] begin - " + e.getBy() + ", time: " + seconds);

		boolean result = true;
		try {
			new WebDriverWait(driver, seconds).until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver input) {
					return e.e();
				}
			});
		} catch (TimeoutException e1) {
			result = false;
		}

		LogUtil.log.debug("[WaitUtil exists] end - result: " + result);

		return result;
	}

	public static WebElement waitFor(final WebDriver driver, final TestElement e, int seconds) {
		LogUtil.log.debug("[WaitUtil waitFor] begin - " + e.getBy() + ", time: " + seconds);

		WebElement element = new WebDriverWait(driver, seconds).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver input) {
				return e.e();
			}

			@Override
			public String toString() {
				return "visibilty of element locatoed by " + e.getBy();
			}
		});

		LogUtil.log.debug("[WaitUtil waitFor] end");

		return element;
	}

	public static void untilGone(final WebDriver driver, final TestElement e, int seconds) {
		LogUtil.log.debug("[WaitUtil untilGone] begin - " + e.getBy() + ", time: " + seconds);

		new WebDriverWait(driver, seconds, seconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				Boolean ret = Boolean.FALSE;
				try {
					WebElement real = e.e();
					if (!real.isDisplayed()) {
						ret = Boolean.TRUE;
					}
				} catch (NoSuchElementException | StaleElementReferenceException e) {
					ret = Boolean.TRUE;
				}
				return ret;
			}

			@Override
			public String toString() {
				return "invisibility of element located by " + e.getBy();
			}
		});

		LogUtil.log.debug("[WaitUtil untilGone] end");
	}

	public static void waitForAttribute(final WebDriver driver, final WebElement e, int seconds, String attributeName,
			final String expectValue, ICondition condition) {
		new WebDriverWait(driver, seconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				String actual = e.getAttribute(attributeName);
				return condition.isTrue(expectValue, actual);
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				sb.append("attribute[").append(attributeName).append("] ").append(condition);
				if (expectValue != null) {
					sb.append(" ").append(expectValue);
				}

				return sb.toString();
			}
		});
	}

	public static String waitForText(final WebDriver driver, final WebElement e, int seconds, final String expectValue,
			ICondition condition) {
		return new WebDriverWait(driver, seconds).until(new ExpectedCondition<String>() {
			@Override
			public String apply(WebDriver input) {
				String actual = e.getText();
				if(condition.isTrue(expectValue, actual))
					return actual;
				return null;
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				sb.append("text ").append(condition);
				if (expectValue != null) {
					sb.append(" ").append(expectValue);
				}

				return sb.toString();
			}
		});
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
