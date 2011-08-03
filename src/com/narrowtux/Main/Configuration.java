package com.narrowtux.Main;

import java.io.File;

import com.narrowtux.Utils.FlatFileReader;

public class Configuration {
	private boolean installSpout = true;
	private FlatFileReader reader;
	public Configuration(File file){
		reader = new FlatFileReader(file, false);
		installSpout = reader.getBoolean("installspout", true);
		reader.write();
	}
	
	public boolean isInstallSpout(){
		return installSpout;
	}
}
