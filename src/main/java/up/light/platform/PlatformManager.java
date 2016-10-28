package up.light.platform;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import up.light.Setting;
import up.light.platform.android.PlatformAndroid;
import up.light.platform.ios.PlatformIOS;
import up.light.utils.ArgumentUtil;

public abstract class PlatformManager {
	private static final Map<String, IPlatform> platforms = new HashMap<>();

	public static boolean isSupported(String name) {
		return platforms.containsKey(name);
	}

	public static Set<String> getSupportedPlatforms() {
		return platforms.keySet();
	}

	public static IPlatform getPlatform(String name) {
		return platforms.get(name);
	}

	public static void registePlatform(IPlatform platofrm) {
		ArgumentUtil.notNull(platofrm, "platofrm must not be null");
		platforms.put(platofrm.getName(), platofrm);
	}

	public static void initialize() {
		addJarToClassPath();
		addPlatformFromJar();
		registePlatform(new PlatformAndroid());
		registePlatform(new PlatformIOS());
	}

	private static void addJarToClassPath() {
		String path = System.getProperty("user.dir") + Setting.SEPARATOR + "platforms" + Setting.SEPARATOR;
		File folder = new File(path);
		List<URL> urls = new ArrayList<>();

		if (folder.exists()) {
			for (File f : folder.listFiles()) {
				if (f.getName().endsWith(".jar")) {
					try {
						urls.add(f.toURI().toURL());
					} catch (MalformedURLException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Method add;
		try {
			add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		add.setAccessible(true);

		for (URL u : urls) {
			try {
				add.invoke(loader, u);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static void addPlatformFromJar() {
		ServiceLoader<IPlatform> sl = ServiceLoader.load(IPlatform.class);

		for (IPlatform p : sl) {
			platforms.put(p.getName(), p);
		}
	}
}
