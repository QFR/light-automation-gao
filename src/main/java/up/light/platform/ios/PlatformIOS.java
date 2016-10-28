package up.light.platform.ios;

import up.light.platform.IContextHandler;
import up.light.platform.IDriverGenerator;
import up.light.platform.IPlatform;

public class PlatformIOS implements IPlatform {

	@Override
	public String getName() {
		return "ios";
	}

	@Override
	public IDriverGenerator getGenerator() {
		return new GenForIOS();
	}

	@Override
	public IContextHandler getContextHandler() {
		return new CHForIOS();
	}

}
