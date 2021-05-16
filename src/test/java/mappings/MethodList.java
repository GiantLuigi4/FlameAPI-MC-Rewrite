package mappings;

import tfc.flamemc.API.utils.mapping.Intermediary;
import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsMethod;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodList {
	public final ArrayList<MethodObject> methodObjects = new ArrayList<>();
	
	public static void add(HashMap<String, MethodList> methodListHashMap, String clazzFlame, MappingsClass clazzMojmap, String method, String mapped, String desc) {
		MappingsClass intermediary = Intermediary.getClassFromObsf("1.16.4", clazzMojmap.getSecondaryName());
		MappingsMethod methodMojmap = scanFor(clazzMojmap, method, desc);
		MappingsMethod methodInter;
		
		if (desc.contains("net/minecraft")) methodInter = methodMojmap;
		else methodInter = scanFor(intermediary, methodMojmap.getSecondary(), desc);
		
		MethodList interList = methodListHashMap.getOrDefault(clazzFlame, new MethodList());
		interList.add(new MethodObject(methodMojmap.getPrimary(), mapped, methodMojmap.getDesc(), false, methodMojmap.equals(methodInter)));
		interList.add(new MethodObject(methodInter.getPrimary(), mapped, methodInter.getDesc(), false, !methodMojmap.equals(methodInter)));
		if (!methodListHashMap.containsKey(clazzFlame)) methodListHashMap.put(clazzFlame, interList);
	}
	
	public static void add(HashMap<String, MethodList> methodListHashMap, String clazzFlame, MappingsClass clazzMojmap, String method, String mapped, String desc, boolean isStatic) {
		MappingsClass intermediary = Intermediary.getClassFromObsf("1.16.4", clazzMojmap.getSecondaryName());
		MappingsMethod methodMojmap = scanFor(clazzMojmap, method, desc);
		MappingsMethod methodInter;
		
		if (desc.contains("net/minecraft")) methodInter = methodMojmap;
		else methodInter = scanFor(intermediary, methodMojmap.getSecondary(), desc);
		
		MethodList interList = methodListHashMap.getOrDefault(clazzFlame, new MethodList());
		interList.add(new MethodObject(methodMojmap.getPrimary(), mapped, methodMojmap.getDesc(), isStatic, methodMojmap.equals(methodInter)));
		interList.add(new MethodObject(methodInter.getPrimary(), mapped, methodInter.getDesc(), isStatic, !methodMojmap.equals(methodInter)));
		if (!methodListHashMap.containsKey(clazzFlame)) methodListHashMap.put(clazzFlame, interList);
	}
	
	public void add(MethodObject method) {
		methodObjects.add(method);
	}
	
	public static MappingsMethod scanFor(MappingsClass clazz, String name, String desc) {
		for (MappingsMethod m : clazz.getMethods())
			if (desc != null) {
				if (m.getPrimary().equals(name) && m.getDesc().startsWith(desc) || m.getSecondary().equals(name) && m.getDesc().startsWith(desc))
					return m;
			} else if (m.getPrimary().equals(name) || m.getSecondary().equals(name))
				return m;
		return null;
	}
}
