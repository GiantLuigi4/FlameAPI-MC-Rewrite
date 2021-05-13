public Block(%super_class% parent) {
	super(com.tfc.flamemc.API.utils.wrapper.PropertiesAccessor.getProperties(parent).wrapped);
	this.wrapped = parent;
}
public Block(%BlockProperties% blockProperties) {
	super(blockProperties.wrapped);
	java.lang.System.out.println("E");
}