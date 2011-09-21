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

package com.narrowtux.narrowtuxlib;

import java.io.File;

import com.narrowtux.narrowtuxlib.utils.FlatFileReader;

public class Configuration {
	private boolean installSpout = true;
	private boolean autoUpdate = true;
	private FlatFileReader reader;
	public Configuration(File file){
		reader = new FlatFileReader(file, false);
		installSpout = reader.getBoolean("installspout", true);
		autoUpdate = reader.getBoolean("autoupdate", true);
		reader.write();
	}
	
	public boolean isInstallSpout(){
		return installSpout;
	}
	
	public boolean isAutoUpdate(){
		return autoUpdate;
	}
}
