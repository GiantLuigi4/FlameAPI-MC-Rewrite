package entries.FlameAPI;

import com.tfc.flamemc.API.utils.mapping.Intermediary;
import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Field;
import com.tfc.mappings.structure.FlameMapHolder;
import tfc.flame.IFlameAPIMod;
import com.tfc.flamemc.API.GameInstance;
import tfc.flameasm.remapper.MappingApplicator;
import tfc.flamemc.FlameLauncher;
import net.minecraft.registry.BlockRegistry;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.resource.ResourceName;
import net.minecraft.world.blocks.Block;
import net.minecraft.world.blocks.BlockProperties;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class Main implements IFlameAPIMod {
	private static String[] gameArgs;
	
	public static String[] getArgs() {
		return gameArgs;
	}
	
	private static final String bytecodeUtilsVersion = "455c3d13b7";
	
	/**
	 * downloads a maven artifact from the internet
	 *
	 * @param repo    the repo to download from
	 * @param path    the path
	 * @param name    artifact name
	 * @param version version
	 */
	public static void addDep(String repo, String path, String name, String version) {
		String url = repo + path.replace(".", "/") + "/" + name + "/" + version + "/" + name + "-" + version + ".jar";
		String name1 = path.replace(".", File.separatorChar + "") + File.separatorChar + name + File.separatorChar + version + File.separatorChar + name + "-" + version + ".jar";
		try {
			Method m = FlameLauncher.dependencyManager.getClass().getMethod("addFromURL", String.class);
			m.invoke(FlameLauncher.dependencyManager, (FlameLauncher.isDev ? "run\\libraries\\" : "\\libraries\\" + name1 + "," + url));
		} catch (Throwable err) {
			FlameLauncher.downloadDep(name1, url);
		}
	}
	
	private static String getGameDir() {
		return GameInstance.INSTANCE.gameDir;
	}
	
	@Override
	public void preinit(String[] strings) {
	}
	
	@Override
	public void init(String[] strings) {
	}
	
	private static String getExecDir() {
		return GameInstance.INSTANCE.execDir;
	}
	
	@Override
	public void setupAPI(String[] args) {
		PrintStream oldStream = System.out;
		System.setOut(new PrintStream(oldStream) {
			@Override
			public void write(int b) {
				super.write(b);
			}
			
			@Override
			public void print(String s) {
				super.print(s);
				System.err.println(s);
			}
		});
		
		gameArgs = args;
		GameInstance.init(args);
		
		try {
			if (!FlameLauncher.isDev)
				GameInstance.INSTANCE.dataDirectory = new File((Main.getGameDir() == null ? Main.getExecDir() : Main.getGameDir()));
			else
				GameInstance.INSTANCE.dataDirectory = new File(Main.getExecDir() + "\\run");
			//Bytecode-Utils
			downloadBytecodeUtils();
			addDep("https://jitpack.io/", "com.github.GiantLuigi4", "FlameASM", "9eb3bad50f");
			//Compilers
//			addDep("https://repo1.maven.org/maven2/", "org.javassist", "javassist", "3.27.0-GA");
//			addDep("https://repo1.maven.org/maven2/", "org.codehaus.janino", "janino", "3.1.2");
//			addDep("https://repo1.maven.org/maven2/", "org.codehaus.janino", "commons-compiler", "3.1.2");
//			addDep("https://repo1.maven.org/maven2/", "org.codehaus.janino", "commons-compiler-jdk", "3.1.2");
			//Mappings Helper
			addDep("https://jitpack.io/", "com.github.GiantLuigi4", "MCMappingsHelper", "3edf7efa3d");
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	private void downloadBytecodeUtils() {
		addDep(
				"https://jitpack.io/",
				"com.github.GiantLuigi4",
				"Bytecode-Utils",
				bytecodeUtilsVersion
		);
	}
	
	@Override
	public void postinit(String[] strings) {
		if (FlameLauncher.isDev) {
			java.lang.Class<?> clazz = Block.class;
			clazz = BlockProperties.class;
			clazz = ResourceName.class;
			clazz = DefaultedRegistry.class;
			clazz = BuiltinRegistries.class;
			clazz = BlockRegistry.class;
			
			File f = new File("mappings/flame_mappings.mappings");
			if (f.exists()) {
				byte[] bytes = null;
				FileInputStream stream = null;
				ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
				try {
					stream = new FileInputStream(f);
					int b;
					while ((b = stream.read()) != -1) stream1.write(b);
					bytes = stream1.toByteArray();
				} catch (Throwable ignored) {
					try {
						try {
							stream1.close();
							stream1.flush();
						} catch (Throwable ignored1) {
						}
						if (stream != null) stream1.close();
					} catch (Throwable ignored1) {
					}
				}
				if (bytes != null) {
					FlameMapHolder flame = new FlameMapHolder(new String(bytes).replace("\r", ""));
					MappingApplicator.classMapper = (name) -> {
						Class clazzFlame = flame.getFromSecondaryName(name);
						if (clazzFlame == null) return null;
						String interName = clazzFlame.getPrimaryName();
						Class interClass = Intermediary.getClassFromInter(interName);
						if (interClass == null) return null;
						return interClass.getSecondaryName();
					};
					MappingApplicator.methodMapper = (name, methodName) -> {
						//TODO: make this work for when there are multiple methods with the same name
						Class clazzFlame = flame.getFromSecondaryName(name);
						if (clazzFlame == null) return null;
						String interName = clazzFlame.getPrimaryName();
						com.tfc.mappings.structure.Method m = clazzFlame.getMethodPrimary(methodName);
						if (m == null) return null;
						String interMethodName = m.getSecondary();
						Class interClass = Intermediary.getClassFromInter(interName);
						if (interClass == null) return null;
						m = interClass.getMethodPrimary(interMethodName);
						if (m == null) return null;
						return m.getSecondary();
					};
					MappingApplicator.fieldMapper = (name, fieldName) -> {
						Class clazzFlame = flame.getFromSecondaryName(name);
						if (clazzFlame == null) return null;
						String interName = clazzFlame.getPrimaryName();
						Field selected = null;
						for (Field field : clazzFlame.getFields())
							if (field.getPrimary().equals(fieldName)) {
								selected = field;
								break;
							}
						if (selected == null) return null;
						String interMethodName = selected.getSecondary();
						Class interClass = Intermediary.getClassFromInter(interName);
						if (interClass == null) return null;
						selected = null;
						for (Field field : interClass.getFields())
							if (field.getPrimary().equals(interMethodName)) {
								selected = field;
								break;
							}
						if (selected == null) return null;
						return selected.getSecondary();
					};
				}
			}
		}
		
		MapperTest.init();
		
//		BlockRegistry.register(location.toString(), new Block(PropertiesAccessor.getProperties(BlockRegistry.getStone())));

//		System.out.println(MainRegistry.getBlocks());
//		Block block = BlockRegistry.register("hi",null);

//		BlockRegistry.register(location.toString(), new Block());
	}
}
