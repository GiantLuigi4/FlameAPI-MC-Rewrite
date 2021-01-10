package com.tfc.flamemc.API.utils.mapping;

import com.tfc.flamemc.API.GameInstance;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Holder;

import java.util.HashMap;

public class Intermediary {
	private static final HashMap<String, Holder> holderHashMap = new HashMap<>();
	
	public static void load(String version) {
		holderHashMap.put(version, com.tfc.mappings.types.Intermediary.generate(version));
	}
	
	public static Class getClassFromInter(String version, String name) {
		if (!holderHashMap.containsKey(version))
			load(version);
		return holderHashMap.get(version).getFromPrimaryName(name);
	}
	
	public static Class getClassFromInter(String name) {
		return getClassFromInter(GameInstance.INSTANCE.versionMap, name);
	}
	
	public static Class getClassFromObsf(String version, String name) {
		if (!holderHashMap.containsKey(version))
			load(version);
		return holderHashMap.get(version).getFromSecondaryName(name);
	}
	
	public static Class getClassFromObsf(String name) {
		return Intermediary.getClassFromObsf(GameInstance.INSTANCE.versionMap, name);
	}
}
