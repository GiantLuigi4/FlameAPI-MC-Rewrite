package com.tfc.flamemc.API.utils.mapping;

import com.tfc.flame.API.utils.IO.URLUtils;
import com.tfc.flamemc.API.GameInstance;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.FlameMapHolder;

public class Flame {
	public static final FlameMapHolder holder;
	
	static {
		try {
			holder = new FlameMapHolder(URLUtils.readUrl("https://raw.githubusercontent.com/GiantLuigi4/FlameAPI-MC-Rewrite/master/mappings/flame_mappings.mappings"));
		} catch (Throwable err) {
			throw new RuntimeException(err);
		}
	}
	
	public static Class getFromObsf(String obsf) {
		return getFromObsf(GameInstance.INSTANCE.versionMap, obsf);
	}
	
	public static Class getFromObsf(String version, String obsf) {
		Class clazz = Intermediary.getClassFromObsf(version, obsf);
		if (clazz != null) {
			Class clazz2 = Intermediary.getClassFromInter("1.16.4", clazz.getPrimaryName());
			if (clazz2 != null) {
				Class from = holder.getFromPrimaryName(clazz2.getPrimaryName());
				if (from != null) return from;
			}
		}
		clazz = Mojmap.getClassFromObsf(version, obsf);
		if (clazz != null) {
			Class clazz2 = Mojmap.getClassFromMojmap("1.16.4", clazz.getPrimaryName());
			if (clazz2 != null) {
				Class from = holder.getFromPrimaryName(clazz2.getPrimaryName());
				if (from != null) return from;
			}
		}
		return null;
	}
	
	public static Class getFromMapped(String version, String mapped) {
		{
			Class clazz = Mapping.getWithoutFlame(version, mapped.replace("/", "."));
			if (clazz != null) {
				Class res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
			clazz = Mapping.getWithoutFlame(version, mapped.replace(".", "/"));
			if (clazz != null) {
				Class res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
			
			clazz = Mojmap.getClassFromMojmap(version, mapped);
			if (clazz != null) {
				Class res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
			clazz = Intermediary.getClassFromInter(version, mapped);
			if (clazz != null) {
				Class res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
		}
		return null;
	}
	
	public static Class getFromMapped(String mapped) {
		return getFromMapped(GameInstance.INSTANCE.versionMap, mapped);
	}
	
	public static Class getFromFlame(String name) {
		Class clazz = holder.getFromSecondaryName(name.replace(".", "/"));
		if (clazz != null) return clazz;
		return holder.getFromSecondaryName(name.replace("/", "."));
	}
}
