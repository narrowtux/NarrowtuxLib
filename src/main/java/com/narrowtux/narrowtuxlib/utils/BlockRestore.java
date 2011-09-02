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

package com.narrowtux.narrowtuxlib.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
/**
 * This class can be used to restore blocks after some time, for example after an explosion.
 * Construct the object with a map of the locations and types (aka Location, ItemStack) and put the object into a scheduler.
 * @author tux
 */
public class BlockRestore implements Runnable {
	private Map<Location, ItemStack> blocks = new HashMap<Location, ItemStack>();
	/**
	 * Constructs the object
	 * @param b
	 */
	public BlockRestore(Map<Location, ItemStack> b){
		blocks = b;
	}

	@Override
	public void run() {
		for(Location loc:blocks.keySet()){
			ItemStack type = blocks.get(loc);
			Block b = loc.getBlock();
			b.setType(type.getType());
			b.setData((byte) type.getDurability());
		}
	}
}
