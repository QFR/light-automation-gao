package up.light.exceptions;

import java.io.IOException;

public class LightIOException extends IOException {
	private static final long serialVersionUID = 6877863692153283410L;

	public LightIOException() {
		super();
	}

	public LightIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public LightIOException(String message) {
		super(message);
	}

	public LightIOException(Throwable cause) {
		super(cause);
	}

}
