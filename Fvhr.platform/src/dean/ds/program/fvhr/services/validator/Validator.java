package ds.program.fvhr.services.validator;

import java.util.List;

public interface Validator<T> {
	public static final String VALID = "ok";

	public static final String INVALID = "invalid";

	public static final String NOTFOUND = "notfound";

	public static final String NOTWORK = "notwork";

	public static final String UNKNOWN = "unknown";
	
	public static final String QUIT = "quit";

	String validate(T object);

	List<ValidatorMessage> batchValidate(List<T> list);
}
