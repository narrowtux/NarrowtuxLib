package com.narrowtux.gui;

import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.PopupScreen;

public interface Clickable extends PopupScreen {
	/**
	 * This method will be called whenever a button on this screen has been clicked.
	 * @param button that was clicked
	 */
	public void onClick(Button button);
}
