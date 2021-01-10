//package net.minecraft.world.blocks;
//
//import com.tfc.flamemc.API.utils.mapping.Flame;
//import com.tfc.flamemc.API.utils.mapping.Intermediary;
//import com.tfc.flamemc.API.utils.mapping.Mapping;
//import com.tfc.mappings.structure.Class;
//import com.tfc.mappings.structure.Field;
//
//public class PropertiesAccessor {
//	public static Object getProperties(Object block) {
//		Class flameMappedClass = Flame.getFromFlame("net/minecraft/world/blocks/Block");
//		String otherDesc = ("net/minecraft/world/blocks/BlockProperties");
//		String otherName = ""
//		Field field = Mapping.getField(flameMappedClass, otherName, otherDesc);
//
//		Class inter = Intermediary.getClassFromInter(flameMappedClass.getPrimaryName());
//		Field field1 = Mapping.scanForField(inter, otherName, null);
//		block.getClass().getField(
//				field1.
//		)
//	}
//}
