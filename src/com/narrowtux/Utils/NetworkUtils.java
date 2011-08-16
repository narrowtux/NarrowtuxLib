package com.narrowtux.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Logger;

public class NetworkUtils {
	
	/**
	 * Downloads from the given url to the file.
	 * @param log
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	public static void download(Logger log, URL url, File file) throws IOException {
		boolean hasLog = log!=null;
	    if (!file.getParentFile().exists())
	        file.getParentFile().mkdir();
	    if (file.exists())
	        file.delete();
	    file.createNewFile();
	    final int size = url.openConnection().getContentLength();
	    if(hasLog) log.info("[NarrowTuxLib] Downloading " + file.getName() + " (" + size / 1024 + "kb) ...");
	    final InputStream in = url.openStream();
	    final OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
	    final byte[] buffer = new byte[1024];
	    int len, downloaded = 0, msgs = 0;
	    final long start = System.currentTimeMillis();
	    while ((len = in.read(buffer)) >= 0) {
	        out.write(buffer, 0, len);
	        downloaded += len;
	        if ((int)((System.currentTimeMillis() - start) / 500) > msgs) {
	        	if(hasLog) log.info("[NarrowtuxLib] " + (int)((double)downloaded / (double)size * 100d) + "%");
	            msgs++;
	        }
	    }
	    in.close();
	    out.close();
	    if(hasLog) log.info("[NarrowTuxLib] Download finished");
	}
	
	/**
	 * Downloads from the given url to the file without spamming the log.
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	public static void download(URL url, File file) throws IOException {
		download(null, url, file);
	}
}
