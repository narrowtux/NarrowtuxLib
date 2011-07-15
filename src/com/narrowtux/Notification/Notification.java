package com.narrowtux.Notification;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.narrowtux.Main.NarrowtuxLib;

public class Notification {
	private String receiver;
	private String title;
	private String text;
	private String commandAction;
	private int id;
	
	public Notification(String receiver, String title, String text){
		this.receiver = receiver;
		this.title = title;
		this.text = text;
		this.commandAction = "";
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCommandAction() {
		return commandAction;
	}
	public void setCommandAction(String commandAction) {
		this.commandAction = commandAction;
	}
	
	public void print(){
		if(isPlayerOnline()){
			Player p = getPlayer();
			p.sendMessage(title);
			p.sendMessage(text);
			NarrowtuxLib.getNotificationManager().remove(this);
		} else {
			NarrowtuxLib.getNotificationManager().addNotification(this);
		}
	}
	
	public Player getPlayer(){
		try{
			return Bukkit.getServer().getPlayer(receiver);
		} catch(NullPointerException e){
			return null;
		}
	}
	
	public boolean isPlayerOnline(){
		return getPlayer()!=null;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
