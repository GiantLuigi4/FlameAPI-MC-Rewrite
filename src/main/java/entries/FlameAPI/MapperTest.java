package entries.FlameAPI;

import net.minecraft.registry.BlockRegistry;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.util.resource.ResourceName;
import net.minecraft.util.vecmath.Matrix4;
import net.minecraft.util.vecmath.Vector3d;
import net.minecraft.world.blocks.AbstractBlock;
import net.minecraft.world.blocks.Block;
import net.minecraft.world.blocks.BlockProperties;
import tfc.flamemc.API.Registry;

public class MapperTest {
	static {
		if (true){
			ResourceName location = new ResourceName("flame_api:test");
			System.out.println(location);
			System.out.println(location.namespace());
			System.out.println(location.path());
			System.out.println(BuiltinRegistries.BLOCKS);
			System.out.println(new Matrix4());
			System.out.println(Matrix4.perspectiveMatrix(90, 1, 0.01f, 1000));
			System.out.println(Matrix4.translationMatrix(5, 1, 3));
			System.out.println(Matrix4.scaleMatrix(5, 1, 3).copy());
			System.out.println(new Vector3d(1, 2, 3));
			System.out.println(new Vector3d(1, 2, 3).x);
			System.out.println(new Vector3d(1, 2, 3).y);
			System.out.println(new Vector3d(1, 2, 3).z);
			System.out.println(Vector3d.ZERO);
			System.out.println(new Vector3d(1, 2, 3).normalize());
			System.out.println(new Vector3d(1, 2, 3).dotProduct(new Vector3d(0, 0, 0)));
			System.out.println(new Vector3d(1, 2, 3).crossProduct(new Vector3d(3, 2, 1)));
			System.out.println(new Vector3d(0, 0, 0).subtract(1, 2, 3));
			System.out.println(new Vector3d(0, 0, 0).add(1, 2, 3));
			System.out.println(new Vector3d(5, 3, 1).invert());
			System.out.println(new Vector3d(5, 3, 1).scale(2));
			System.out.println(new Vector3d(5, 3, 1).multiply(2, 4, 6));
			System.out.println(Block.class);
			System.out.println(BlockProperties.class);
			System.out.println(DefaultedRegistry.class);
			System.out.println(BuiltinRegistries.class);
			System.out.println(BlockRegistry.class);
			System.out.println(AbstractBlock.class);
			System.out.println(Matrix4.class);
			System.out.println(Vector3d.class);
			
			try {
				System.out.println(Registry.register(Registry.Register.BLOCKS, new ResourceName("flameapi:test"), new Block(BlockProperties.from(BlockRegistry.STONE_BLOCK))));
//				Block b = Registry.register(Registry.Register.BLOCKS, new ResourceName("flameapi:test1"), new TestBlock(BlockProperties.from(BlockRegistry.STONE_BLOCK)));
				System.out.println(Registry.register(Registry.Register.BLOCKS, new ResourceName("flameapi:test1"), new TestBlock(BlockProperties.from(BlockRegistry.STONE_BLOCK))).getClass());
//				System.out.println(b);
//				System.out.println(b.getClass());
				System.out.println(BlockProperties.from(BlockRegistry.STONE_BLOCK));
			} catch (Throwable err) {
				err.printStackTrace();
			}
//			System.out.println(((AbstractBlock)BlockRegistry.AIR).properties);
		}
	}
	
	public static void init() {
	}
}
