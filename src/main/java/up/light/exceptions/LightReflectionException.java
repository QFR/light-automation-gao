package up.light.exceptions;

public class LightReflectionException extends ReflectiveOperationException {
	private static final long serialVersionUID = -1597306199084280690L;

	public LightReflectionException() {
		super();
	}

	public LightReflectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public LightReflectionException(String message) {
		super(message);
	}

	public LightReflectionException(Throwable cause) {
		super(cause);
	}

}
