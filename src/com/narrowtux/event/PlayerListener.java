package com.narrowtux.event;

import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;

import com.narrowtux.Assistant.Assistant;
import com.narrowtux.Main.NarrowtuxLib;
import com.narrowtux.Notification.SimpleNotificationManager;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {
	@Override
	public void onPlayerChat(PlayerChatEvent event){
		//Cool, not?
		Assistant.onPlayerChat(event);
	}
	
	@Override
	public void onPlayerMove(PlayerMoveEvent event){
		Assistant.onPlayerMove(event);
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		((SimpleNotificationManager)NarrowtuxLib.getNotificationManager()).handleJoinEvent(event);
	}
}
