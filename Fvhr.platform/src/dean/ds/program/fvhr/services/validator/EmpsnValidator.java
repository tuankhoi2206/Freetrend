package ds.program.fvhr.services.validator;

import java.util.ArrayList;
import java.util.List;

public class EmpsnValidator implements Validator<String> {

	private static EmpsnValidator INSTANSE;

	private List<EmpsnValidateCondition> listConditions;

	private EmpsnValidator() {
	}

	public static synchronized EmpsnValidator createValidator() {
		if (INSTANSE == null) {
			INSTANSE = new EmpsnValidator();
		}
		return INSTANSE;
	}

	public void addValidateCondition(EmpsnValidateCondition c) {
		if (listConditions == null)
			listConditions = new ArrayList<EmpsnValidateCondition>();
		listConditions.add(c);
	}

	public void setValidateCondition(EmpsnValidateCondition c) {
		listConditions = new ArrayList<EmpsnValidateCondition>();
		listConditions.add(c);
	}

	public synchronized String validate(String empsn) {
		if (!empsn.matches("[0-9]{8}"))
			return INVALID;
		if (listConditions != null) {
			StringBuffer sb = new StringBuffer();
			for (EmpsnValidateCondition c : listConditions) {
				if (!c.validate(empsn))
					sb.append(c.getMessage()).append("&");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
				return sb.toString();
			}
		}
		return VALID;
	}

	public void clear() {
		listConditions = new ArrayList<EmpsnValidateCondition>();
	}

	public synchronized List<ValidatorMessage> batchValidate(
			List<String> listEmpsn) {
		List<ValidatorMessage> list = new ArrayList<ValidatorMessage>();
		for (String str : listEmpsn) {
			String validate = validate(str);
			if (!validate.equals(VALID)) {
				ValidatorMessage msg = new ValidatorMessage();
				msg.setObj(str);
				msg.setMessage(validate);
				list.add(msg);
			}
		}
		return list;
	}

}
