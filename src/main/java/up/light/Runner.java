package up.light;

import java.util.ArrayList;

import org.testng.TestNG;

import up.light.pagefactory.TestElement;
import up.light.platform.PlatformManager;
import up.light.reader.impl.ExcelReader.ExcelReaderCloser;
import up.light.utils.LogUtil;
import up.light.writer.impl.ExcelWriter.ExcelWriterCloser;

public class Runner {

	public void run(String platform) {
		// initialize system
		initialize(platform);

		// run testng
		String pathConfig = Setting.getPath(FolderType.CONFIGURATION);
		TestNG testng = new TestNG(false);
		ArrayList<String> suites = new ArrayList<>();
		suites.add(pathConfig + "test.xml");
		testng.setTestSuites(suites);

		try {
			testng.run();
		} finally {
			new ExcelReaderCloser().doAfter();
			new ExcelWriterCloser().doAfter();
			DriverFactory.quitDriver();
		}
	}

	private static void initialize(String platform) {
		PlatformManager.initialize();
		Setting.initialize(platform);
		LogUtil.initialize();
		LogUtil.log.info("[Platform] supported platforms: " + PlatformManager.getSupportedPlatforms());

		DriverFactory.initialize();
		TestElement.initialize(DriverFactory.getContextHandler());
	}
}
