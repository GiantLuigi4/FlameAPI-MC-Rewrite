package mappings;

import com.tfc.API.flamemc.utils.mapping.Intermediary;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Method;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodList {
	public final ArrayList<MethodObject> methodObjects = new ArrayList<>();
	
	public static void add(HashMap<String, MethodList> methodListHashMap, String clazzFlame, Class clazzMojmap, String method, String mapped, String desc) {
		Class intermediary = Intermediary.getClassFromObsf("1.16.4", clazzMojmap.getSecondaryName());
		Method methodMojmap = scanFor(clazzMojmap, method, desc);
		Method methodInter = intermediary.getMethodSecondary(methodMojmap.getSecondary());
		
		MethodList interList = methodListHashMap.getOrDefault(clazzFlame, new MethodList());
		interList.add(new MethodObject(methodMojmap.getPrimary(), mapped, methodMojmap.getDesc()));
		interList.add(new MethodObject(methodInter.getPrimary(), mapped, methodInter.getDesc()));
		if (!methodListHashMap.containsKey(clazzFlame)) methodListHashMap.put(clazzFlame, interList);
	}
	
	public static void add(HashMap<String, MethodList> methodListHashMap, String clazzFlame, Class clazzMojmap, String method, String mapped, String desc, boolean isStatic) {
		Class intermediary = Intermediary.getClassFromObsf("1.16.4", clazzMojmap.getSecondaryName());
		Method methodMojmap = scanFor(clazzMojmap, method, desc);
		Method methodInter = intermediary.getMethodSecondary(methodMojmap.getSecondary());
		
		MethodList interList = methodListHashMap.getOrDefault(clazzFlame, new MethodList());
		interList.add(new MethodObject(methodMojmap.getPrimary(), mapped, methodMojmap.getDesc(), isStatic));
		interList.add(new MethodObject(methodInter.getPrimary(), mapped, methodInter.getDesc(), isStatic));
		if (!methodListHashMap.containsKey(clazzFlame)) methodListHashMap.put(clazzFlame, interList);
	}
	
	public void add(MethodObject method) {
		methodObjects.add(method);
	}
	
	public static Method scanFor(Class clazz, String name, String desc) {
		for (Method m : clazz.getMethods())
			if (desc != null) {
				if (m.getPrimary().equals(name) && m.getDesc().equals(desc) || m.getSecondary().equals(name) && m.getDesc().equals(desc))
					return m;
			} else if (m.getPrimary().equals(name) || m.getSecondary().equals(name))
				return m;
		return null;
	}
}
