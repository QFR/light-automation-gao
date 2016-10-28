package up.light.pagefactory;

import java.lang.reflect.Field;

import up.light.repository.LocatorBean;
import up.light.repository.Repository;
import up.light.utils.LogUtil;

/**
 * @version 1.0
 */
public class PageBase {

	public PageBase() {
		Class<?> clazz = this.getClass();
		String className = clazz.getSimpleName();

		LogUtil.log.debug("[Initialize class] begin: " + className);
		
		for (Field f : clazz.getDeclaredFields()) {
			if (TestElement.class.isAssignableFrom(f.getType())) {
				String fieldName = f.getName();
				LocatorBean bean = Repository.getLocatorBean(className, fieldName);
				
				if(bean == null) {
					String msg = "can't get LocatorBean: " + fieldName;
					LogUtil.log.error(msg);
					throw new RuntimeException(msg);
				}
				
				// TODO multiple by
				TestElement value = new TestElement(bean.getBys().get(0), bean.getIn());
				f.setAccessible(true);
				
				try {
					f.set(this, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				
				LogUtil.log.debug(fieldName + ": " + value);
			}
		}
		
		LogUtil.log.debug("[Initialize class] end: " + className);
	}
}
