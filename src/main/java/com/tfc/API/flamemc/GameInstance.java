package com.tfc.API.flamemc;

import com.tfc.API.flame.utils.logging.Logger;
import com.tfc.API.flame_asm.annotations.Unmodifiable;
import entries.FlameAPI.Main;

@Unmodifiable
public class GameInstance {
	public static final GameInstance INSTANCE = new GameInstance(Main.getArgs());
	
	public final String versionMap;
	public final String version;
	public final String gameDir;
	public final String assetVersion;
	public final boolean isMappedVersion;
	
	public GameInstance(String[] args) {
		String version = "";
		String versionMap = "";
		String gameDir = "";
		String assetVersion = "";
		boolean isMappedVersion = false;
		
		try {
			boolean isAssetIndex = false;
			boolean isVersion = false;
			boolean isDir = false;
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
	}
	
	public static void init() {
		//just allows INSTANCE to be initialized before any mods can switch the args
	}
}
