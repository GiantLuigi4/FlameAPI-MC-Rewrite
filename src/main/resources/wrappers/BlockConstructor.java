public Block(%super_class% parent) {
	super(net.minecraft.world.blocks.PropertiesAccessor.getProperties(parent));
	this.wrapped = parent;
}