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
