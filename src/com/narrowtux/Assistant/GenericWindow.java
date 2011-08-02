package com.narrowtux.Assistant;

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
