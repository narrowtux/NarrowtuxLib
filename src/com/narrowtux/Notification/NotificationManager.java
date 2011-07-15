package com.narrowtux.Notification;

import java.util.List;

import org.bukkit.entity.Player;

public interface NotificationManager {
	public Notification sendNotification(Player player, String title, String text);
	public Notification sendNotification(String player, String title, String text);
	public Notification sendNotification(Notification notification);
	public List<Notification> getPendingNotifications();
	public List<Notification> getPendingNotifications(String player);
	public List<Notification> getPendingNotifications(Player player);
	public void clear();
	public void clear(String player);
	public void clear(Player player);
	public void remove(Notification n);
	public void addNotification(Notification n);
	public Notification get(int id);
}
