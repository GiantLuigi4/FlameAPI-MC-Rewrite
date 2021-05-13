package com.tfc.flamemc.API.utils.wrapper;

import com.tfc.bytecode.utils.Formatter;
import com.tfc.flame.API.utils.IO.ClassLoaderIO;
import com.tfc.flame.API.utils.IO.FileUtils;
import com.tfc.flame.API.utils.data.properties.Properties;
import com.tfc.flamemc.API.utils.mapping.Flame;
import com.tfc.flamemc.API.utils.mapping.Intermediary;
import com.tfc.flamemc.API.utils.mapping.Mapping;
import com.tfc.flamemc.API.utils.mapping.Mojmap;
import com.tfc.flamemc.FlameLauncher;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Field;
import com.tfc.mappings.structure.Method;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WrapperClassGen {
	public static final Properties classes;
	public static final Properties access;
	
	static {
		FlameLauncher.getLoader().getAsmAppliers().put("flame_api:generate_wrapper", WrapperClassGen::generateWrapper);
		try {
			classes = new Properties(ClassLoaderIO.readAsString("wrapper_classes.properties"));
			access = new Properties(ClassLoaderIO.readAsString("wrappers/access.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void init() {
		//just class loads the class
	}
	
	private static byte[] generateWrapper(String name, byte[] source) {
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
			String className = flameName.substring(flameName.lastIndexOf("/") + 1);
			classFile.append("public class ").append(flameName, flameName.lastIndexOf("/") + 1, flameName.length()).append(" extends ").append(superClass.getSecondaryName()).append(" {");

			boolean hasNonStatic = false;
			
			try {
				Properties clazz = new Properties(ClassLoaderIO.readAsString("wrappers/" + file + ".properties"));
				
				for (String entry : clazz.getEntries()) {
					String nameAndType = clazz.getValue(entry);
					
					if (nameAndType.startsWith("static.field") || nameAndType.startsWith("field")) {
						try {
							boolean isStatic = nameAndType.startsWith("static");
							
							hasNonStatic = hasNonStatic || !isStatic;
							
							String otherName = entry.substring(0, entry.indexOf("|"));
							String otherDesc = entry.substring(entry.indexOf("|") + 1);

							Field field = Mapping.getField(flameMappedClass, otherName, otherDesc);
							
							Class inter = Intermediary.getClassFromInter(flameMappedClass.getPrimaryName());
							Field field1 = Mapping.scanForField(inter, otherName, null);

							String getter = field.getPrimary().substring(0, 1).toUpperCase() + field.getPrimary().substring(1);
							classFile.append("\n\tpublic ").append(isStatic ? "static " : "").append(otherDesc.replace("/", ".")).append(" get").append(getter).append("(){\n\t\t").append("return new ").append(otherDesc.replace("/", ".")).append("(").append(isStatic ? (superClass.getSecondaryName() + ".") : "this.").append(field1.getSecondary()).append(");\n\t").append("}\n");
						} catch (Throwable err) {
							err.printStackTrace();
						}
					}
					
					if (nameAndType.startsWith("static.method") || nameAndType.startsWith("method")) {
						try {
							boolean isStatic = nameAndType.startsWith("static");
							
							hasNonStatic = hasNonStatic || !isStatic;

							String otherName = entry.substring(0, entry.indexOf("("));
							String otherDesc = entry.substring(entry.indexOf("("));

							Method method = Mapping.getMethod(flameMappedClass, otherName, otherDesc);

							Method otherMapped;
							if (method.getSecondary().startsWith("method_")) {
								otherMapped = Mapping.getMethod(Intermediary.getClassFromInter(flameMappedClass.getPrimaryName()), method.getSecondary(), otherDesc);
							} else {
								otherMapped = Mapping.getMethod(Mojmap.getClassFromObsf(Intermediary.getClassFromInter(flameMappedClass.getPrimaryName()).getSecondaryName()), method.getSecondary(), otherDesc);
							}
							
							String typeName = otherDesc.substring(otherDesc.indexOf(")"));
							if (typeName.contains(";")) {
								typeName = typeName.substring(2, typeName.length() - 1);
							} else {
								typeName = parseSourceDescFromBytecodeDesc(otherDesc.substring(otherDesc.indexOf(")") + 1));
							}
							
							String type = getFlameFor(typeName);
							String paramsStr = otherDesc.substring(1, otherDesc.indexOf(")"));
							String params = parseParams(paramsStr);
							
							boolean hasReturn = !type.equals("void");
							
							if (getAccess(className, method.getPrimary()).equals("public")) {
								classFile.append("\n\tpublic ").append(isStatic ? "static " : "").append(type).append(" ").append(method.getPrimary()).append("(").append(params).append(") {\n\t\t").append(hasReturn ? ("return (" + type + ")") : "").append(isStatic ? (superClass.getSecondaryName() + ".") : "this.").append(otherMapped.getSecondary()).append("(").append(parseParams(paramsStr, false)).append(");\n\t}\n");
							} else {
								String reflection =
										"try {\n\t\t\t" +
												"java.lang.reflect.Method m = " + superClass.getSecondaryName() + ".class" + ".getDeclaredMethod(\"" + otherMapped.getSecondary() + "\"," + parseParams(paramsStr, false, true) + ");\n\t\t\t" +
												"m.setAccessible(true);\n\t\t\t" +
												(hasReturn ? ("return (" + type + ")") : "") + "m.invoke(" + (isStatic ? "null" : "this") + "," + parseParams(paramsStr, false) + ");\n\t\t" +
												"} catch (Throwable ignored) {}\n\t\t" +
												(hasReturn ? "return null;" : "");
								classFile.append("\n\tpublic ").append(isStatic ? "static " : "").append(type).append(" ").append(method.getPrimary()).append("(").append(params).append(") {\n\t\t").append(reflection).append("\n\t}\n");
							}
						} catch (Throwable err) {
							err.printStackTrace();
						}
					}
				}

//				try {
//					ClassReader reader = new ClassReader(source);
//					ClassNode node = new ClassNode();
//					ClassWriter writer =
//							new ClassWriter(
//									reader,
//									ClassWriter.COMPUTE_FRAMES
//							);
//					node.accept(writer);
//					for (MethodNode method : node.methods) {
//						if (method.name.equals("<init>")) {
//							classFile.append("public " + className + parseParams(method.desc.substring(1, method.desc.length() - 2)));
//							classFile.append(" {\n\t\tsuper(" + parseParams(method.desc, false) + "\n\t}\n");
//						}
//					}
//				} catch (Throwable ignored) {
//					ignored.printStackTrace();
//				}
				
				if (FlameLauncher.getLoader().getResource("wrappers/" + className + "Constructor.java") != null) {
					String string = ClassLoaderIO.readAsString("wrappers/" + className + "Constructor.java")
							                .replace("%BlockProperties%", "BlockProperties")
							                .replace("%super_class%", superClass.getSecondaryName());
					classFile.append(string);
					classFile.append("\tpublic ").append(superClass.getSecondaryName()).append(" wrapped = null;\n");
				}
				classFile.append("}");

				String classFileStr = classFile.toString().replace(", )", ")").replace("/", ".");
				
				byte[] bytes;

				try {
					bytes = CompilerHelper.compile(Formatter.formatForCompile(classFileStr), flameName.replace("/", "."));
					FileUtils.write(new File("flame_asm/" + flameName.replace(".", "/") + ".java"), Formatter.formatForCompile(classFileStr));
				} catch (Throwable ex) {
					bytes = CompilerHelper.compile(Formatter.formatForCompile(classFileStr).replace("extends " + superClass.getSecondaryName() + " ", ""), flameName.replace("/", "."));
					FileUtils.write(new File("flame_asm/" + flameName.replace(".", "/") + "_thrown.java"), Formatter.formatForCompile(classFileStr).replace("extends " + superClass.getSecondaryName() + " ", ""));
				}

				FileUtils.write(new File("flame_asm/" + flameName.replace(".", "/") + ".class"), bytes);
				FileUtils.write(new File("flame_asm/" + flameName.replace(".", "/") + "_source.class"), source);

				return bytes;
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
		return source;
	}
	
	private static String parseParams(String desc, boolean outputClasses, boolean outputWithClass) {
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
					
					if (!outputWithClass) {
						if (!counts.containsKey(val)) {
							out.append(outputClasses ? val : "").append(" ").append(typeName).append(", ");
							counts.put(val, 1);
						} else {
							out.append(outputClasses ? val : "").append(" ").append(typeName).append(counts.get(typeName)).append(", ");
							counts.remove(val, counts.get(val) + 1);
						}
					} else {
						Class clazz = Flame.getFromFlame(val.replace(".", "/"));
						if (clazz != null) {
							Class other = Mapping.getWithoutFlame(clazz.getPrimaryName());
							val = other.getSecondaryName();
						}
						
						out.append(val).append(".class").append(", ");
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
					
					if (!outputWithClass) {
						if (!counts.containsKey(val)) {
							out.append(outputClasses ? val : "").append(" ").append(typeName).append(", ");
							counts.put(val, 1);
						} else {
							out.append(outputClasses ? val : "").append(" ").append(typeName).append(counts.get(typeName)).append(", ");
							counts.remove(val, counts.get(val) + 1);
						}
					} else {
						out.append(val).append(".class").append(", ");
					}
				}
			}
		}
		
		return out.toString();
	}
	
	public static String getAccess(String clazz, String method) {
		return access.getEntries().contains(clazz + "$" + method) ? access.getValue(clazz + "$" + method) : "public";
	}
	
	public static String parseSourceDescFromBytecodeDesc(String desc) {
		StringBuilder out = new StringBuilder();
		String desc1 = desc.replace("[", "").trim();
		if (desc1.equals("J")) out = new StringBuilder("long");
		else if (desc1.equals("I")) out = new StringBuilder("int");
		else if (desc1.equals("S")) out = new StringBuilder("short");
		else if (desc1.equals("B")) out = new StringBuilder("byte");
		else if (desc1.equals("C")) out = new StringBuilder("char");
		else if (desc1.equals("F")) out = new StringBuilder("float");
		else if (desc1.equals("D")) out = new StringBuilder("double");
		else if (desc1.equals("Z")) out = new StringBuilder("boolean");
		else if (desc1.equals("V")) out = new StringBuilder("void");
		else if (desc1.startsWith("L")) out = new StringBuilder(desc1.substring(1, desc1.length() - 1));
		for (int i = 0; i < desc.split("\\[").length - 1; i++) {
			out.append("[]");
		}
		return out.toString();
	}
	
	public static String parseParams(String desc) {
		return parseParams(desc, true);
	}

//	public static java.lang.Class[] parseParamsClassList(String desc) throws ClassNotFoundException {
//		ArrayList<java.lang.Class> classes = new ArrayList<>();
//		boolean inDesc = false;
//
//		StringBuilder type = new StringBuilder("L");
//
//		for (int i = 0; i < desc.length(); i++) {
//			if (inDesc) {
//				type.append(desc.charAt(i));
//
//				if (desc.charAt(i) == ';') {
//					String val = getFlameFor(parseSourceDescFromBytecodeDesc(type.toString())).replace(".", "/");
//					String typeName = val.substring(val.lastIndexOf("/") + 1);
//					typeName = typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
//
//					if (!isNative(typeName)) {
//						Class clazz = Mapping.getWithoutFlame(Mapping.get(typeName).getPrimaryName());
//						classes.add(java.lang.Class.forName(clazz.getSecondaryName()));
//					}
//				}
//			} else {
//				if (desc.charAt(i) == 'L') {
//					inDesc = true;
//				} else {
//					String typeName = parseSourceDescFromBytecodeDesc(String.valueOf(desc.charAt(i)).toLowerCase());
//
//					if (typeName.equals("int")) classes.add(int.class);
//					else if (typeName.equals("long")) classes.add(long.class);
//					else if (typeName.equals("double")) classes.add(double.class);
//					else if (typeName.equals("byte")) classes.add(byte.class);
//					else if (typeName.equals("boolean")) classes.add(boolean.class);
//					else if (typeName.equals("short")) classes.add(short.class);
//					else if (typeName.equals("float")) classes.add(float.class);
//					else if (typeName.equals("char")) classes.add(char.class);
//					else if (typeName.equals("void")) classes.add(void.class);
//				}
//			}
//		}
//
//		return classes.toArray(new java.lang.Class[0]);
//	}
	
	public static boolean isNative(String type) {
		return
				type.equals("int") ||
						type.equals("byte") ||
						type.equals("char") ||
						type.equals("long") ||
						type.equals("short") ||
						type.equals("float") ||
						type.equals("double") ||
						type.equals("boolean");
	}
	
	public static String parseParams(String desc, boolean outputClasses) {
		return parseParams(desc, outputClasses, false);
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
