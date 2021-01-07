package com.tfc.API.flamemc.utils.mapping;

import com.tfc.API.flamemc.GameInstance;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.FlameMapHolder;

import static com.tfc.API.flame.utils.IO.URL.readUrl;

public class Flame {
	public static final FlameMapHolder holder;
	
	static {
		try {
			holder = new FlameMapHolder(readUrl("https://raw.githubusercontent.com/GiantLuigi4/FlameAPI-MC-Rewrite/master/mappings/flame_mappings.mappings"));
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
			Class clazz2 = Intermediary.getClassFromInter("1.16.4", clazz.getSecondaryName());
			if (clazz2 != null) {
				return holder.getFromPrimaryName(clazz2.getPrimaryName());
			}
		}
		return null;
	}
	
	public static Class getFromMapped(String version, String mapped) {
		{
			Class clazz = Mojmap.getClassFromMojmap(version, mapped);
			if (clazz != null) return getFromObsf(version, clazz.getSecondaryName());
			else return getFromObsf(version, Intermediary.getClassFromInter(version, mapped).getSecondaryName());
		}
	}
	
	public static Class getFromMapped(String mapped) {
		return getFromMapped(GameInstance.INSTANCE.versionMap, mapped);
	}
}
