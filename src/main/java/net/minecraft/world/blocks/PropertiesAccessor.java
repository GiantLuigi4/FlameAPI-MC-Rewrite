package net.minecraft.world.blocks;

import com.tfc.flame.API.utils.reflection.Fields;
import com.tfc.flamemc.API.utils.mapping.Flame;
import com.tfc.flamemc.API.utils.mapping.Intermediary;
import com.tfc.flamemc.API.utils.mapping.Mapping;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Field;

import java.lang.reflect.Constructor;

public class PropertiesAccessor {
	public static BlockPropertiesE getProperties(Object block) {
		Class flameMappedClass = Flame.getFromFlame("net/minecraft/world/blocks/Block");
		String otherDesc = ("net/minecraft/world/blocks/BlockProperties");
		Field field = Mapping.getField(flameMappedClass, "properties", otherDesc);
		String otherName = field.getSecondary();
		
		Class inter = Intermediary.getClassFromInter(flameMappedClass.getPrimaryName());
		Field field1 = Mapping.scanForField(inter, otherName, null);
		try {
			java.lang.reflect.Field f = block.getClass().getField(field1.getSecondary());
			Object properties = f.get(block);
			
			BlockPropertiesE properties1 = null;
			
			for (Constructor<?> constructor : BlockPropertiesE.class.getConstructors()) {
				try {
					properties1 = (BlockPropertiesE) constructor.newInstance(block);
				} catch (Throwable ignored) {
				}
			}
			
			BlockPropertiesE finalProperties = properties1;
			Fields.forEach(properties.getClass(), (field2) -> {
				try {
					field2.setAccessible(true);
					field2.set(finalProperties, field2.get(properties));
				} catch (Throwable ignored) {
				}
			});
			return finalProperties;
		} catch (Throwable ignored) {
		}
		return null;
	}
}
