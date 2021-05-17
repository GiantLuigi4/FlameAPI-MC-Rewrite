package net.minecraft.world.blocks;

import net.minecraft.util.collision.voxel.VoxelShape;
import net.minecraft.util.context.ISelectionContext;
import net.minecraft.world.World;
import net.minecraft.world.entities.AbstractEntity;
import net.minecraft.world.interfaces.IBlockContainer;
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
	
	public VoxelShape shape(BlockState state, IBlockContainer reader, BlockPosition position) {
		return null;
	}
	
	public VoxelShape collisionShape(BlockState state, IBlockContainer reader, BlockPosition position, ISelectionContext context) {
		return null;
	}
}