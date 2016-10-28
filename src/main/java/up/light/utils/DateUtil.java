package up.light.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 */
public class DateUtil {

	public static String getDateString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
}
