public Block(%super_class% parent) {
	super(net.minecraft.world.blocks.PropertiesAccessor.getProperties(parent).wrapped);
	this.wrapped = parent;
}
public Block(%BlockProperties% properties) {
	super(properties.wrapped);
}