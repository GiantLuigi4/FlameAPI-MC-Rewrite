package com.tfc.flamemc.API.utils.mapping;

import com.tfc.flamemc.API.GameInstance;
import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsMethod;

public class Mapping {
	public static MappingsClass get(String name) {
		return get(GameInstance.INSTANCE.versionMap, name);
	}
	
	public static MappingsClass getWithoutFlame(String name) {
		return getWithoutFlame(GameInstance.INSTANCE.versionMap, name);
	}
	
	public static MappingsClass get(String version, String name) {
		MappingsClass clazz = Mojmap.getClassFromMojmap(version, name);
		if (clazz == null) clazz = Flame.getFromObsf(version, name);
		if (clazz == null) clazz = Flame.getFromMapped(version, name);
		if (clazz == null) clazz = Intermediary.getClassFromInter(version, name);
		if (clazz == null) clazz = Mojmap.getClassFromObsf(version, name);
		return clazz;
	}
	
	public static MappingsClass getWithoutFlame(String version, String name) {
		MappingsClass clazz = Mojmap.getClassFromMojmap(version, name);
		if (clazz == null) clazz = Intermediary.getClassFromInter(version, name);
		if (clazz == null) clazz = Mojmap.getClassFromObsf(version, name);
		return clazz;
	}
	
	public static MappingsMethod getMethod(MappingsClass clazz, String name, String desc) {
		MappingsClass source = clazz;
		
		MappingsMethod method = scanForMethod(clazz, name, desc);
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

	public static MappingsField getField(MappingsClass clazz, String name, String desc) {
		MappingsClass source = clazz;
		
		MappingsField field = scanForField(clazz, name, desc);
		if (field != null) return field;
		
		clazz = Flame.getFromMapped(clazz.getPrimaryName());
		field = scanForField(clazz, name, desc);
		if (field != null) return field;
		
		clazz = Intermediary.getClassFromObsf(clazz.getSecondaryName());
		field = scanForField(clazz, name, desc);
		if (field != null) return field;
		
		clazz = Flame.getFromMapped(source.getPrimaryName());
		field = scanForField(clazz, name, desc);
		if (field != null) return field;
		
		clazz = Mojmap.getClassFromObsf(source.getSecondaryName());
		field = scanForField(clazz, name, desc);
		return field;
	}
	
	public static MappingsMethod scanForMethod(MappingsClass clazz, String name, String desc) {
		if (clazz == null) return null;
		for (MappingsMethod m : clazz.getMethods())
			if (desc != null) {
				if (
						(
								m.getPrimary().equals(name) &&
										m.getDesc().startsWith(desc) ||
										m.getSecondary().equals(name) &&
												m.getDesc().startsWith(desc)
						)
				)
					return m;
			} else if (m.getPrimary().equals(name) || m.getSecondary().equals(name))
				return m;
		return null;
	}

	public static MappingsField scanForField(MappingsClass clazz, String name, String desc) {
		if (clazz == null) return null;
		for (MappingsField f : clazz.getFields())
			if (desc != null) {
				if ((f.getPrimary().equals(name) && f.getDesc().startsWith(desc) ||
					 f.getSecondary().equals(name) && f.getDesc().startsWith(desc)) ||
					(f.getPrimary().equals(name) && f.getDesc().startsWith("L" + desc) ||
							 f.getSecondary().equals(name) && f.getDesc().startsWith("L" + desc)))
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
