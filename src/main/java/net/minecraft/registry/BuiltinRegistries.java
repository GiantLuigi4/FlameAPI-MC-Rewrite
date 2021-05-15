package net.minecraft.registry;
public class BuiltinRegistries {
	//Fields
	public static net.minecraft.registry.DefaultedRegistry ENTITIES;
	
	public static net.minecraft.registry.DefaultedRegistry BLOCKS;
	
	public static net.minecraft.registry.DefaultedRegistry ITEMS;
	
	public static BuiltinRegistries TILE_ENTITIES;
	
	//Methods
	public static Object register(BuiltinRegistries register, String name, Object value) {
		return value;
	}
}
