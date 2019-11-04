package ds.program.fvhr.services.validator;

public class AttException extends Exception {
	private static final long serialVersionUID = 1L;

	public AttException() {
		super();
	}

	public AttException(String message) {
		super(message);
	}

	public AttException(String message, Throwable cause) {
		super(message, cause);
	}

	public AttException(Throwable cause) {
		super(cause);
	}
}
