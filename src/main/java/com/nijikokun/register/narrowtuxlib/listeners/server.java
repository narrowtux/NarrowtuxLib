package com.nijikokun.register.narrowtuxlib.listeners;

// Imports for MyPlugin
import com.narrowtux.narrowtuxlib.NarrowtuxLib;
import com.nijikokun.register.narrowtuxlib.payment.Methods;

// Bukkit Imports
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

public class server extends ServerListener {
    private NarrowtuxLib plugin;

    public server(NarrowtuxLib narrowtuxLib) {
        this.plugin = narrowtuxLib;
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        // Check to see if the plugin thats being disabled is the one we are using
        if (Methods.hasMethod()) {
            if(Methods.checkDisabled(event.getPlugin())) {
                Methods.reset();
                System.out.println("[" + plugin.info.getName() + "] Payment method was disabled. No longer accepting payments.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        // Check to see if we need a payment method
        if (!Methods.hasMethod() && Methods.setMethod(plugin.getServer().getPluginManager())) {
            System.out.println("[" + plugin.info.getName() + "] Payment method found (" + Methods.getMethod().getName() + " version: " + Methods.getMethod().getVersion() + ")");
        }
    }
}