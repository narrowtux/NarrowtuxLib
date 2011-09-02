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

package com.narrowtux.tuxlib.assistant;

import org.bukkit.ChatColor;

import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GenericWindow extends GenericPopup {
	private Texture background;
	private Label titleLabel;
	private String title = "";
	private int marginLeft, marginTop, marginBottom, marginRight;

	public GenericWindow(String title, SpoutPlayer player){
		this.title = title;
		background = new GenericTexture("http://tetragaming.com/narrowtux/pluginres/narrowtuxlib/messageBoxBG.png");
		int width = player.getMainScreen().getWidth();
		int height = player.getMainScreen().getHeight();
		background.setHeight(200).setWidth(400).setX((width-400)/2).setY((height-200)/2);
		attachWidget(background);

		marginLeft = (width-380)/2;
		marginTop = (background.getY())+10;
		marginBottom = background.getHeight()+background.getX()-10;
		marginRight = background.getX()+background.getWidth();

		//Title
		titleLabel = new GenericLabel(ChatColor.WHITE+title);
		titleLabel.setWidth(380).setHeight(20).setX(marginLeft+4).setY(marginTop+4);
		attachWidget(titleLabel);
	}

	public void setTitle(String title) {
		this.title = title;
		titleLabel.setText(ChatColor.WHITE+title).setDirty(true);
	}

	public int getMarginLeft() {
		return marginLeft;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public int getMarginBottom() {
		return marginBottom;
	}

	public int getMarginRight() {
		return marginRight;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int getWidth(){
		return 400;
	}

	@Override
	public int getHeight(){
		return 200;
	}
}
