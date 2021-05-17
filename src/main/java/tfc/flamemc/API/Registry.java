package tfc.flamemc.API;

import net.minecraft.registry.BlockRegistry;
import net.minecraft.util.resource.ResourceName;
import net.minecraft.world.blocks.Block;
import tfc.flameasm.remapper.MappingApplicator;
import tfc.flameasm.remapper.NoRemap;

import java.lang.reflect.Method;

//TODO: figure out why the remapper doesn't get applied to this class in FlameAPI dev envro
public class Registry {
	@NoRemap
	public enum Register {
		BLOCKS
	}
	
	private static final Method doRegister;
	static {
		System.out.println("hello");
		//TODO: access modifiers instead of reflection
		Method m = null;
		try {
			m = BlockRegistry.class.getDeclaredMethod(
					MappingApplicator.methodMapper.apply("net/minecraft/registry/BlockRegistry;register(Ljava/lang/String;Lnet/minecraft/world/blocks/Block;)Lnet/minecraft/world/blocks/Block;", MappingApplicator.getSteps("FLAME", MappingApplicator.targetMappings)),
					String.class, Block.class
			);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		m.setAccessible(true);
		doRegister = m;
	}
	
	public static <T> T register(Register register, ResourceName name, T value) {
		switch (register) {
			case BLOCKS:
				try {
					return (T)(doRegister.invoke(null, name.toString(), value));
				} catch (Throwable err) {
					err.printStackTrace();
					return null;
				}
			default:
				return null;
		}
	}
}
