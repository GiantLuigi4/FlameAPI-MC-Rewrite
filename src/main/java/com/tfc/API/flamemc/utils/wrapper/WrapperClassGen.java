package com.tfc.API.flamemc.utils.wrapper;

import com.tfc.API.flame.utils.IO.ClassLoaderIO;
import com.tfc.API.flame.utils.data.properties.Properties;
import com.tfc.API.flamemc.utils.mapping.Flame;
import com.tfc.flamemc.FlameLauncher;
import com.tfc.mappings.structure.Class;

import java.io.IOException;

public class WrapperClassGen {
	public static final Properties classes;
	
	static {
		FlameLauncher.getLoader().getReplacementGetters().put("flame_api:generate_wrapper", WrapperClassGen::generateWrapper);
		try {
			classes = new Properties(ClassLoaderIO.readAsString("wrapper_classes.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static byte[] generateWrapper(String name) {
		Class flameMappedClass = Flame.getFromObsf(name.replace(".", "/"));
		if (flameMappedClass != null) {
			String flameName = flameMappedClass.getPrimaryName();
			if (classes.getEntries().contains(flameName)) {
				String file = classes.getValue(flameName);
				StringBuilder classFile = new StringBuilder("package ").append(flameName, 0, flameName.lastIndexOf(".")).append(";\n");
				classFile.append("public class ").append(flameName, flameName.lastIndexOf(".") + 1, flameName.length()).append(" {\n}");
				try {
					Properties clazz = new Properties(ClassLoaderIO.readAsString("wrappers/" + file + ".properties"));
					for (String entry : clazz.getEntries()) {
						String nameAndType = clazz.getValue(entry);
						if (nameAndType.startsWith("static.method")) {
							String methodName = nameAndType.substring("static.method_".length());
						}
					}
				} catch (Throwable ignored) {
				}
				System.out.println(classFile.toString());
			}
		}
		return null;
	}
}
