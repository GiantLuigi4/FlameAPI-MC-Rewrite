package com.tfc.flamemc.API;

import com.tfc.flame.API.utils.logging.Logger;
import com.tfc.flame_asm.annotations.Unmodifiable;

import java.io.File;

@Unmodifiable
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
		
		String version = "";
		String versionMap = "";
		String gameDir = "";
		String assetVersion = "";
		boolean isMappedVersion = false;
		
		try {
			boolean isAssetIndex = false;
			boolean isVersion = false;
			boolean isDir = false;
			
			System.out.println(args);
			
			for (String s : args) {
				if (s.equals("--version")) {
					isVersion = true;
				} else if (isVersion) {
					version = s;
					versionMap = version.replace("-flame", "");
					isVersion = false;
				} else if (s.equals("--gameDir")) {
					isDir = true;
				} else if (isDir) {
					gameDir = s;
					isDir = false;
				} else if (s.equals("--assetIndex")) {
					isAssetIndex = true;
				} else if (isAssetIndex) {
					assetVersion = s;
					isMappedVersion = Integer.parseInt(assetVersion.replace("1.", "")) >= 14;
					isAssetIndex = false;
				}
			}
		} catch (Throwable err) {
			Logger.logErrFull(err);
		}
		if (versionMap.startsWith("1.14"))
			isMappedVersion = isMappedVersion && versionMap.replace("1.14", "").equals(".4");
		
		this.version = version;
		this.versionMap = versionMap;
		this.gameDir = gameDir;
		this.assetVersion = assetVersion;
		this.isMappedVersion = isMappedVersion;
		
		this.execDir = System.getProperty("user.dir");
	}
}
