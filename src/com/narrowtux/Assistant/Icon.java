package com.narrowtux.Assistant;

import org.bukkit.ChatColor;

public enum Icon {
	INFORMATION("http://tetragaming.com/narrowtux/pluginres/narrowtuxlib/dialog-information.png", ChatColor.BLUE+"[INFO]"),
	ERROR("http://tetragaming.com/narrowtux/pluginres/narrowtuxlib/dialog-error.png", ChatColor.RED+"[ERROR]"),
	WARNING("http://tetragaming.com/narrowtux/pluginres/narrowtuxlib/dialog-warning.png", ChatColor.YELLOW+"[WARNING]");
	
	private final String url;
	private final String message;
	
	private Icon(String url, String message){
		this.url = url;
		this.message = message;
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getMessage(){
		return message;
	}
}
