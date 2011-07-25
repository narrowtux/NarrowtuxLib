package com.narrowtux.Assistant;

public class NumberPage extends AssistantPage {

	private double min = Double.MIN_VALUE, max = Double.MAX_VALUE;
	private Accuracy accuracy;
	private Double value = null;
	
	public NumberPage(Assistant assistant, Accuracy accuracy) {
		super(assistant);
		this.accuracy = accuracy;
	}
	
	public void setRange(double min, double max){
		setMinimum(min);
		setMaximum(max);
	}
	
	public void setMinimum(double min){
		this.min = min;
	}
	
	public void setMaximum(double max){
		this.max = max;
	}

	@Override
	public AssistantAction onPageInput(String text) {
		double val = 0;
		try{
			val = Double.valueOf(text);
		} catch(Exception e){
			sendMessage("Please enter a valid number");
			return AssistantAction.SILENT_REPEAT;
		}
		if(min<=val&&val<=max){
			value = val;
			return AssistantAction.CONTINUE;
		} else {
			sendMessage("Number out of range!");
			return AssistantAction.SILENT_REPEAT;
		}
	}
	
	public double getValue(){
		return value;
	}
	
	public boolean hasValue(){
		return value!=null;
	}
}
