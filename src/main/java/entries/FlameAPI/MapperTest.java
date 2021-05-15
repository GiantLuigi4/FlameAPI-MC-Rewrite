package entries.FlameAPI;

import com.tfc.flamemc.API.utils.properties.BlockPropertiesHelper;
import net.minecraft.registry.BlockRegistry;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.resource.ResourceName;
import net.minecraft.vecmath.Matrix4x4;
import net.minecraft.world.blocks.AbstractBlock;
import net.minecraft.world.blocks.Block;
import net.minecraft.world.blocks.BlockProperties;
import tfc.flameasm.remapper.MappingApplicator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MapperTest {
	static {
		ResourceName location = new ResourceName("flame_api:test");
		System.out.println(location);
		System.out.println(location.namespace());
		System.out.println(location.path());
		System.out.println(BuiltinRegistries.BLOCKS);
		System.out.println(new Matrix4x4());
		System.out.println(Matrix4x4.perspectiveMatrix(90, 1, 0.01f, 1000));
		System.out.println(Matrix4x4.translationMatrix(5, 1 ,3));
		System.out.println(Matrix4x4.scaleMatrix(5, 1 ,3).copy());
		
		System.out.println(Block.class);
		System.out.println(BlockProperties.class);
		System.out.println(DefaultedRegistry.class);
		System.out.println(BuiltinRegistries.class);
		System.out.println(BlockRegistry.class);
		System.out.println(AbstractBlock.class);
		System.out.println(Matrix4x4.class);
		
		try {
			System.out.println(BlockPropertiesHelper.getProperties(BlockRegistry.STONE_BLOCK));
			Method m = BlockRegistry.class.getDeclaredMethod(
					MappingApplicator.methodMapper.apply("net/minecraft/registry/BlockRegistry", "register"),
					String.class, Block.class
			);
			m.setAccessible(true);
			m.invoke(null, location.toString(), new Block(BlockPropertiesHelper.getProperties(BlockRegistry.STONE_BLOCK)));
		} catch (Throwable err) {
			err.printStackTrace();
		}
//		System.out.println(((AbstractBlock)BlockRegistry.AIR).properties);
	}
	
	public static void init() {
	}
}
