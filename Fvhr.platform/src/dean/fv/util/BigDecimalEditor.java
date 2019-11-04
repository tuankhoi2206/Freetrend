package fv.util;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalEditor extends PropertyEditorSupport{
	private boolean zeroWhenNull=false;
	private String pattern;
	
	public BigDecimalEditor(boolean zeroWhenNull, String pattern){
		this.zeroWhenNull=zeroWhenNull;
		this.pattern=pattern;
	}
	
	@Override
	public String getAsText() {
		if (getValue()==null){
			if (zeroWhenNull) return "0";
			else return "";
		}else{
			BigDecimal val = (BigDecimal) getValue();
			DecimalFormat format = new DecimalFormat();
			format.applyPattern(pattern);
			return format.format(val.doubleValue());
		}
	}
		
}
