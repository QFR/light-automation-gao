package up.light.platform.android;

import up.light.platform.IContextHandler;
import up.light.platform.IDriverGenerator;
import up.light.platform.IPlatform;

public class PlatformAndroid implements IPlatform {

	@Override
	public String getName() {
		return "android";
	}

	@Override
	public IDriverGenerator getGenerator() {
		return new GenForAndroid();
	}

	@Override
	public IContextHandler getContextHandler() {
		return new CHForAndroid();
	}

}
