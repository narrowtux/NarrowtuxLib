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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Assistant {
	private List<AssistantPage> pages = new ArrayList<AssistantPage>();
	private String title = "Generic assistant";
	protected AssistantPage currentPage = null;
	private int currentPageIndex = 0;
	private Player player = null;
	private String heldBackChat = "";
	private Location assistantStartLocation = null;
	protected static Map<Player, Assistant> instances = new HashMap<Player, Assistant>();
	protected boolean active;

	/**
	 * Creates an assistant.
	 * @param p is the Player that summoned the assistant.
	 */
	public Assistant(Player p){
		setPlayer(p);
	}

	/**
	 * Called when players chat to forward the chat input to the current page.
	 * If the player has an assistant, the event is cancelled
	 * @param event
	 * @returns false when the assistant was cancelled
	 */
	public static boolean onPlayerChat(PlayerChatEvent event){
		//Dispatch the chat to the right assistant
		Assistant current = null;
		for(Player p:instances.keySet()){
			event.getRecipients().remove(p);
			if(!event.getPlayer().equals(p)){
				instances.get(p).heldBackChat+= "<"+event.getPlayer().getName()+"> "+event.getMessage()+"\n";
			}
		}
		if(instances.containsKey(event.getPlayer())){
			current = instances.get(event.getPlayer());
		} else {
			return false;
		}
		//Handle the input, send it to the right page
		String text = event.getMessage();
		if(current.getCurrentPage()!=null)
		{
			event.setCancelled(true);
			switch(current.getCurrentPage().onPageInput(text)){
			case CANCEL:
				current.cancel();
				return false;
			case FINISH:
				current.stop();
				return true;
			case CONTINUE:
				current.currentPageIndex++;
				if(current.pages.size()>current.currentPageIndex){
					current.currentPage = current.pages.get(current.currentPageIndex);
					current.currentPage.play();
				} else {
					current.stop();
				}
				return true;
			case REPEAT:
				current.repeatCurrentPage();
				current.currentPageIndex++;
				if(current.pages.size()>current.currentPageIndex){
					current.currentPage = current.pages.get(current.currentPageIndex);
					current.currentPage.play();
				} else {
					current.stop();
				}
				return true;
			case SILENT_REPEAT:
				return true;
			}
		}
		return false;
	}

	public static void onPlayerMove(PlayerMoveEvent event){
		Assistant current = null;
		if(instances.containsKey(event.getPlayer())){
			current = instances.get(event.getPlayer());
		} else {
			return;
		}
		if(current.assistantStartLocation==null)
		{
			return;
		}
		if(current.assistantStartLocation.toVector().distanceSquared(event.getTo().toVector())>25){
			current.cancel();
		}
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param currentPage the current page to set
	 */
	public void setCurrentPage(AssistantPage currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the current page
	 */
	public AssistantPage getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the pages
	 */
	public List<AssistantPage> getPages(){
		return pages;
	}

	/**
	 * Adds a page to the end of the assistant
	 * @param page the page to add
	 */
	public void addPage(AssistantPage page){
		pages.add(page);
		page.setAssistant(this);
		if(pages.size()==1){
			currentPage = page;
		}
	}

	/*
	 * Assistant events
	 */

	/**
	 * Will be called when the assistant was cancelled
	 * Override this method to provide your own message
	 */
	public void onAssistantCancel(){
		getPlayer().sendMessage(ChatColor.YELLOW+"Assistant cancelled.");
	}

	/**
	 * Will be called when the assistant was finished.
	 * Normally, this means that the user has completed the last page
	 */
	public void onAssistantFinish(){
		//TODO: some default implementation here.
	}

	/*
	 * Assistant actions
	 */

	/**
	 * Starts the assistant.
	 */
	public void start(){
		active = true;
		instances.put(getPlayer(),this);
		String message = getSeparator()+"\n";
		message += formatLine(getTitle())+"\n";
		message += getSeparator();
		sendMessage(message);
		currentPage.play();
	}

	/**
	 * Cancels the assistant. onAssistantCancel() will be called
	 * @see onAssistantCancel
	 */
	public void cancel(){
		closeScreen();
		onAssistantCancel();
		sendMessage(getSeparator());
		remove();
	}


	/**
	 * Stops the assistant. onAssistantFinish() will be called
	 * @see onAssistantFinish
	 */
	public void stop(){
		closeScreen();
		onAssistantFinish();
		sendMessage(getSeparator());
		remove();
	}

	protected void closeScreen(){
		active = false;
	}

	private void remove(){
		instances.remove(getPlayer());
		if(!heldBackChat.equals("")){
			sendMessage(ChatColor.YELLOW+"This is the chat held back for you:");
			sendMessage(heldBackChat);
		}

	}
	/*
	 * Misc actions
	 */

	/**
	 * Sends a message to the player
	 * Multilines will be sent seperately
	 */
	public void sendMessage(String text){
		for(String line:text.split("\n")){
			getPlayer().sendMessage(line);
		}
	}
	

	/**
	 * @returns the seperator
	 */
	public String getSeparator(){
		return ChatColor.LIGHT_PURPLE+"|---------------------------------------------------|";
	}

	/**
	 * Use this to format one line
	 * @param line the line to format
	 * @returns the formatted line
	 */
	public String formatLine(String line){
		return ChatColor.LIGHT_PURPLE+"| "+ChatColor.WHITE+line;
	}

	/**
	 * When you set a start location, the assistant will be cancelled when the player goes
	 * 5 meters away from this location
	 * @param assistantStartLocation the assistantStartLocation to set
	 */
	public void setAssistantStartLocation(Location assistantStartLocation) {
		this.assistantStartLocation = assistantStartLocation;
	}

	/**
	 * @return the assistantStartLocation
	 */
	public Location getAssistantStartLocation() {
		return assistantStartLocation;
	}

	/**
	 * Repeats the current page.
	 * @deprecated please return AssistantAction.REPEAT in AssistantPage.onPageInput instead
	 * @see AssistantAction
	 */
	@Deprecated
	public void repeatCurrentPage(){
		pages.add(currentPageIndex+1, getCurrentPage());
	}

	public boolean isActive(){
		return active;
	}

	public void sendMessage(Icon icon, String title, String text){
		sendMessage(icon.getMessage()+" "+ChatColor.WHITE+title+"\n"+ChatColor.WHITE+text);
	}
	
	public void render(AssistantPage page){
		
	}
	
	public boolean useGUI(){
		return false;
	}
}
