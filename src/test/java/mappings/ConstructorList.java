package mappings;

import java.util.ArrayList;

public class ConstructorList {
	public ArrayList<ConstructorObject> constructorObjects = new ArrayList<>();
	
	public ConstructorList add(String desc) {
		constructorObjects.add(new ConstructorObject(desc));
		return this;
	}
}
