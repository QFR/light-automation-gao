package up.light.parser;

import java.util.Map;

import up.light.repository.LocatorBean;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
public class RepoParserFactory {

	public static IParser<Map<String, LocatorBean>> getParser(String type) {
		if("json".equals(type)) {
			return new RepoJsonParser();
		}
		
		String msg = "unsupport type: " + type;
		LogUtil.log.error(msg);
		throw new RuntimeException(msg);
	}
}
