package com.narrowtux.event;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.Screen;

import com.narrowtux.Assistant.AssistantScreen;
import com.narrowtux.Assistant.MessageBox;

public class NTScreenListener extends ScreenListener {

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		Screen screen = event.getScreen();
		if(screen instanceof AssistantScreen){
			AssistantScreen ascreen = (AssistantScreen)screen;
			ascreen.handleClick(event.getButton());
		}
		if(screen instanceof MessageBox){
			MessageBox msg = (MessageBox)screen;
			if(msg.getOkButton().equals(event.getButton())){
				msg.close();
			}
		}
	}

	@Override
	public void onScreenClose(ScreenCloseEvent event) {
		Screen screen = event.getScreen();
		if(screen instanceof AssistantScreen){
			AssistantScreen ascreen = (AssistantScreen)screen;
			if(ascreen.getAssistant().isActive()){
				ascreen.getAssistant().cancel();
			}
		}
	}

	@Override
	public void onTextFieldChange(TextFieldChangeEvent event) {
	}

}
