package tfc.flamemc.API;

import net.minecraft.registry.BlockRegistry;
import net.minecraft.resource.ResourceName;
import net.minecraft.world.blocks.Block;
import tfc.flameasm.remapper.MappingApplicator;

import java.lang.reflect.Method;

public class Registry {
	public enum Register {
		BLOCKS
	}
	
	private static final Method doRegister;
	static {
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
