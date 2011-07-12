package com.narrowtux.translation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

import com.narrowtux.Utils.FlatFileReader;

public class Translation {
	private Map<String, String> translations = new HashMap<String, String>();
	private File file;
	private int version;
	
	public void reload(File file){
		this.file = file;
		translations.clear();
		load();
	}
	
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
	
	public String tr(String key){
		if(!translations.containsKey(key)){
			return "Can't find key "+key+" for translation!";
		} else {
			String trans = translations.get(key);
			return trans;
		}
	}
	
	public static String parseColors(String str) {
		//Method written by Afforess
		for (ChatColor color : ChatColor.values()) {
			String name = "\\[" + color.name().toUpperCase() + "]";
			str = str.replaceAll(name, color.toString());
		}
		return str;
	}
	
	public int getVersion(){
		return version;
	}
}
