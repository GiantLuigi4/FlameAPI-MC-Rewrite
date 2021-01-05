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
	
	static {
		classMap.put("net.minecraft.core.Registry","net.minecraft.registry.MainRegistry");
		classMap.put("net/minecraft/class_2378","net.minecraft.registry.MainRegistry");
		
		classMap.put("net.minecraft.core.DefaultedRegistry","net.minecraft.registry.DefaultedRegistry");
		classMap.put("net/minecraft/class_2348","net.minecraft.registry.DefaultedRegistry");
		
		fieldMap.put("net.minecraft.registry.MainRegistry",new FieldList()
				.add("net.minecraft.registry.DefaultedRegistry","field_11146","blocks")
				.add("net.minecraft.registry.DefaultedRegistry","BLOCK","blocks")
				.add("net.minecraft.registry.DefaultedRegistry","field_11142","items")
				.add("net.minecraft.registry.DefaultedRegistry","ITEM","items")
				.add("net.minecraft.registry.DefaultedRegistry","field_11145","entities")
				.add("net.minecraft.registry.DefaultedRegistry","ENTITY","entities")
				.add("net.minecraft.registry.MainRegistry","field_11137","tile_entities")
				.add("net.minecraft.registry.MainRegistry","BLOCK_ENTITY_TYPE","tile_entities")
		);
	}
	
	public static void main(String[] args) throws IOException {
		MojmapHolder holder = Mojang.generate("1.16.4");
		write(new File("mojmap.txt"), holder.toFancyString());
		Holder iholder = com.tfc.mappings.types.Intermediary.generate("1.16.4");
		write(new File("intermediary.txt"), iholder.toFancyString());
		StringBuilder mappingsFile = new StringBuilder();
		classMap.forEach((other,flame)->{
			Class clazz = Mojmap.getClassObsf("1.16.4",other);
			if (clazz == null) clazz = Intermediary.getClassObsf("1.16.4",other);
			if (clazz == null) System.out.println(other);
			mappingsFile.append(clazz.getPrimaryName()).append(" : ").append(flame).append("\n");
			if (fieldMap.containsKey(flame)) {
				FieldList list = fieldMap.get(flame);
				list.map.forEach((otherF, flameF) -> {
							mappingsFile.append("f-").append(otherF.type).append("->").append(otherF.name).append(" : ").append(flameF).append("\n");
						}
				);
			}
//			if (method_map.containsKey(flame)) {
//				FieldList list = method_map.get(flame);
//				list.map.forEach((otherF, flameF) -> {
//							mappingsFile.append("f-").append(otherF.type).append("->").append(otherF.name).append(" : ").append(flameF).append("\n");
//						}
//				);
//			}
			mappingsFile.append("\n");
		});
		write(new File("mappings/flame_mappings.mappings"),mappingsFile.toString());
	}
	
	private static void write(File f, String text) throws IOException {
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		FileOutputStream stream = new FileOutputStream(f);
		stream.write(text.getBytes());
		stream.close();
	}
}
