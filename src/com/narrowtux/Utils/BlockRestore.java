package com.narrowtux.Utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
/**
 * This class can be used to restore blocks after some time, for example after an explosion.
 * Construct the object with a map of the locations and types (aka Location, ItemStack) and put the object into a scheduler.
 * @author tux
 *
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
