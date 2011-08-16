package com.narrowtux.event;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.event.screen.SliderDragEvent;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.Screen;

import com.narrowtux.Assistant.AssistantScreen;
import com.narrowtux.Assistant.MessageBox;
import com.narrowtux.gui.Clickable;
import com.narrowtux.gui.GenericCheckBox;

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
		if(event.getButton() instanceof GenericCheckBox){
			GenericCheckBox cb = (GenericCheckBox)event.getButton();
			cb.onClick();
		}
		if(screen instanceof Clickable){
			Clickable cscreen = (Clickable)screen;
			cscreen.onClick(event.getButton());
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

	@Override
	public void onSliderDrag(SliderDragEvent event) {
	}

}
