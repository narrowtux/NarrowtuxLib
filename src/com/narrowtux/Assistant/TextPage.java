package com.narrowtux.Assistant;

public class TextPage extends AssistantPage {
	public TextPage(Assistant assistant) {
		super(assistant);
	}

	private String input = "";
	
	@Override
	public AssistantAction onPageInput(String text){
		input=text;
		return AssistantAction.CONTINUE;
	}
	/**
	 * Get the input of the page after it has been processed.
	 * @return the text that the player typed in.
	 */
	public String getInput(){
		return input;
	}
}
