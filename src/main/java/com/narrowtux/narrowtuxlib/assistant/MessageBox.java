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

package com.narrowtux.narrowtuxlib.assistant;

import org.bukkit.ChatColor;

import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MessageBox extends GenericWindow {
	private String text = "";
	private Icon icon = Icon.INFORMATION;
	private Label  textLabel;
	private Texture iconTexture;
	private Button okButton;

	public MessageBox(String title, String text, Icon icon, SpoutPlayer player){
		super(title, player);
		String changedText = "";
		for(String line:text.split("\n")){
			changedText+=ChatColor.WHITE+line+"\n";
		}
		text = changedText;

		//Button
		okButton = new GenericButton("OK");
		okButton.setWidth(100).setHeight(20).setX(getMarginLeft()+(getWidth()-100)/2).setY(getMarginBottom()-10);
		attachWidget(okButton);

		//Icon
		iconTexture = new GenericTexture(icon.getUrl());
		iconTexture.setWidth(32).setHeight(32).setX(getMarginLeft()).setY(getMarginTop()+10+30-16);
		attachWidget(iconTexture);

		//Text
		textLabel = new GenericLabel(text);
		textLabel.setWidth(380-42).setHeight(20).setX(getMarginLeft()+42).setY(iconTexture.getY()+16);
		attachWidget(textLabel);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public Label getTextLabel() {
		return textLabel;
	}
	public Texture getIconTexture() {
		return iconTexture;
	}
	public Button getOkButton() {
		return okButton;
	}
}
