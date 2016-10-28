package up.light.repository;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import up.light.FolderType;
import up.light.Setting;
import up.light.io.FileSystemResource;
import up.light.io.IResource;
import up.light.parser.IParser;
import up.light.parser.RepoParserFactory;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
public class Repository {
	private static Map<String, SoftReference<Map<String, LocatorBean>>> mRepo = new HashMap<>();
	private static IParser<Map<String, LocatorBean>> mParser;
	
	public static LocatorBean getLocatorBean(String className, String fieldName) {
		SoftReference<Map<String, LocatorBean>> ref = mRepo.get(className);
		
		if(ref == null) {
			// has not parsed
			LogUtil.log.debug("repository has not been parsed - " + className);
			parse(className);
			ref = mRepo.get(className);
		}
		
		Map<String, LocatorBean> m = ref.get();
		
		if(m == null) {
			// cleaned by gc
			LogUtil.log.debug("repository was cleaned, parse it again");
			parse(className);
			ref = mRepo.get(className);
			m = ref.get();
		}
		
		return m.get(fieldName);
	}
	
	private static void parse(String className) {
		String type = "json";
		
		if(mParser == null) {
			mParser = RepoParserFactory.getParser(type);
		}
		
		String file = className + "." + type;
		IResource res = new FileSystemResource(Setting.getPath(FolderType.REPOSITORY) + file);
		SoftReference<Map<String, LocatorBean>> ref = new SoftReference<>(mParser.parse(res));
		mRepo.put(className, ref);
	}
}
