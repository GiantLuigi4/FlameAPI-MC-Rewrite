//package entries.FlameAPI;
//
//import net.minecraft.util.collision.voxel.VoxelShape;
//import net.minecraft.util.collision.voxel.VoxelShapeHelper;
//import net.minecraft.util.context.ISelectionContext;
//import net.minecraft.world.World;
//import net.minecraft.world.blocks.Block;
//import net.minecraft.world.blocks.BlockProperties;
//import net.minecraft.world.blocks.BlockState;
//import net.minecraft.world.entities.AbstractEntity;
//import net.minecraft.world.interfaces.IBlockContainer;
//import net.minecraft.world.position.BlockPosition;
//
//public class TestBlock extends Block {
//	public TestBlock(BlockProperties properties) {
//		super(properties);
//	}
//
//	@Override
//	public void entityCollision(BlockState state, World world, BlockPosition pos, AbstractEntity entity) {
//		entity.setMot(0, 1, 1);
//	}
//
//	@Override
//	public boolean canProvidePower() {
//		return true;
//	}
//
//	@Override
//	public float jumpFactor() {
//		return 0;
//	}
//
//	@Override
//	public VoxelShape shape(BlockState state, IBlockContainer reader, BlockPosition position) {
//		return shape();
//	}
//
//	@Override
//	public VoxelShape collisionShape(BlockState state, IBlockContainer reader, BlockPosition position, ISelectionContext context) {
//		return collisionShape();
//	}
//
//	public VoxelShape shape() {
//		return VoxelShapeHelper.createShape(0.1, 0.1, 0.1, 15.9, 15.9, 15.9);
//	}
//
//	public VoxelShape collisionShape() {
//		return VoxelShapeHelper.createShape(0.1, 0.1, 0.1, 15.9, 15.9, 15.9);
//	}
//}
