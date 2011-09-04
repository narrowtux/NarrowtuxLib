package com.narrowtux.narrowtuxlib.gui;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.Label;

public class GenericCheckBox extends GenericButton {
	private boolean checked = false;
	private String checkedText = "Yes";
	private String uncheckedText = "No";
	private String text;
	
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
		updateText();
	}
	
	public String getCheckedText() {
		return checkedText;
	}
	
	public void setCheckedText(String checkedText) {
		this.checkedText = checkedText;
		updateText();
	}
	
	public String getUncheckedText() {
		return uncheckedText;
	}
	
	public void setUncheckedText(String uncheckedText) {
		this.uncheckedText = uncheckedText;
		updateText();
	}
	
	@Override 
	public Label setText(String text){
		this.text = text;
		updateText();
		return this;
	}
	
	private void updateText() {
		super.setText(text + ": "+(isChecked()?checkedText:uncheckedText));
		this.setDirty(true);
	}
	
	public void onClick(){
		setChecked(!checked);
	}
}
