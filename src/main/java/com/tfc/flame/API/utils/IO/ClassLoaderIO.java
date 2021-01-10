package com.tfc.flame.API.utils.IO;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderIO {
	public static String readAsString(String resource) throws IOException {
		InputStream stream = ClassLoaderIO.class.getClassLoader().getResourceAsStream(resource);
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		stream.close();
		return new String(bytes);
	}
}
