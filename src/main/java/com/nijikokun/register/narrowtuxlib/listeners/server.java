package com.nijikokun.register.narrowtuxlib.listeners;

// Imports for MyPlugin
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import com.narrowtux.narrowtuxlib.NarrowtuxLib;
import com.nijikokun.register.narrowtuxlib.payment.Methods;

public class server implements Listener {
    private NarrowtuxLib plugin;

    public server(NarrowtuxLib narrowtuxLib) {
        this.plugin = narrowtuxLib;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        // Check to see if the plugin thats being disabled is the one we are using
        if (Methods.hasMethod()) {
            if(Methods.checkDisabled(event.getPlugin())) {
                Methods.reset();
                System.out.println("[" + plugin.info.getName() + "] Payment method was disabled. No longer accepting payments.");
            }
        }
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        // Check to see if we need a payment method
        if (!Methods.hasMethod() && Methods.setMethod(plugin.getServer().getPluginManager())) {
            System.out.println("[" + plugin.info.getName() + "] Payment method found (" + Methods.getMethod().getName() + " version: " + Methods.getMethod().getVersion() + ")");
        }
    }
}