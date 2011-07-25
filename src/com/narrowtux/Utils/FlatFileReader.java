package com.narrowtux.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;

/**
 * The FlatFileReader can be used to read and automatically write config files or simple key=value storages.
 * @code
 * FlatFileReader reader = new FlatFileReader(new File("/foo/bar.txt"), false);
 * int maximumInstances = reader.getInteger("maxinstances", 3);
 * reader.write();
 * @endcode
 * The above code-block reads an integer value from the file /foo/bar.txt. 
 * When the file doesn't exist or hasn't the key "maxinstances", the fallback value (3) will be returned.
 * Also, when calling reader.write(), the fallback value would be written to the file.
 * So, this is just perfect to create easy and auto-updating configuration files.
 * Whenever you add a new config-value, it'll be added to the file while preserving the configuration the user already made.
 * @author narrowtux
 *
 */
public class FlatFileReader {
	private File file;
	private boolean caseSensitive;
	private Map<String, List<String>> keySet = new HashMap<String,List<String>>();
	
	/**
	 * Constructs a FlatFileReader
	 * @param file the file to read
	 * @param caseSensitive when set to false, all keys will be converted to lowercase, and the keys you provide in the getXXX-Methods have to be lowercase too.
	 */
	public FlatFileReader(File file, boolean caseSensitive){
		this.file = file;
		this.caseSensitive = caseSensitive;
		reload();
	}
	
	/**
	 * Reloads the file
	 */
	public void reload(){
		keySet.clear();
		load();
	}
	
	/**
	 * Gets an integer value from the file.
	 * @param key
	 * @param fallback
	 * @return the integer for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public int getInteger(String key, int fallback){
		if(keySet.containsKey(key)){
			int ret;
			try{
				ret = Integer.valueOf(get(key));
			} catch(Exception e){
				ret = fallback;
			}
			return ret;
		} else {
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(fallback));
			keySet.put(key, list);
			return fallback;
		}
	}
	
	/**
	 * Gets an string value from the file.
	 * @param key
	 * @param fallback
	 * @return the string for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public String getString(String key, String fallback){
		if(keySet.containsKey(key)){
			return get(key);
		} else {
			
			List<String> list;
			list = keySet.get(key);
			if(list==null)
			{
				list = new ArrayList<String>();
				keySet.put(key, list);
			}
			list.add(fallback);
			return fallback;
		}
	}
	
	/**
	 * Gets an boolean value from the file. It will accept "true" and "false".
	 * @param key
	 * @param fallback
	 * @return the boolean for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public boolean getBoolean(String key, boolean fallback){
		if(keySet.containsKey(key)){
			boolean ret;
			try{
				ret = Boolean.valueOf(get(key));
			} catch(Exception e){
				ret = fallback;
			}
			return ret;
		} else {
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(fallback));
			keySet.put(key, list);
			return fallback;
		}
	}
	
	/**
	 * Gets an double value from the file.
	 * @param key
	 * @param fallback
	 * @return the double for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public double getDouble(String key, double fallback){
		if(keySet.containsKey(key)){
			double ret;
			try{
				ret = Double.valueOf(get(key));
			} catch(Exception e){
				ret = fallback;
			}
			return ret;
		} else {
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(fallback));
			keySet.put(key, list);
			return fallback;
		}
	}
	
	/**
	 * Gets an float value from the file.
	 * @param key
	 * @param fallback
	 * @return the float for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public float getFloat(String key, float fallback){
		if(keySet.containsKey(key)){
			float ret;
			try{
				ret = Float.valueOf(get(key));
			} catch(Exception e){
				ret = fallback;
			}
			return ret;
		} else {
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(fallback));
			keySet.put(key, list);
			return fallback;
		}
	}
	
	/**
	 * Gets an material value from the file. It parses material-ids as well as Bukkit-Material names.
	 * @param key
	 * @param fallback
	 * @return the material for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public Material getMaterial(String key, Material fallback){
		if(keySet.containsKey(key)){
			Material ret;
			try{
				ret = Material.getMaterial(get(key));
			} catch(Exception e){
				try{
					ret = Material.getMaterial(Integer.valueOf(get(key)));
				} catch(Exception ex){
					return fallback;
				}
			}
			return ret;
		} else {
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(fallback));
			keySet.put(key, list);
			return fallback;
		}
	}
	
	/**
	 * @return all loaded keys
	 */
	public Set<String> keys(){
		return keySet.keySet();
	}
	
	/**
	 * When one key exists multiple times, use this method to get all values as strings in a list.
	 * @param key the key to search
	 * @return all values for that key.
	 */
	public List<String> values(String key){
		if(keySet.containsKey(key)){
			return keySet.get(key);
		} else {
			return new ArrayList<String>();
		}
	}
	
	private boolean load(){
		if(file.exists()){
			FileInputStream input;
			try{
				input = new FileInputStream(file.getAbsoluteFile());
				InputStreamReader ir = new InputStreamReader(input);
				BufferedReader r = new BufferedReader(ir);
				while(true){
					String line = r.readLine();
					if(line==null)
						break;
					if(!line.startsWith("#")){
						String splt[] = line.split("=");
						if(splt.length==2){
							String key = splt[0];
							String value = splt[1];
							if(!caseSensitive){
								key = key.toLowerCase();
							}
							if(keySet.containsKey(key)){
								keySet.get(key).add(value);
							} else {
								List<String> list = new ArrayList<String>();
								list.add(value);
								keySet.put(key, list);
							}
						}
					}
				}
				r.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		} else {
			System.out.println("File "+file.getAbsoluteFile()+" not found.");
			return false;
		}
		return true;
	}
	
	private String get(String key){
		return keySet.get(key).get(0);
	}

	/**
	 * Writes the keyset to the file
	 */
	public void write() {
		String finalFile = "";
		for(String key: keys()){
			for(String value: keySet.get(key)){
				finalFile+=key+"="+value+"\n";
			}
		}
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Could not create Datafile ("+e.getCause()+"). Aborting.");
				return;
			}
		}
		try {
			FileOutputStream output = new FileOutputStream(file.getAbsoluteFile());
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(output));
			w.write(finalFile);
			w.flush();
			output.close();
		} catch (Exception e){
			System.out.println("Could not write configuration file.");
		}
	}
	
	public void addString(String key, String value){
		List<String> list = keySet.get(key);
		if(list==null)
		{
			list = new ArrayList<String>();
			keySet.put(key, list);
		}
		list.add(value);
	}
	
	public void clear(){
		keySet.clear();
	}
}
