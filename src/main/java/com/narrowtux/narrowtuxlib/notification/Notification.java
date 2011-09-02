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

package com.narrowtux.tuxlib.notification;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.narrowtux.tuxlib.NarrowtuxLib;

public class Notification {
	private String receiver;
	private String title;
	private String text;
	private String commandAction;
	private int id;

	/**
	 * Constructs a custom Notification
	 * @param receiver the name of the player that the notification will be sent to.
	 * @param title the title of the notification. Will also be shown in the unread notifications list.
	 * @param text the text of the notification.
	 */
	public Notification(String receiver, String title, String text){
		this.receiver = receiver;
		this.title = title;
		this.text = text;
		this.commandAction = "";
	}
	/**
	 *
	 * @return the name of the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * sets the receiver of the notification
	 * @param receiver the name of the receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 *
	 * @return the title of the notification
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * sets the title of the notification.
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 *
	 * @return the text of the notification
	 */
	public String getText() {
		return text;
	}

	/**
	 * sets the text of the notification
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 *
	 * @return the command action
	 * @see setCommandAction
	 */
	public String getCommandAction() {
		return commandAction;
	}
	/**
	 * Sets the command Action. It's a command that will be triggered for that player when the player actions it.
	 * Currently, actioning a notification is not implemented.
	 * Use a command like this: "/myplugin handle name".
	 * @param commandAction
	 */
	public void setCommandAction(String commandAction) {
		this.commandAction = commandAction;
	}

	/**
	 * Tries to print a notification.
	 */
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

	/**
	 *
	 * @return a player object of the receiver.
	 * @return null when the player is offline.
	 */
	public Player getPlayer(){
		try{
			return Bukkit.getServer().getPlayer(receiver);
		} catch(NullPointerException e){
			return null;
		}
	}

	/**
	 *
	 * @return true if the receiver is online.
	 */
	public boolean isPlayerOnline(){
		return getPlayer()!=null;
	}

	/**
	 * Sets the unique ID of the notification.
	 * The unique ID will be used in the unread notification list
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 *
	 * @return the unique ID of the notification
	 */
	public int getId() {
		return id;
	}

	/**
	 * Executes the commandAction when the player is online.
	 */
	public void runCommand(){
		if(isPlayerOnline() && !commandAction.equals("")){
			Bukkit.getServer().dispatchCommand(getPlayer(), commandAction);
		}
	}
}
