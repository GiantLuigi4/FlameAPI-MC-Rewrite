package com.tfc.flamemc.API.utils.wrapper;

import com.tfc.flame.API.utils.reflection.Fields;
import com.tfc.flamemc.API.utils.mapping.Flame;
import com.tfc.flamemc.API.utils.mapping.Intermediary;
import com.tfc.flamemc.API.utils.mapping.Mapping;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Field;
import net.minecraft.world.blocks.BlockProperties;

import java.lang.reflect.Constructor;

public class PropertiesAccessor {
	public static BlockProperties getProperties(Object block) {
		System.out.println("Entering Method");
		Class flameMappedClass = Flame.getFromFlame("net/minecraft/world/blocks/Block");
		String otherDesc = ("net/minecraft/world/blocks/BlockProperties");
		Field field = Mapping.getField(flameMappedClass, "properties", otherDesc);
		String otherName = field.getSecondary();
		
		Class inter = Intermediary.getClassFromInter(flameMappedClass.getPrimaryName());
		Field field1 = Mapping.scanForField(inter, otherName, null);
		try {
			java.lang.reflect.Field f = block.getClass().getField(field1.getSecondary());
			Object properties = f.get(block);
			
			BlockProperties properties1 = null;
			
			for (Constructor<?> constructor : BlockProperties.class.getConstructors()) {
				try {
					properties1 = (BlockProperties) constructor.newInstance(block);
				} catch (Throwable ignored) {
				}
			}
			
			BlockProperties finalProperties = properties1;
			Fields.forEach(properties.getClass(), (field2) -> {
				try {
					field2.setAccessible(true);
					field2.set(finalProperties, field2.get(properties));
				} catch (Throwable ignored) {
				}
			});
			System.out.println("Hi: " + finalProperties.wrapped);
			return finalProperties;
		} catch (Throwable ignored) {
		}
		return null;
	}
}
