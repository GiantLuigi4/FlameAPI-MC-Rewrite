package mappings;

public class FieldObject {
	public String type, name;
	public boolean isStatic;
	
	public FieldObject(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public FieldObject(String type, String name, boolean isStatic) {
		this.type = type;
		this.name = name;
		this.isStatic = isStatic;
	}
}
