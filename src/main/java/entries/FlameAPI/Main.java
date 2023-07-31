package entries.FlameAPI;

import tfc.flame.loader.IFlameMod;
import tfc.flamemc.API.GameInstance;
import tfc.flamemc.FlameConfig;
import tfc.flamemc.FlameLauncher;
import tfc.flamemc.FlameUtils;

import java.io.File;

public class Main implements IFlameMod {
	private static final String[] gameArgs;
	
	public static String[] getArgs() {
		return gameArgs;
	}
	
	static {
		FlameConfig.println("Loading API");
		gameArgs = FlameLauncher.gameArgs;
		GameInstance.init(gameArgs);
		HookinSetup.init();
		
		try {
			GameInstance.INSTANCE.dataDirectory = FlameUtils.isDev ? new File(Main.getExecDir() + File.separator + "run") : new File((Main.getGameDir() == null ? Main.getExecDir() : Main.getGameDir()));
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
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
		String folder = path.replace(".", File.separator) + File.separator + name + File.separator + version;
		FlameLauncher.downloadDependency(url, folder, name + "-" + version + ".jar");
	}
	
	private static String getGameDir() {
		return GameInstance.INSTANCE.gameDir;
	}
	
	@Override
	public void preInit() {
		FlameConfig.println("Loading API pre-init");
	}
	
	@Override
	public void onInit() {
		FlameConfig.println("Loading API on-init");
	}
	
	private static String getExecDir() {
		return GameInstance.INSTANCE.execDir;
	}
}
