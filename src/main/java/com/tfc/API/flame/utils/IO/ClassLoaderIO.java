package com.tfc.API.flame.utils.IO;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderIO {
	public static String readAsString(String resource) throws IOException {
		InputStream stream = ClassLoaderIO.class.getClassLoader().getResourceAsStream(resource);
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		try {
			stream.close();
		} catch (Throwable ignored) {
		}
		return new String(bytes);
	}
}
