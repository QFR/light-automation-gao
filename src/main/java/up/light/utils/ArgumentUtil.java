package up.light.utils;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * @version 1.0
 */
public class ArgumentUtil {
	
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notBlank(String str, String message) {
		if (StringUtils.isBlank(str)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isInstance(Class<?> argClass, Class<?> superClass, String message) {
		if(!superClass.isAssignableFrom(argClass)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void notEmpty(Collection<?> c, String message) {
		if(c.size() == 0) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isEmpty(Collection<?> c, String message) {
		if(c.size() != 0) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isEqual(int i, int j, String message) {
		if(i != j) {
			throw new IllegalArgumentException(message);
		}
	}
}
