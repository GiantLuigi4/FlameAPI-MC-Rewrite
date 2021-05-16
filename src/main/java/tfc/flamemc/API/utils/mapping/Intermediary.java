package tfc.flamemc.API.utils.mapping;

import tfc.flamemc.API.GameInstance;
import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsHolder;

import java.util.HashMap;

public class Intermediary {
	private static final HashMap<String, MappingsHolder> holderHashMap = new HashMap<>();
	
	public static void load(String version) {
		holderHashMap.put(version, tfc.mappings.types.Intermediary.generate(version));
	}
	
	public static MappingsClass getClassFromInter(String version, String name) {
		if (!holderHashMap.containsKey(version))
			load(version);
		return holderHashMap.get(version).getFromPrimaryName(name);
	}
	
	public static MappingsClass getClassFromInter(String name) {
		return getClassFromInter(GameInstance.INSTANCE.versionMap, name);
	}
	
	public static MappingsClass getClassFromObsf(String version, String name) {
		if (!holderHashMap.containsKey(version))
			load(version);
		return holderHashMap.get(version).getFromSecondaryName(name);
	}
	
	public static MappingsClass getClassFromObsf(String name) {
		return Intermediary.getClassFromObsf(GameInstance.INSTANCE.versionMap, name);
	}
}
