package com.narrowtux.narrowtuxlib.assistant;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.narrowtux.narrowtuxlib.NarrowtuxLib;

public class GuiAssistant extends Assistant {
	private AssistantScreen screen;

	public GuiAssistant(Player p) {
		super(p);
	}
	
	@Override
	public boolean useGUI(){
		if(NarrowtuxLib.isSpoutInstalled()){
			SpoutPlayer player = getSpoutPlayer();
			return player.isSpoutCraftEnabled();
		} else {
			return false;
		}
	}
	
	public SpoutPlayer getSpoutPlayer(){
		return (SpoutPlayer)getPlayer();
	}

	@Override
	public void sendMessage(Icon icon, String title, String text){
		if(useGUI()){
			MessageBox msg = new MessageBox(title, text, icon, getSpoutPlayer());
			if(getScreen()!=null)
				getScreen().attachMessageBox(msg);
			else
				getSpoutPlayer().getMainScreen().attachPopupScreen(msg);
		} else {
			sendMessage(icon.getMessage()+" "+ChatColor.WHITE+title+"\n"+ChatColor.WHITE+text);
		}
	}
	
	@Override
	public void sendMessage(String text){
		if(useGUI()){
			MessageBox msg = new MessageBox("Message", text, Icon.INFORMATION, getSpoutPlayer());
			if(screen!=null)
				((AssistantScreen)screen).attachMessageBox(msg);
			else
				getSpoutPlayer().getMainScreen().attachPopupScreen(msg);
		} else {
			for(String line:text.split("\n")){
				getPlayer().sendMessage(line);
			}
		}
	}
	
	@Override
	protected void closeScreen(){
		super.closeScreen();
		if(useGUI()){
			((PopupScreen)screen).close();
			screen = null;
		}
	}
	
	public AssistantScreen getScreen(){
		return screen;
	}
	
	/**
	 * Starts the assistant.
	 */
	@Override
	public void start(){
		active = true;
		instances.put(getPlayer(),this);
		if(useGUI()){
			screen = createAssistantScreen();
			getSpoutPlayer().getMainScreen().attachPopupScreen((PopupScreen) screen);
		} else {
			String message = getSeparator()+"\n";
			message += formatLine(getTitle())+"\n";
			message += getSeparator();
			sendMessage(message);
		}
		currentPage.play();
	}

	public AssistantScreen createAssistantScreen(){
		return new AssistantScreen(this);
	}
	
	@Override 
	public void render(AssistantPage page){
		getScreen().renderPage(page);
	}
}
