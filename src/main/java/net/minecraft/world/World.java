package net.minecraft.world;

import net.minecraft.world.blocks.BlockState;
import net.minecraft.world.interfaces.IBlockContainer;
import net.minecraft.world.position.BlockPosition;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class World implements IBlockContainer {
	@Override
	public BlockState getBlockState(BlockPosition position) {
		return null;
	}
}
