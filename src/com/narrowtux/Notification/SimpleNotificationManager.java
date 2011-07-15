package com.narrowtux.Notification;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import com.narrowtux.Main.NarrowtuxLib;
import com.narrowtux.Utils.FileUtils;

public class SimpleNotificationManager implements NotificationManager {

	private Map<Integer, Notification> notifications = new HashMap<Integer, Notification>();
	private int lastId = 1;
	
	@Override
	public Notification sendNotification(Player player, String title,
			String text) {
		return sendNotification(player.getName(), title, text);
	}

	@Override
	public Notification sendNotification(String player, String title,
			String text) {
		Notification n = new Notification(player, title, text);
		return sendNotification(n);
	}

	@Override
	public Notification sendNotification(Notification notification) {
		addNotification(notification);
		notification.print();
		return notification;
	}

	@Override
	public List<Notification> getPendingNotifications() {
		return (List<Notification>)notifications.values();
	}

	@Override
	public List<Notification> getPendingNotifications(String player) {
		List<Notification> ret = new ArrayList<Notification>();
		for(Notification n:notifications.values()){
			if(n.getReceiver().toLowerCase().equals(player.toLowerCase())){
				ret.add(n);
			}
		}
		return ret;
	}

	@Override
	public List<Notification> getPendingNotifications(Player player) {
		return getPendingNotifications(player.getName());
	}

	@Override
	public void clear() {
		notifications.clear();
	}

	@Override
	public void clear(String player) {
		for(Notification n:notifications.values()){
			if(n.getReceiver().equals(player)){
				notifications.remove(n);
			}
		}
	}

	@Override
	public void clear(Player player) {
		clear(player.getName());
	}

	@Override
	public void remove(Notification n) {
		notifications.remove(n.getId());
	}
	
	public void handleJoinEvent(PlayerJoinEvent event){
		List<Notification> pending = getPendingNotifications(event.getPlayer());
		Player p = event.getPlayer();
		if(pending.size()>0)
			p.sendMessage(ChatColor.RED+"You've got "+pending.size()+" unread notifications! Type /nt");
	}

	@Override
	public void addNotification(Notification n) {
		notifications.put(lastId, n);
		n.setId(lastId);
		lastId++;
	}

	public void load() {
		try {
			String cnt = FileUtils.getContents(getDataFile());
			int version = 0;
			for(String line:cnt.split("\n")){
				String args[] = line.split(",");
				if(args[0].equals("version")){
					version = Integer.valueOf(args[1]);
					continue;
				}
				if(version == 1 && args.length >= 3){
					String receiver = args[0];
					String title = unescapeString(args[1]);
					String text = unescapeString(args[2]);
					Notification n = new Notification(receiver, title, text);
					if(args.length == 4){
						String cmd = unescapeString(args[3]);
						n.setCommandAction(cmd);
					}
					addNotification(n);
				}
			}
		} catch (FileNotFoundException e) {
			return;
		}
	}

	public void save() {
		String cnt = "version,1\n";
		for(Notification n:notifications.values()){
			cnt+=n.getReceiver()+","+escapeString(n.getTitle())+","+escapeString(n.getText())+","+escapeString(n.getCommandAction())+"\n";
		}
		FileUtils.setContents(getDataFile(), cnt);
	}
	
	private File getDataFile(){
		return new File(NarrowtuxLib.getInstance().getDataFolder(), "notifications.csv");
	}
	
	private String escapeString(String str){
		return str.replaceAll("\n", "\\n").replaceAll(",", ";;");
	}
	
	private String unescapeString(String str){
		return str.replaceAll("\\n", "\n").replaceAll(";;", ",");
	}

	@Override
	public Notification get(int id) {
		return notifications.get(id);
	}
}
