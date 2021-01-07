package mappings;

public class MethodObject {
	public String unmapped, mapped, desc;
	public boolean isStatic = false, shouldBeUsed = false;
	
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
	
	public MethodObject(String unmapped, String mapped, String desc, boolean isStatic, boolean shouldBeUsed) {
		this.unmapped = unmapped;
		this.mapped = mapped;
		this.desc = desc;
		this.isStatic = isStatic;
		this.shouldBeUsed = shouldBeUsed;
	}
	
	public String getReturnType() {
		String type = desc.substring(desc.lastIndexOf(")") + 1);
		if (type.startsWith("L"))
			type = type.substring(1, type.length() - 1);
		return type.replace("/", ".");
	}
	
	public String getDescriptorForMethod() {
		return ("(" + MappingsHelper.parseParams(this.desc.substring(1, desc.lastIndexOf(")"))).replace("/", ".") + ")").replace(", )", ")");
	}
}
