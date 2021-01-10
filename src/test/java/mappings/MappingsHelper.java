package mappings;

import com.tfc.flamemc.API.utils.mapping.Intermediary;
import com.tfc.flamemc.API.utils.mapping.Mojmap;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Holder;
import com.tfc.mappings.structure.MojmapHolder;
import com.tfc.mappings.types.Mojang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class MappingsHelper {
	private static final HashMap<String, String> classMap = new HashMap<>();
	private static final HashMap<String, FieldList> fieldMap = new HashMap<>();
	private static final HashMap<String, MethodList> methodMap = new HashMap<>();
	private static final HashMap<String, ConstructorList> constructorMap = new HashMap<>();
	
	public static final MojmapHolder holder = Mojang.generate("1.16.4");
	public static final Holder iholder = com.tfc.mappings.types.Intermediary.generate("1.16.4");
	
	static {
		try {
			write(new File("mojmap.txt"), holder.toFancyString());
			write(new File("intermediary.txt"), iholder.toFancyString());
		} catch (Throwable ignored) {
		}
		

		classMap.put("net/minecraft/core/Registry", "net/minecraft/registry/MainRegistry");
		classMap.put("net/minecraft/class_2378", "net/minecraft/registry/MainRegistry");
		
		classMap.put("net/minecraft/core/DefaultedRegistry", "net/minecraft/registry/DefaultedRegistry");
		classMap.put("net/minecraft/class_2348", "net/minecraft/registry/DefaultedRegistry");
		
		classMap.put("net/minecraft/world/level/block/Blocks", "net/minecraft/registry/BlockRegistry");
		classMap.put("net/minecraft/class_2246", "net/minecraft/registry/BlockRegistry");
		
		fieldMap.put("net/minecraft/registry/MainRegistry", new FieldList()
				.add("net/minecraft/registry/DefaultedRegistry", "field_11146", "blocks", true)
				.add("net/minecraft/registry/DefaultedRegistry", "BLOCK", "blocks", true)
				.add("net/minecraft/registry/DefaultedRegistry", "field_11142", "items", true)
				.add("net/minecraft/registry/DefaultedRegistry", "ITEM", "items", true)
				.add("net/minecraft/registry/DefaultedRegistry", "field_11145", "entities", true)
				.add("net/minecraft/registry/DefaultedRegistry", "ENTITY", "entities", true)
				.add("net/minecraft/registry/MainRegistry", "field_11137", "tileEntities", true)
				.add("net/minecraft/registry/MainRegistry", "BLOCK_ENTITY_TYPE", "tileEntities", true)
		);
		
		fieldMap.put("net/minecraft/registry/BlockRegistry", new FieldList()
				.add("net/minecraft/world/blocks/Block", "AIR", "air", true)
				.add("net/minecraft/world/blocks/Block", "field_10124", "air", true)
				.add("net/minecraft/world/blocks/Block", "STONE", "stone", true)
				.add("net/minecraft/world/blocks/Block", "field_10340", "stone", true)
		);
		
		
		{
			classMap.put("net/minecraft/world/level/block/Block", "net/minecraft/world/blocks/Block");
			classMap.put("net/minecraft/class_2248", "net/minecraft/world/blocks/Block");
			
			fieldMap.put("net/minecraft/world/blocks/Block", new FieldList()
					.add("net/minecraft/world/blocks/BlockProperties", "properties", "properties", false)
					.add("net/minecraft/world/blocks/BlockProperties", "field_23155", "properties", false)
			);
			
			MethodList.add(methodMap, "net/minecraft/world/blocks/Block", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/world/level/block/Block"),
					"getFriction", "getFriction", "()F");
			MethodList.add(methodMap, "net/minecraft/world/blocks/Block", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/world/level/block/Block"),
					"getSpeedFactor", "speedFactor", "()F");
			MethodList.add(methodMap, "net/minecraft/world/blocks/Block", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/world/level/block/Block"),
					"getJumpFactor", "jumpFactor", "()F");
			MethodList.add(methodMap, "net/minecraft/world/blocks/Block", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/world/level/block/Block"),
					"getExplosionResistance", "explosionResistance", "()F");
			MethodList.add(methodMap, "net/minecraft/world/blocks/Block", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/world/level/block/Block"),
					"isPossibleToRespawnInThis", "blocksRespawning", "()Z");
		}
		
		{
//			classMap.put("net.minecraft.world.level.block.state.BlockBehaviour$Properties", "net/minecraft/world/blocks/BlockProperties");
//			classMap.put("net/minecraft/class_4970$class_2251", "net/minecraft/world/blocks/BlockProperties");
		}
		
		MethodList.add(methodMap, "net/minecraft/registry/BlockRegistry", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/world/level/block/Blocks"),
				"register", "register", "(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;", true);
		
		{
			classMap.put("net/minecraft/resources/ResourceLocation", "net/minecraft/resource/ResourceLocation");
			classMap.put("net/minecraft/class_2960", "net/minecraft/resource/ResourceLocation");
			
			constructorMap.put("net/minecraft/resource/ResourceLocation", new ConstructorList()
					.add("(Ljava/lang/String;)V")
					.add("(Ljava/lang/String;Ljava/lang/String;)V")
			);
			
			MethodList.add(methodMap, "net/minecraft/resource/ResourceLocation", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/resources/ResourceLocation"),
					"getPath", "getPath", "()Ljava/lang/String;", false);
			MethodList.add(methodMap, "net/minecraft/resource/ResourceLocation", Mojmap.getClassFromMojmap("1.16.4", "net/minecraft/resources/ResourceLocation"),
					"getNamespace", "getNamespace", "()Ljava/lang/String;", false);
		}
		
		try {
			StringBuilder wrapperProperties = new StringBuilder();
			wrapperProperties
					.append(generateWrapperFile("net/minecraft/world/blocks/Block", "Block")).append("\n")
					.append(generateWrapperFile("net/minecraft/registry/BlockRegistry", "BlockRegistry")).append("\n")
					.append(generateWrapperFile("net/minecraft/registry/MainRegistry", "MainRegistry")).append("\n")
					.append(generateWrapperFile("net/minecraft/registry/DefaultedRegistry", "DefaultedRegistry")).append("\n")
					.append(generateWrapperFile("net/minecraft/resource/ResourceLocation", "ResourceLocation")).append("\n")
					.append(generateWrapperFile("net/minecraft/world/blocks/BlockProperties", "BlockPropertiesE")).append("\n")
			;
			write(new File("src/main/resources/wrapper_classes.properties"), wrapperProperties.toString());
		} catch (Throwable ignored) {
			ignored.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		StringBuilder mappingsFile = new StringBuilder();
		System.out.println(parseSourceDescFromBytecodeDesc("[[[I"));
		System.out.println(parseBytecodeFromSourceDescDesc("int[][][]"));
		classMap.forEach((other, flame) -> {
			if (other.contains("class_")) {
				Class clazz = Mojmap.getClassFromMojmap("1.16.4", other);
				
				if (clazz == null) clazz = Intermediary.getClassFromInter("1.16.4", other);
				if (clazz == null) System.out.println(other);
				
				mappingsFile.append(clazz.getPrimaryName()).append(" : ").append(flame).append("\n");
				
				if (fieldMap.containsKey(flame)) {
					FieldList list = fieldMap.get(flame);
					list.map.forEach((otherF, flameF) -> {
						if (otherF.name.startsWith("field_"))
							mappingsFile.append("f-").append(otherF.type).append(" : ").append(otherF.name).append("->").append(flameF).append("\n");
					});
				}
				
				if (methodMap.containsKey(flame)) {
					MethodList list = methodMap.get(flame);
					list.methodObjects.forEach((method) -> {
						if (method.shouldBeUsed)
							mappingsFile.append("m-").append(method.desc).append(" : ").append(method.unmapped).append("->").append(method.mapped).append("\n");
					});
				}
				
				mappingsFile.append("\n");
			}
		});
		
		write(new File("mappings/flame_mappings.mappings"), mappingsFile.toString());
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
		
		StringBuilder wrapperClass = new StringBuilder("package ").append(clazz, 0, clazz.lastIndexOf("/")).append(";\npublic class ").append(fileName).append(" {\n");
		
		if (methodMap.containsKey(clazz)) {
			wrapperClass.append("\t//Methods\n");
			MethodList list = methodMap.get(clazz);
			
			for (MethodObject methodObject : list.methodObjects) {
				if (methodObject.shouldBeUsed) {
					wrapperClass.append("\tpublic ");
					
					if (methodObject.isStatic) {
						wrapperClass.append("static ");
						wrapper.append(methodObject.unmapped).append(methodObject.desc).append("=").append("static.method_").append(methodObject.mapped).append("\n");
					} else {
						wrapper.append(methodObject.unmapped).append(methodObject.desc).append("=").append("method_").append(methodObject.mapped).append("\n");
					}
					
					String type = getFlameFor(methodObject.getReturnType());
					String returnVal = " null";
					
					if (methodObject.getReturnType().equals("void")) {
						type = "void";
						returnVal = "";
					} else if (!parseSourceDescFromBytecodeDesc(methodObject.getReturnType()).equals(methodObject.getReturnType())) {
						returnVal = " 0";
						if (methodObject.getReturnType().equals("Z")) returnVal = " false";
						else if (methodObject.getReturnType().equals("V")) returnVal = "";
					}
					
					if (type.equals("V")) {
						type = "void";
						returnVal = "";
					} else if (isNative(parseSourceDescFromBytecodeDesc(methodObject.getReturnType())))
						type = parseSourceDescFromBytecodeDesc(type);
					else returnVal = " null";
					
					
					wrapperClass.append(type).append(" ").append(methodObject.mapped).append(methodObject.getDescriptorForMethod()).append("{return").append(returnVal).append(";}\n");
				}
			}
		}
		
		if (fieldMap.containsKey(clazz)) {
			if (methodMap.containsKey(clazz))
				wrapperClass.append("\n\n");
			
			wrapperClass.append("\t//Fields\n");
			FieldList listF = fieldMap.get(clazz);
			listF.map.forEach((otherF, flame) -> {
				if (otherF.name.startsWith("field_")) {
					wrapperClass.append("\tpublic ");
					
					if (otherF.isStatic) {
						wrapperClass.append("static ");
						wrapper.append(otherF.name).append("|").append(otherF.type).append("=").append("static.field_").append(flame).append("\n");
					} else {
						wrapper.append(otherF.name).append("|").append(otherF.type).append("=").append("field_").append(flame).append("\n");
					}
					
					String returnVal = " null";
					if (otherF.type.equals("boolean")) {
						returnVal = " false";
					} else if (isNative(otherF.type)) {
						returnVal = " 0";
					}
					
					String thisCase = flame.substring(0, 1).toUpperCase() + flame.substring(1);
					if (!otherF.isStatic) wrapperClass.append("final ");
					wrapperClass.append(getFlameFor(otherF.type)).append(" ").append("get").append(thisCase).append("(){return").append(returnVal).append(";}\n");
				}
			});
		}
		
		if (constructorMap.containsKey(clazz)) {
			if (methodMap.containsKey(clazz) || fieldMap.containsKey(clazz))
				wrapperClass.append("\n\n");
			
			wrapperClass.append("\t//Constructors\n");
			ConstructorList listF = constructorMap.get(clazz);
			listF.constructorObjects.forEach((constructorObject) -> {
				wrapperClass.append("\tpublic ").append(fileName).append("(").append(parseParams(constructorObject.desc.substring(1, constructorObject.desc.length() - 2))).append("){}\n");
			});
		}
		
		wrapperClass.append("}\n");
		System.out.println(wrapperClass.toString());
		
		write(new File("src/main/resources/wrappers/" + fileName + ".properties"), wrapper.toString());
		write(new File("src/main/java/" + clazz.replace(".", "/") + ".java"), wrapperClass.toString().replace("/", ".").replace("..", "//").replace(", )", ")"));
		
		return clazz + "=" + fileName;
	}
	
	public static boolean isNative(String type) {
		return
				type.equals("int") ||
						type.equals("byte") ||
						type.equals("char") ||
						type.equals("long") ||
						type.equals("short") ||
						type.equals("float") ||
						type.equals("double") ||
						type.equals("boolean");
	}
	
	public static String getMojmapFor(String flamed) {
		for (String key : classMap.keySet()) {
			if (classMap.get(key).equals(flamed)) {
				return key;
			}
		}
		
		return null;
	}
	
	public static String getFlameFor(String clazz) {
		for (String key : classMap.keySet()) {
			if (key.replace("/", ".").equals(clazz) || key.replace(".", "/").equals(clazz)) {
				return classMap.get(key);
			}
		}
		
		return clazz;
	}
	
	public static String parseParams(String desc) {
		boolean inDesc = false;
		
		StringBuilder out = new StringBuilder();
		StringBuilder type = new StringBuilder("L");
		
		HashMap<String, Integer> counts = new HashMap<>();
		for (int i = 0; i < desc.length(); i++) {
			if (inDesc) {
				type.append(desc.charAt(i));
				
				if (desc.charAt(i) == ';') {
					String val = getFlameFor(parseSourceDescFromBytecodeDesc(type.toString())).replace(".", "/");
					String typeName = val.substring(val.lastIndexOf("/") + 1);
					typeName = typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
					
					if (!counts.containsKey(val)) {
						out.append(val).append(" ").append(typeName).append(", ");
						counts.put(val, 1);
					} else {
						out.append(val).append(" ").append(typeName).append(counts.get(typeName)).append(", ");
						counts.remove(val, counts.get(val) + 1);
					}
					
					type = new StringBuilder("L");
					inDesc = false;
				}
			} else {
				if (desc.charAt(i) == 'L') {
					inDesc = true;
				} else {
					String val = parseSourceDescFromBytecodeDesc(String.valueOf(desc.charAt(i)));
					String typeName = String.valueOf(desc.charAt(i)).toLowerCase();
					
					if (!counts.containsKey(val)) {
						out.append(val).append(" ").append(typeName).append(", ");
						counts.put(val, 1);
					} else {
						out.append(val).append(" ").append(typeName).append(counts.get(typeName)).append(", ");
						counts.remove(val, counts.get(val) + 1);
					}
				}
			}
		}
		
		return out.toString();
	}
	
	private static String parseSourceDescFromBytecodeDesc(String desc) {
		String out = "";
		String desc1 = desc.replace("[", "").trim();
		if (desc1.equals("J")) out = "long";
		else if (desc1.equals("I")) out = "int";
		else if (desc1.equals("S")) out = "short";
		else if (desc1.equals("B")) out = "byte";
		else if (desc1.equals("C")) out = "char";
		else if (desc1.equals("F")) out = "float";
		else if (desc1.equals("D")) out = "double";
		else if (desc1.equals("Z")) out = "boolean";
		else if (desc1.equals("V")) out = "void";
		else if (desc1.startsWith("L")) out = desc1.substring(1, desc1.length() - 1);
		for (int i = 0; i < desc.split("\\[").length - 1; i++) {
			out += "[]";
		}
		return out;
	}

	private static String parseBytecodeFromSourceDescDesc(String desc) {
		String out = "";
		String desc1 = desc.replace("[]", "").trim();
		for (int i = 0; i < desc.split("\\[]").length; i++) {
			out += "[";
		}
		if (desc1.equals("long")) out = "J";
		else if (desc1.equals("int")) out = "I";
		else if (desc1.equals("short")) out = "S";
		else if (desc1.equals("byte")) out = "B";
		else if (desc1.equals("char")) out = "C";
		else if (desc1.equals("float")) out = "F";
		else if (desc1.equals("double")) out = "D";
		else if (desc1.equals("boolean")) out = "Z";
		else if (desc1.equals("void")) out = "V";
		else out = ("L" + desc1 + ";").replace(".", "/");
		return out;
	}
}
