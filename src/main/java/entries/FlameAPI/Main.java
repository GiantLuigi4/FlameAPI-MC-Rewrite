package entries.FlameAPI;

import tfc.flame.IFlameAPIMod;
import com.tfc.flamemc.API.GameInstance;
import tfc.flameasm.remapper.MappingApplicator;
import tfc.flameasm.remapper.MappingsInfo;
import tfc.flamemc.FlameLauncher;
import net.minecraft.registry.BlockRegistry;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.resource.ResourceName;
import net.minecraft.world.blocks.Block;
import net.minecraft.world.blocks.BlockProperties;
import tfc.mappings.structure.FlameMapHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

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
			FlameLauncher.downloadDep(name1, url);
//			Method m = FlameLauncher.dependencyManager.getClass().getMethod("addFromURL", String.class);
//			m.invoke(FlameLauncher.dependencyManager, (FlameLauncher.isDev ? "run\\libraries\\" : "\\libraries\\" + name1 + "," + url));
		} catch (Throwable err) {
			FlameLauncher.downloadDep(name1, url);
		}
	}
	
	private static String getGameDir() {
		return GameInstance.INSTANCE.gameDir;
	}
	
	@Override
	public void preinit(String[] strings) {
//		addDep(
//				"https://files.minecraftforge.net/",
//				"de.oceanlabs.mcp.mcp_config",
//				"1.16.5","20210115.111550"
//		);
		MappingApplicator.targetMappings = "OBFUSCATION";
	}
	
	@Override
	public void init(String[] strings) {
	}
	
	private static String getExecDir() {
		return GameInstance.INSTANCE.execDir;
	}
	
	@Override
	public void setupAPI(String[] args) {
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
//			java.lang.Class<?> clazz = Block.class;
//			clazz = BlockProperties.class;
//			clazz = ResourceName.class;
//			clazz = DefaultedRegistry.class;
//			clazz = BuiltinRegistries.class;
//			clazz = BlockRegistry.class;
			
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
					try {
						Field f1 = MappingApplicator.class.getDeclaredField("mappingsSystems");
						f1.setAccessible(true);
						HashMap<String, MappingsInfo> infos = (HashMap<String, MappingsInfo>) f1.get(null);
						infos.replace("FLAME", new MappingsInfo(new FlameMapHolder(new String(bytes)), "INTERMEDIARY", "FLAME"));
					} catch (Throwable ignored) {
					}
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
