package com.narrowtux.Notification;

import java.util.List;

import org.bukkit.entity.Player;

public interface NotificationManager {
	/**
	 * Sends a notification to the given player directly
	 * @param player the receiver
	 * @param title the title
	 * @param text the text
	 * @return the Notification object that has been created
	 */
	public Notification sendNotification(Player player, String title, String text);
	/**
	 * Sends a notification to the given player directly
	 * @param player the name of the receiver
	 * @param title the title
	 * @param text the text
	 * @return the Notification object that has been created
	 */
	public Notification sendNotification(String player, String title, String text);
	/**
	 * Sends a custom created Notification
	 * @param notification
	 * @return the given notification
	 */
	public Notification sendNotification(Notification notification);
	/**
	 * @return a list of all pending notifications
	 */
	public List<Notification> getPendingNotifications();
	/**
	 * @param player
	 * @return a list of all pending notifications for the given player
	 */
	public List<Notification> getPendingNotifications(String player);
	/**
	 * @param player
	 * @return a list of all pending notifications for the given player
	 */
	public List<Notification> getPendingNotifications(Player player);
	/**
	 * Clears all notifications.
	 */
	public void clear();
	/**
	 * Clears all notifications for the given player
	 * @param player
	 */
	public void clear(String player);
	/**
	 * Clears all notifications for the given player
	 * @param player
	 */
	public void clear(Player player);
	/**
	 * Removes the given Notification
	 * @param n
	 */
	public void remove(Notification n);
	/**
	 * Adds a notification without printing it
	 * @param n
	 */
	public void addNotification(Notification n);
	/**
	 * 
	 * @param id the unique ID of the notification
	 * @return the Notification with the given unique ID
	 */
	public Notification get(int id);
}
