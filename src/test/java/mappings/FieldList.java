package mappings;

import java.util.HashMap;

public class FieldList {
	HashMap<FieldObject,String> map = new HashMap<>();
	
	boolean done = false;
	
	public FieldList add(String type, String secondary, String primary) {
		map.put(new FieldObject(type,secondary),primary);
		return this;
	}
}
