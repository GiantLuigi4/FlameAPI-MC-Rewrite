package net.minecraft.world.interfaces;

import net.minecraft.world.blocks.BlockState;
import net.minecraft.world.position.BlockPosition;

public interface IBlockContainer {
	BlockState getBlockState(BlockPosition position);
	default int getLight(BlockPosition position){return 0;}
	default int getMaxLight(){return 15;}
	default int getMaxY(){return 256;}
}
