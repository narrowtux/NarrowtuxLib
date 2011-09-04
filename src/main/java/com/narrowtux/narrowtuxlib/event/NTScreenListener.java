/*
 * Copyright (C) 2011 Moritz Schmale <narrow.m@gmail.com>
 *
 * NarrowtuxLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

package com.narrowtux.narrowtuxlib.event;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.event.screen.SliderDragEvent;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.Screen;

import com.narrowtux.narrowtuxlib.gui.Clickable;
import com.narrowtux.narrowtuxlib.gui.GenericCheckBox;
import com.narrowtux.narrowtuxlib.assistant.AssistantScreen;
import com.narrowtux.narrowtuxlib.assistant.MessageBox;

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
}
