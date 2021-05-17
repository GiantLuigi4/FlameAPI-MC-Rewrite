package entries.FlameAPI;

import net.minecraft.vecmath.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.blocks.Block;
import net.minecraft.world.blocks.BlockProperties;
import net.minecraft.world.blocks.BlockState;
import net.minecraft.world.entities.AbstractEntity;
import net.minecraft.world.position.BlockPosition;

public class TestBlock extends Block {
	public TestBlock(BlockProperties properties) {
		super(properties);
	}
	
	@Override
	public void entityCollision(BlockState state, World world, BlockPosition pos, AbstractEntity entity) {
		entity.setMot(new Vector3d(0, 1, 1));
	}
}
