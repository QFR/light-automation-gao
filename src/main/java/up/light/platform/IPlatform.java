package up.light.platform;

public interface IPlatform {

	String getName();

	IDriverGenerator getGenerator();

	IContextHandler getContextHandler();
}
