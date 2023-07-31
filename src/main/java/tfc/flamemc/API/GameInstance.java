package tfc.flamemc.API;

import tfc.flame.loader.FlameLoader;
import tfc.flame.loader.util.JDKLoader;
import tfc.flamemc.FlameUtils;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

public class GameInstance {
	public static final GameInstance INSTANCE = new GameInstance();
	
	public String versionMap;
	public String version;
	public String gameDir;
	public String execDir;
	public String assetVersion;
	public boolean isMappedVersion;
	public File dataDirectory = null;
	
	public GameInstance(String[] args) {
		setup(args);
	}
	
	public GameInstance() {
	}
	
	public static void init(String[] args) {
		//just allows INSTANCE to be initialized before any mods can switch the args
		INSTANCE.setup(args);
	}
	
	private void setup(String[] args) {
		this.execDir = System.getProperty("user.dir");
		
		FlameLoader test = (FlameLoader) JDKLoader.createLoader(new URL[0]);
		System.out.println("Parent ClassLoader: " + test.getParent().getClass());
		System.out.println(Arrays.toString(args));
		
		this.version = FlameUtils.keyOrDefault(args, "--version", "b1.8");;
		this.versionMap = this.version.replace("-flame", "");;
		this.gameDir = FlameUtils.keyOrDefault(args, "--gameDir", FlameUtils.findRunDir());;
		this.assetVersion = FlameUtils.keyOrDefault(args, "--assetIndex", "None");
		this.isMappedVersion = false;
	}
}
