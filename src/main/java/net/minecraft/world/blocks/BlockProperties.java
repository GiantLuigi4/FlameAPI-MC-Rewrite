package net.minecraft.world.blocks;

import com.tfc.flamemc.API.utils.wrapper.PropertiesAccessor;

public class BlockProperties {
	public Object wrapped;
	
	public BlockProperties(Block block) {
		wrapped = PropertiesAccessor.getProperties(block);
	}
	
	public BlockProperties(Object wrapped) {
		this.wrapped = wrapped;
	}
}
