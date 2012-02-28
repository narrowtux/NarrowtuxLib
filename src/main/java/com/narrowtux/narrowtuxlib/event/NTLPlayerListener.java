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

package com.narrowtux.narrowtuxlib.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.narrowtux.narrowtuxlib.NarrowtuxLib;
import com.narrowtux.narrowtuxlib.assistant.Assistant;
import com.narrowtux.narrowtuxlib.notification.SimpleNotificationManager;

public class NTLPlayerListener implements Listener {
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerChat(PlayerChatEvent event){
		//Cool, not?
		Assistant.onPlayerChat(event);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		try{
			Assistant.onPlayerMove(event);
		}catch(NoClassDefFoundError e){}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		((SimpleNotificationManager)NarrowtuxLib.getNotificationManager()).handleJoinEvent(event);
	}
}
