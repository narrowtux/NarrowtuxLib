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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.jar.JarFile;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class FileUtils {

	/**
	 * Reads the contents of a file
	 * @param file the file to read
	 * @return the content of the file
	 * @throws FileNotFoundException when the file wasn't found
	 */
	public static String getContents(File file) throws FileNotFoundException{
		if(file.exists()){
			FileInputStream input;
			try{
				input = new FileInputStream(file.getAbsoluteFile());
				InputStreamReader ir = new InputStreamReader(input);
				BufferedReader r = new BufferedReader(ir);
				String ret = "";
				while(true){
					String line = r.readLine();
					if(line!=null)
					{
						ret+=line+"\n";
					} else {
						break;
					}
				}
				return ret;
			} catch (Exception e){
				return "";
			}
		} else {
			throw new FileNotFoundException("File not found: "+file.getAbsoluteFile());
		}
	}

	/**
	 * Sets the content of a file
	 * @param file the file to write
	 * @param content the content to write into the given file
	 */
	public static void setContents(File file, String content){
		try {
			FileOutputStream output = new FileOutputStream(file.getAbsoluteFile());
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(output));
			w.write(content);
			w.flush();
			output.close();
		} catch (Exception e){
		}
	}

	/**
	 * Converts a Location-Object into a string.
	 * @code
	 * worldname,x,y,z
	 * @endcode
	 * @param loc
	 * @return the converted string
	 * @see locationFromString
	 */
	public static String locationToString(Location loc){
		if(loc==null)
		{
			return "(null)";
		}
		String ret = "";
		ret+=loc.getWorld().getName()+",";
		ret+=loc.getX()+","+loc.getY()+","+loc.getZ();
		return ret;
	}

	/**
	 * Converts a string back to a location
	 * @param string the string in this format: @code worldname,x,y,z @endcode
	 * @param server the server to get a world object
	 * @return the converted Location object
	 */
	public static Location locationFromString(String string, Server server){
		if(string.equals("(null)")){
			return null;
		}
		try{
			String args[] = string.split(",");
			World w = server.getWorld(args[0]);
			double x = Double.valueOf(args[1]);
			double y = Double.valueOf(args[2]);
			double z = Double.valueOf(args[3]);
			Location loc = new Location(w,x,y,z);
			return loc;
		} catch(Exception e){
			System.out.println(string);
			return null;
		}
	}
	
	public static void copyFromJarToDisk(String entry, JavaPlugin plugin, File pluginFile) throws IOException{
		JarFile jar = new JarFile(pluginFile);
		InputStream is = jar.getInputStream(jar.getJarEntry(entry));
		OutputStream os = new FileOutputStream(new File(plugin.getDataFolder(), entry));
		byte[] buffer = new byte[4096];
		int length;
		while (is!=null&&(length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		os.close();
		is.close();
	}
}
