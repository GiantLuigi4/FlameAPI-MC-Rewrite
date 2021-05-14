package entries.FlameAPI;

import net.minecraft.registry.BlockRegistry;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.resource.ResourceName;
import net.minecraft.world.blocks.Block;
import net.minecraft.world.blocks.BlockProperties;

public class MapperTest {
	static {
		ResourceName location = new ResourceName("flame_api:test");
		System.out.println(location);
		System.out.println(location.namespace());
		System.out.println(location.path());
		System.out.println(BuiltinRegistries.BLOCKS);
		
		System.out.println(Block.class);
		System.out.println(BlockProperties.class);
		System.out.println(DefaultedRegistry.class);
		System.out.println(BuiltinRegistries.class);
		System.out.println(BlockRegistry.class);
	}
	
	public static void init() {
	}
}
