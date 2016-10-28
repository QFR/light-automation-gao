package up.light.utils;

import up.light.exceptions.LightReflectionException;

/**
 * @version 1.0
 */
public class ClassUtil {

	public static Class<?> getClass(String name) throws LightReflectionException {
		ArgumentUtil.notNull(name, "Class name must not be null");

		Class<?> c = null;

		try {
			c = Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new LightReflectionException(e);
		}

		return c;
	}

	public static <T> T getInstance(Class<T> clazz) throws LightReflectionException {
		ArgumentUtil.notNull(clazz, "Class must not be null");

		T o = null;

		try {
			o = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new LightReflectionException(e);
		}

		return o;
	}

}
