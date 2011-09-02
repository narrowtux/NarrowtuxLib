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

package com.narrowtux.event;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;

public class NTLServerListener extends ServerListener {
	private Methods Methods = null;
	private Method method = null;

    public NTLServerListener() {
        this.Methods = new Methods();
    }

	@Override
	public void onPluginDisable(PluginDisableEvent event) {
		// Check to see if the plugin thats being disabled is the one we are using
        if (this.Methods != null && this.Methods.hasMethod()) {
            Boolean check = this.Methods.checkDisabled(event.getPlugin());

            if(check) {
                method = null;
                System.out.println("[NarrowtuxLib] Payment method was disabled. No longer accepting payments.");
            }
        }
	}

	@Override
	public void onPluginEnable(PluginEnableEvent event) {
		// Check to see if we need a payment method
        if (!this.Methods.hasMethod()) {
            if(this.Methods.setMethod(event.getPlugin())) {
                // You might want to make this a public variable inside your MAIN class public Method Method = null;
                // then reference it through this.plugin.Method so that way you can use it in the rest of your plugin ;)
            	method = this.Methods.getMethod();
                System.out.println("[NarrowtuxLib] Payment method found (" + method.getName() + " version: " + method.getVersion() + ")");
            }
        }
	}

	public Method getMethod(){
		return method;
	}
}
