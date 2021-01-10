package com.tfc.flamemc.API.utils.wrapper;

import com.tfc.flame.API.utils.IO.ClassLoaderIO;
import com.tfc.flame.API.utils.data.properties.Properties;
import com.tfc.flamemc.API.utils.mapping.Flame;
import com.tfc.flamemc.API.utils.mapping.Mapping;
import com.tfc.flamemc.FlameLauncher;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Field;
import com.tfc.mappings.structure.Method;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

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
	
	public static void init() {
		//just class loads the class
	}
	
	private static byte[] generateWrapper(String name) {
//		Class flameMappedClass = Flame.getFromObsf(name.replace(".", "/"));
//		if (flameMappedClass != null) {
//			String flameName = flameMappedClass.getSecondaryName();
		if (classes.getEntries().contains(name.replace(".", "/"))) {
			Class flameMappedClass = Flame.getFromFlame(name);
			Class superClass = Mapping.getWithoutFlame(flameMappedClass.getPrimaryName());
			
			String flameName = name.replace(".", "/");
//			if (classes.getEntries().contains(flameName)) {
			String file = classes.getValue(flameName);
			StringBuilder classFile = new StringBuilder("package ").append(flameName.replace("/", "."), 0, flameName.lastIndexOf("/")).append(";\n");
			classFile.append("public class ").append(flameName, flameName.lastIndexOf("/") + 1, flameName.length()).append(" extends ").append(superClass.getSecondaryName()).append(" {");
			
			boolean hasNonStatic = false;
			
			try {
				Properties clazz = new Properties(ClassLoaderIO.readAsString("wrappers/" + file + ".properties"));
				
				for (String entry : clazz.getEntries()) {
					String nameAndType = clazz.getValue(entry);

					if (nameAndType.startsWith("static.field") || nameAndType.startsWith("field")) {
						boolean isStatic = nameAndType.startsWith("static");

						hasNonStatic = hasNonStatic || !isStatic;

						String otherName = entry.substring(0, entry.indexOf("|"));
						String otherDesc = entry.substring(entry.indexOf("|") + 1);

						System.out.println(flameMappedClass);
						Field field = Mapping.getField(flameMappedClass, otherName, otherDesc);

						classFile.append("\n\tpublic " + (isStatic ? "static " : "") + parseSourceDescFromBytecodeDesc(otherDesc) + " " + field.getPrimary() + ";");
					}

					if (nameAndType.startsWith("static.method") || nameAndType.startsWith("method")) {
						boolean isStatic = nameAndType.startsWith("static");
						
						hasNonStatic = hasNonStatic || !isStatic;

//							System.out.println(methodName);
//							System.out.println(flameMappedClass.getMethodSecondary(nameAndType.substring("static.method_".length())));
//							System.out.println(flameMappedClass.getMethodPrimary(nameAndType.substring("static.method_".length())));
						
						//register(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;
						String otherName = entry.substring(0, entry.indexOf("("));
						String otherDesc = entry.substring(entry.indexOf("("));

//							System.out.println(otherName);
//							System.out.println(otherDesc);
						
						Method method = Mapping.getMethod(flameMappedClass, otherName, otherDesc);

						String typeName = otherDesc.substring(otherDesc.indexOf(")"));
						if (typeName.contains(";")) {
							typeName = otherDesc.substring(otherDesc.indexOf(")") + 2, typeName.length());
						} else {
							typeName = parseSourceDescFromBytecodeDesc(otherDesc.substring(otherDesc.indexOf(")") + 1));
						}

						String type = getFlameFor(typeName);
//							System.out.println(type);
						String paramsStr = otherDesc.substring(1, otherDesc.indexOf(")"));
//							System.out.println(paramsStr);
						String params = parseParams(paramsStr);
						
						boolean hasReturn = !type.equals("void");
						
						classFile.append("\n\tpublic " + (isStatic ? "static " : "") + type + " " + method.getPrimary() + "(" + params + ") {\n\t\t" +
								(hasReturn ? "return " : "") + (isStatic ? (superClass.getSecondaryName() + ".") : "this.") + method.getSecondary() +
								";\n\t}\n");
					}
				}
				classFile.append("}");
				String classFileStr = classFile.toString().replace(", )", ")").replace("/", ".");
				
				System.out.println(classFileStr);
				
				System.out.println(FlameLauncher.getLoader().getResource("vk.class"));
				
				System.out.println(WrapperClassGen.class.getClassLoader().getResourceAsStream("vk".replace(".", "/") + ".class"));
				System.out.println(WrapperClassGen.class.getClassLoader().getResourceAsStream("vk".replace(".", "/") + ""));
				
				if (!hasNonStatic) {
					classFileStr = classFileStr.replace("extends " + superClass.getSecondaryName() + " ", "");
				}
				
				Enumeration<URL> urls = WrapperClassGen.class.getClassLoader().getResources("vk".replace(".", "/") + "");
				
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					System.out.println(url);
				}
				
				byte[] bytes = CompilerHelper.compile(classFileStr, flameName.replace("/", "."));
				
				//System.out.println(new String(bytes));
				
				return bytes;
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
			//System.out.println(classFile.toString());
//			}
		}
		return null;
	}
	
	public static String parseSourceDescFromBytecodeDesc(String desc) {
		String out = "";
		String desc1 = desc.replace("[", "").trim();
		if (desc1.equals("J")) out = "long";
		else if (desc1.equals("I")) out = "int";
		else if (desc1.equals("S")) out = "short";
		else if (desc1.equals("B")) out = "byte";
		else if (desc1.equals("C")) out = "char";
		else if (desc1.equals("F")) out = "float";
		else if (desc1.equals("D")) out = "double";
		else if (desc1.equals("Z")) out = "boolean";
		else if (desc1.equals("V")) out = "void";
		else if (desc1.startsWith("L")) out = desc1.substring(1, desc1.length() - 1);
		for (int i = 0; i < desc.split("\\[").length - 1; i++) {
			out += "[]";
		}
		return out;
	}
	
	public static String parseParams(String desc) {
		boolean inDesc = false;
		
		StringBuilder out = new StringBuilder();
		StringBuilder type = new StringBuilder("L");
		
		HashMap<String, Integer> counts = new HashMap<>();
		for (int i = 0; i < desc.length(); i++) {
			if (inDesc) {
				type.append(desc.charAt(i));
				
				if (desc.charAt(i) == ';') {
					String val = getFlameFor(parseSourceDescFromBytecodeDesc(type.toString())).replace(".", "/");
					String typeName = val.substring(val.lastIndexOf("/") + 1);
					typeName = typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
					
					if (!counts.containsKey(val)) {
						out.append(val).append(" ").append(typeName).append(", ");
						counts.put(val, 1);
					} else {
						out.append(val).append(" ").append(typeName).append(counts.get(typeName)).append(", ");
						counts.remove(val, counts.get(val) + 1);
					}
					
					type = new StringBuilder("L");
					inDesc = false;
				}
			} else {
				if (desc.charAt(i) == 'L') {
					inDesc = true;
				} else {
					String val = parseSourceDescFromBytecodeDesc(String.valueOf(desc.charAt(i)));
					String typeName = String.valueOf(desc.charAt(i)).toLowerCase();
					
					if (!counts.containsKey(val)) {
						out.append(val).append(" ").append(typeName).append(", ");
						counts.put(val, 1);
					} else {
						out.append(val).append(" ").append(typeName).append(counts.get(typeName)).append(", ");
						counts.remove(val, counts.get(val) + 1);
					}
				}
			}
		}
		
		return out.toString();
	}
	
	private static String getFlameFor(String name) {
		return getFlameWithSlashFor(name).replace("/", ".");
	}
	
	private static String getFlameWithSlashFor(String parseSourceDescFromBytecodeDesc) {
		Class clazz = Flame.getFromObsf(parseSourceDescFromBytecodeDesc.replace(".", "/"));
		if (clazz != null) return clazz.getSecondaryName();
		clazz = Flame.getFromMapped(parseSourceDescFromBytecodeDesc.replace(".", "/"));
		if (clazz != null) return clazz.getSecondaryName();
		clazz = Flame.getFromObsf(parseSourceDescFromBytecodeDesc.replace("/", "."));
		if (clazz != null) return clazz.getSecondaryName();
		clazz = Flame.getFromMapped(parseSourceDescFromBytecodeDesc.replace("/", "."));
		if (clazz != null) return clazz.getSecondaryName();
		return parseSourceDescFromBytecodeDesc;
	}
}
