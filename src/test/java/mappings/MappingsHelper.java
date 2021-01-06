package mappings;

import com.tfc.API.flamemc.utils.mapping.Intermediary;
import com.tfc.API.flamemc.utils.mapping.Mojmap;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Holder;
import com.tfc.mappings.structure.MojmapHolder;
import com.tfc.mappings.types.Mojang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class MappingsHelper {
	private static final HashMap<String,String> classMap = new HashMap<>();
	private static final HashMap<String,FieldList> fieldMap = new HashMap<>();
	private static final HashMap<String,MethodList> methodMap = new HashMap<>();
	
	public static final MojmapHolder holder = Mojang.generate("1.16.4");
	public static final Holder iholder = com.tfc.mappings.types.Intermediary.generate("1.16.4");
	
	static {
		classMap.put("net.minecraft.core.Registry","net.minecraft.registry.MainRegistry");
		classMap.put("net/minecraft/class_2378","net.minecraft.registry.MainRegistry");
		
		classMap.put("net.minecraft.core.DefaultedRegistry","net.minecraft.registry.DefaultedRegistry");
		classMap.put("net/minecraft/class_2348","net.minecraft.registry.DefaultedRegistry");
		
		classMap.put("net.minecraft.world.level.block.Blocks","net.minecraft.registry.BlockRegistry");
		classMap.put("net/minecraft/class_2246","net.minecraft.registry.BlockRegistry");
		
		fieldMap.put("net.minecraft.registry.MainRegistry",new FieldList()
				.add("net.minecraft.registry.DefaultedRegistry","field_11146","blocks", true)
				.add("net.minecraft.registry.DefaultedRegistry","BLOCK","blocks", true)
				.add("net.minecraft.registry.DefaultedRegistry","field_11142","items", true)
				.add("net.minecraft.registry.DefaultedRegistry","ITEM","items", true)
				.add("net.minecraft.registry.DefaultedRegistry","field_11145","entities", true)
				.add("net.minecraft.registry.DefaultedRegistry","ENTITY","entities", true)
				.add("net.minecraft.registry.MainRegistry","field_11137","tile_entities", true)
				.add("net.minecraft.registry.MainRegistry","BLOCK_ENTITY_TYPE","tile_entities", true)
		);
		
		fieldMap.put("net.minecraft.registry.BlockRegistry",new FieldList()
				.add("net.minecraft.world.blocks.Block","AIR","air", true)
				.add("net.minecraft.world.blocks.Block","field_10124","air", true)
				.add("net.minecraft.world.blocks.Block","STONE","stone", true)
				.add("net.minecraft.world.blocks.Block","field_10340","stone", true)
		);
		
		classMap.put("net.minecraft.world.level.block.Block","net.minecraft.world.blocks.Block");
		classMap.put("net/minecraft/class_2248","net.minecraft.world.blocks.Block");
		
		MethodList.add(methodMap, "net.minecraft.world.blocks.Block", Mojmap.getClassObsf("1.16.4","net.minecraft.world.level.block.Block"),
				"getFriction", "getFriction", "()F");
		MethodList.add(methodMap, "net.minecraft.world.blocks.Block", Mojmap.getClassObsf("1.16.4","net.minecraft.world.level.block.Block"),
				"getSpeedFactor", "speedFactor", "()F");
		MethodList.add(methodMap, "net.minecraft.world.blocks.Block", Mojmap.getClassObsf("1.16.4","net.minecraft.world.level.block.Block"),
				"getJumpFactor", "jumpFactor", "()F");
		MethodList.add(methodMap, "net.minecraft.world.blocks.Block", Mojmap.getClassObsf("1.16.4","net.minecraft.world.level.block.Block"),
				"getExplosionResistance", "explosionResistance", "()F");
		MethodList.add(methodMap, "net.minecraft.world.blocks.Block", Mojmap.getClassObsf("1.16.4","net.minecraft.world.level.block.Block"),
				"isPossibleToRespawnInThis", "blocksRespawning", "()Z");
		
		MethodList.add(methodMap, "net.minecraft.registry.BlockRegistry", Mojmap.getClassObsf("1.16.4","net.minecraft.world.level.block.Blocks"),
				"register", "register", "(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;", true);
		
		try {
			StringBuilder wrapperProperties = new StringBuilder();
			wrapperProperties
					.append(generateWrapperFile("net.minecraft.world.blocks.Block","Block")).append("\n")
					.append(generateWrapperFile("net.minecraft.registry.BlockRegistry","BlockRegistry")).append("\n")
					.append(generateWrapperFile("net.minecraft.registry.MainRegistry","MainRegistry")).append("\n")
					.append(generateWrapperFile("net.minecraft.registry.DefaultedRegistry","DefaultedRegistry")).append("\n")
			;
			write(new File("src/main/resources/wrapper_classes.properties"),wrapperProperties.toString());
		} catch (Throwable ignored) {
		}
	}
	
	public static void main(String[] args) throws IOException {
		write(new File("mojmap.txt"), holder.toFancyString());
		write(new File("intermediary.txt"), iholder.toFancyString());
		StringBuilder mappingsFile = new StringBuilder();
		classMap.forEach((other,flame)->{
			Class clazz = Mojmap.getClassObsf("1.16.4",other);
			if (clazz == null) clazz = Intermediary.getClassObsf("1.16.4",other);
			if (clazz == null) System.out.println(other);
			mappingsFile.append(clazz.getPrimaryName()).append(" : ").append(flame).append("\n");
			if (fieldMap.containsKey(flame)) {
				FieldList list = fieldMap.get(flame);
				list.map.forEach((otherF, flameF) -> mappingsFile.append("f-").append(otherF.type).append(" : ").append(otherF.name).append("->").append(flameF).append("\n"));
			}
			if (methodMap.containsKey(flame)) {
				MethodList list = methodMap.get(flame);
				list.methodObjects.forEach((method) -> mappingsFile.append("m-").append(method.desc).append(" : ").append(method.unmapped).append("->").append(method.mapped).append("\n"));
			}
			mappingsFile.append("\n");
		});
		write(new File("mappings/flame_mappings.mappings"),mappingsFile.toString());
	}
	
	private static void write(File f, String text) throws IOException {
		if (!f.exists()) {
			if (f.getParentFile() != null)
				f.getParentFile().mkdirs();
			f.createNewFile();
		}
		FileOutputStream stream = new FileOutputStream(f);
		stream.write(text.getBytes());
		stream.close();
	}
	
	public static String generateWrapperFile(String clazz, String fileName) throws IOException {
		StringBuilder wrapper = new StringBuilder();
		if (methodMap.containsKey(clazz)) {
			MethodList list = methodMap.get(clazz);
			for (MethodObject methodObject : list.methodObjects) {
				if (!methodObject.unmapped.contains("method")) {
					if (methodObject.isStatic)
						wrapper.append(methodObject.mapped).append(methodObject.desc).append("=").append("static.method_"+methodObject.mapped).append("\n");
					else
						wrapper.append(methodObject.mapped).append(methodObject.desc).append("=").append("method_"+methodObject.mapped).append("\n");
				}
			}
		}
		if (fieldMap.containsKey(clazz)) {
			FieldList listF = fieldMap.get(clazz);
			listF.map.forEach((otherF,flame)->{
				if (!otherF.name.contains("field")) {
					if (otherF.isStatic)
						wrapper.append(otherF.name).append("|").append(otherF.type).append("=").append("static.field_" + flame).append("\n");
					else
						wrapper.append(otherF.name).append("|").append(otherF.type).append("=").append("field_" + flame).append("\n");
				}
			});
		}
		System.out.println(wrapper.toString());
		write(new File("src/main/resources/wrappers/"+fileName+".properties"),wrapper.toString());
		return clazz+"="+fileName;
	}
	
	public static String getMojmapFor(String flamed) {
		for (String key : classMap.keySet()) {
			if (classMap.get(key).equals(flamed)) {
				return key;
			}
		}
		return null;
	}
}
