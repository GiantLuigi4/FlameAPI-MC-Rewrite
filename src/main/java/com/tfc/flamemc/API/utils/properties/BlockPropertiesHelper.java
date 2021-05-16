package com.tfc.flamemc.API.utils.properties;

import net.minecraft.world.blocks.AbstractBlock;
import net.minecraft.world.blocks.Block;
import net.minecraft.world.blocks.BlockProperties;
import tfc.flameasm.remapper.MappingApplicator;

import java.lang.reflect.Field;

public class BlockPropertiesHelper {
	
//	private static final Field f;
	static {
		try {
//			f = AbstractBlock.class.getDeclaredField(MappingApplicator.fieldMapper.apply("net/minecraft/world/blocks/AbstractBlock", "properties"));
//			f.setAccessible(true);
		} catch (Throwable err) {
			throw new RuntimeException(err);
		}
	}
	
	public static BlockProperties getProperties(Block block) {
		try {
//			return (BlockProperties) f.get(block); // TODO: make this be a copy of the properties
		} catch (Throwable ignored) {
		}
		return null;
	}
}
