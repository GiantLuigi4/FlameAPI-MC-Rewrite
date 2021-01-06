package mappings;

public class MethodObject {
	public String unmapped, mapped, desc;
	public boolean isStatic = false;
	
	public MethodObject(String unmapped, String mapped, String desc) {
		this.unmapped = unmapped;
		this.mapped = mapped;
		this.desc = desc;
	}
	
	public MethodObject(String unmapped, String mapped, String desc, boolean isStatic) {
		this.unmapped = unmapped;
		this.mapped = mapped;
		this.desc = desc;
		this.isStatic = isStatic;
	}
}
