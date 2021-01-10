package com.tfc.flame.API.utils.IO;

import com.tfc.flamemc.FlameLauncher;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
	public static void write(File file, String text) {
		try {
			if (FlameLauncher.isDev) file = new File("run/" + file);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(file);
			stream.write(text.getBytes());
			stream.close();
		} catch (Throwable ignored) {
		}
	}
	
	public static void write(File file, byte[] bytes) {
		try {
			if (FlameLauncher.isDev) file = new File("run/" + file);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(file);
			stream.write(bytes);
			stream.close();
		} catch (Throwable ignored) {
		}
	}
}
