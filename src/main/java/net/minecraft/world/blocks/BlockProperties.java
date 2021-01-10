package net.minecraft.world.blocks;

public class BlockProperties {
	public Object wrapped;
	
	public BlockProperties(Block block) {
		wrapped = PropertiesAccessor.getProperties(block);
	}
	
	public BlockProperties(Object wrapped) {
		this.wrapped = wrapped;
	}
}
