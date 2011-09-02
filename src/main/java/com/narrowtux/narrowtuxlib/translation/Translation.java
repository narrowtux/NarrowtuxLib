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

package com.narrowtux.narrowtuxlib.translation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

import com.narrowtux.narrowtuxlib.utils.FlatFileReader;

/**
 * Translation provides a basic localisation. The files have to have the following format:
 * @code key=value
 * @endcode
 * A translation version can be stored like this:
 * @code version=1
 * @endcode
 * @see getVersion
 * @author tux
 *
 */
public class Translation {
	private Map<String, String> translations = new HashMap<String, String>();
	private File file;
	private int version;

	/**
	 * Loads the translation again from the given file
	 * @param file the file to load.
	 */
	public void reload(File file){
		this.file = file;
		translations.clear();
		load();
	}

	/**
	 * Reloads the translation from the stored file
	 */
	public void reload(){
		translations.clear();
		load();
	}

	private void load() {
		FlatFileReader reader = new FlatFileReader(file, true);
		for(String key:reader.keys()){
			if(!key.equals("version")){
				String trans = parseColors(reader.getString(key, "No translation for '"+key+"' found!"));
				trans = trans.replaceAll("\\\\n", "\n");
				translations.put(key, trans);
			}
		}
		version = reader.getInteger("version", 0);
	}

	/**
	 * Translates the given key to the loaded language
	 * @param key the translation key
	 * @param args arguments for the translation.
	 * @return the translation
	 */
	public String tr(String key, Object... args){
		if(!translations.containsKey(key)){
			return "Can't find key "+key+" for translation!";
		} else {
			String trans = translations.get(key);
			try{
				return String.format(trans, args);
			} catch(Exception e){
				return "Error formatting: "+trans;
			}
		}
	}

	/**
	 * Translates the given key to the loaded language
	 * @param key the translation key
	 * @return the translation
	 */
	public String tr(String key){
		if(!translations.containsKey(key)){
			return "Can't find key "+key+" for translation!";
		} else {
			String trans = translations.get(key);
			return trans;
		}
	}

	/**
	 * Converts [COLOR] into ChatColor from the given text
	 * @param str the string to convert
	 * @return the converted string
	 * @author Afforess
	 */
	public static String parseColors(String str) {
		//Method written by Afforess
		for (ChatColor color : ChatColor.values()) {
			String name = "\\[" + color.name().toUpperCase() + "]";
			str = str.replaceAll(name, color.toString());
		}
		return str;
	}

	/**
	 * @return the version of the translation.
	 */
	public int getVersion(){
		return version;
	}
}
