package fv.util;

import java.beans.PropertyEditorSupport;

/**
 * VNI text editor. Oracle 8 only.
 * @author Hieu
 *
 */

public class VniEditor extends PropertyEditorSupport{
	
	public VniEditor(){
		super();
	}
	
	@Override
	public String getAsText() {
		if (super.getValue()==null) return "";
		return Vni2Uni.convertToUnicode(super.getAsText());
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text==null) setValue(null);
		else {
			setValue(Vni2Uni.convertToVNI(text));
		}
	}
}
