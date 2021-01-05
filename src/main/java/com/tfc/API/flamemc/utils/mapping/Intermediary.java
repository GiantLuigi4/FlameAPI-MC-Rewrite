package com.tfc.API.flamemc.utils.mapping;

import com.tfc.API.flamemc.GameInstance;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Holder;

import java.util.HashMap;

public class Intermediary {
	private static final HashMap<String, Holder> holderHashMap = new HashMap<>();
	
	public static void load(String version) {
		holderHashMap.put(version, com.tfc.mappings.types.Intermediary.generate(version));
	}
	
	public static Class getClassObsf(String version, String name) {
		if (!holderHashMap.containsKey(version))
			load(version);
		return holderHashMap.get(version).getFromPrimaryName(name);
	}
	
	public static Class getClassObsf(String name) {
		return getClassObsf(GameInstance.INSTANCE.versionMap, name);
	}
	
	public static Class getClassInter(String version, String name) {
		if (!holderHashMap.containsKey(version))
			load(version);
		return holderHashMap.get(version).getFromSecondaryName(name);
	}
	
	public static Class getClassInter(String name) {
		return Intermediary.getClassInter(GameInstance.INSTANCE.versionMap, name);
	}
}
