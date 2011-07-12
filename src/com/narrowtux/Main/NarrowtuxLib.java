package com.narrowtux.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import com.narrowtux.event.PlayerListener;

public class NarrowtuxLib extends JavaPlugin {
	private static Logger log = Bukkit.getServer().getLogger();
	private PlayerListener playerListener = new PlayerListener();

	@Override
	public void onDisable() {
		//Attempt to auto update if file is available
        try {
            File directory = new File(Bukkit.getServer().getUpdateFolder());
            if (directory.exists()) {
                File plugin = new File(directory.getPath(), "NarrowtuxLib.jar");
                if (plugin.exists()) {
                    FileUtil.copy(plugin, this.getFile());
                    plugin.delete();
                }
            }
        }
        catch (Exception e) {}
		sendDescription("disabled");
	}

	@Override
	public void onEnable() {
		(new Thread() {
            public void run() {
                update();
            }
        }).start();
		registerEvents();
		sendDescription("enabled");
	}

	private void registerEvents() {
		registerEvent(Type.PLAYER_CHAT, playerListener, Priority.Lowest);
		registerEvent(Type.PLAYER_MOVE, playerListener);
	}

	private void registerEvent(Type type, Listener listener, Priority priority){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(type, listener, priority, this);
	}

	private void registerEvent(Type type, Listener listener){
		registerEvent(type, listener, Priority.Normal);
	}

	public void sendDescription(String startup){
		PluginDescriptionFile pdf = getDescription();
		String authors = "";
		for(String name: pdf.getAuthors()){
			if(authors.length()>0){
				authors+=", ";
			}
			authors+=name;
		}
		log.log(Level.INFO, "["+pdf.getName()+"] v"+pdf.getVersion()+" by ["+authors+"] "+startup+".");
	}

	public static Logger getLogger(){
		return log;
	}

	protected boolean isUpdateAvailable() {
		log.info("Version: "+getVersion());
		try {
			URL url = new URL("http://tetragaming.com/narrowtux/plugins/NarrowtuxLibVersion.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String str;
			while ((str = in.readLine()) != null) {
				String[] split = str.split("\\.");
				int version = Integer.parseInt(split[0]) * 100 + Integer.parseInt(split[1]) * 10 + Integer.parseInt(split[2]);
				if (version > getVersion()){
					in.close();
					return true;
				}
			}
			in.close();
		}
		catch (Exception e) {
		}
		return false;
	}

	public int getVersion() {
		try {
			String[] split = this.getDescription().getVersion().split("\\.");
			return Integer.parseInt(split[0]) * 100 + Integer.parseInt(split[1]) * 10 + Integer.parseInt(split[2]);
		}
		catch (Exception e) {}
		return -1;
	}
	
	protected void update() {
        if (!isUpdateAvailable()) {
            return;
        }
        try {
            File directory = new File(Bukkit.getServer().getUpdateFolder());
            if (!directory.exists()) {
                directory.mkdir();
            }
            File plugin = new File(directory.getPath(), "NarrowtuxLib.jar");
            if (!plugin.exists()) {
                URL bukkitContrib = new URL("http://tetragaming.com/narrowtux/plugins/NarrowtuxLib.jar");
                HttpURLConnection con = (HttpURLConnection)(bukkitContrib.openConnection());
                System.setProperty("http.agent", ""); //Spoofing the user agent is required to track stats
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.100 Safari/534.30");
                ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
                FileOutputStream fos = new FileOutputStream(plugin);
                fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            }
        }
        catch (Exception e) {}
    }

}
