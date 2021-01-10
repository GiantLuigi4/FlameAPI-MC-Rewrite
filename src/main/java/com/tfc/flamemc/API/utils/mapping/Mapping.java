package com.tfc.flamemc.API.utils.mapping;

import com.tfc.flamemc.API.GameInstance;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Field;
import com.tfc.mappings.structure.Method;

public class Mapping {
	public static Class get(String name) {
		return get(GameInstance.INSTANCE.versionMap, name);
	}
	
	public static Class getWithoutFlame(String name) {
		return getWithoutFlame(GameInstance.INSTANCE.versionMap, name);
	}
	
	public static Class get(String version, String name) {
		Class clazz = Mojmap.getClassFromMojmap(version, name);
		if (clazz == null) clazz = Flame.getFromObsf(version, name);
		if (clazz == null) clazz = Flame.getFromMapped(version, name);
		if (clazz == null) clazz = Intermediary.getClassFromInter(version, name);
		if (clazz == null) clazz = Mojmap.getClassFromObsf(version, name);
		return clazz;
	}
	
	public static Class getWithoutFlame(String version, String name) {
		Class clazz = Mojmap.getClassFromMojmap(version, name);
		if (clazz == null) clazz = Intermediary.getClassFromInter(version, name);
		if (clazz == null) clazz = Mojmap.getClassFromObsf(version, name);
		return clazz;
	}
	
	public static Method getMethod(Class clazz, String name, String desc) {
		Class source = clazz;
		
		Method method = scanForMethod(clazz, name, desc);
		if (method != null) return method;
		
		clazz = Flame.getFromMapped(clazz.getPrimaryName());
		method = scanForMethod(clazz, name, desc);
		if (method != null) return method;
		
		clazz = Intermediary.getClassFromObsf(clazz.getSecondaryName());
		method = scanForMethod(clazz, name, desc);
		if (method != null) return method;
		
		clazz = Flame.getFromMapped(clazz.getPrimaryName());
		method = scanForMethod(clazz, name, desc);
		if (method != null) return method;
		
		clazz = Mojmap.getClassFromObsf(source.getSecondaryName());
		method = scanForMethod(clazz, name, desc);
		return method;
	}

	public static Field getField(Class clazz, String name, String desc) {
		Class source = clazz;

		Field field = scanForField(clazz, name, desc);
		if (field != null) return field;

		clazz = Flame.getFromMapped(clazz.getPrimaryName());
		field = scanForField(clazz, name, desc);
		if (field != null) return field;

		clazz = Intermediary.getClassFromObsf(clazz.getSecondaryName());
		field = scanForField(clazz, name, desc);
		if (field != null) return field;

		clazz = Flame.getFromMapped(clazz.getPrimaryName());
		field = scanForField(clazz, name, desc);
		if (field != null) return field;

		clazz = Mojmap.getClassFromObsf(source.getSecondaryName());
		field = scanForField(clazz, name, desc);
		return field;
	}
	
	public static Method scanForMethod(Class clazz, String name, String desc) {
		for (Method m : clazz.getMethods())
			if (desc != null) {
				if (m.getPrimary().equals(name) && m.getDesc().startsWith(desc) || m.getSecondary().equals(name) && m.getDesc().startsWith(desc))
					return m;
			} else if (m.getPrimary().equals(name) || m.getSecondary().equals(name))
				return m;
		return null;
	}

	public static Field scanForField(Class clazz, String name, String desc) {
		for (Field f : clazz.getFields())
			if (desc != null) {
				if (f.getPrimary().equals(name) && f.getDesc().startsWith(desc) || f.getSecondary().equals(name) && f.getDesc().startsWith(desc))
					return f;
			} else if (f.getPrimary().equals(name) || f.getSecondary().equals(name))
				return f;
		return null;
	}

//	public static String getUnmappedFor(String name) {
//		if (name.startsWith("net.minecraft.init.")) {
//			String s1 = name.replace("net.minecraft.init.", "");
//			s1 = s1.toLowerCase();
//			s1 = "minecraft:" + s1;
//			return ScanningUtils.toClassName(Main.getRegistries().get(s1));
//		} else {
//			try {
//				return Mojmap.getClassObsf(name).getSecondaryName();
//			} catch (Throwable ignored) {
//			}
//			try {
//				return Intermediary.getClassObsf(name).getSecondaryName();
//			} catch (Throwable ignored) {
//			}
//		}
//		return name;
//	}
//
//	public static String getMappedClassForRegistry(String name) {
//		if (name.startsWith("minecraft:")) {
//			String s1 = name.replace("minecraft:", "");
//			s1 = s1.toUpperCase().replace(s1.substring(1).toUpperCase(), s1.substring(1));
//			return "net.minecraft.init." + s1;
//		}
//		return name;
//	}
}
