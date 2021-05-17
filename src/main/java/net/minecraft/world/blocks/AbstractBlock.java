package net.minecraft.world.blocks;

import net.minecraft.world.World;
import net.minecraft.world.entities.AbstractEntity;
import net.minecraft.world.position.BlockPosition;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class AbstractBlock {
	protected final net.minecraft.world.blocks.BlockProperties properties = null;
	
	public void entityCollision(BlockState state, World world, BlockPosition pos, AbstractEntity entity) {
	}
	
	public boolean canProvidePower() {
		return false;
	}
	
	public boolean hasTransparency() {
		return false;
	}
}
