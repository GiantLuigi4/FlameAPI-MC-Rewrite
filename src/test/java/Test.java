import tfc.flamemc.FlameLauncher;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		FlameLauncher.isDev = true;
		
		File f = new File("");
		args = new String[]{
				"--username", "GiantLuigi4",
//				"--version", "fabric-loader-0.11.3-1.16.5",
				"--version", "1.16.5-srg",
//				"--version", "1.16.5",
				"--gameDir", f.getAbsolutePath() + "\\run",
				"--assetsDir", FlameLauncher.findMCDir(false) + "\\assets",
				"--assetIndex", "1.16",
				"--accessToken", "PLEASE_FLAME_WORK_I_BEG_YOU",
				"--uuid", "ad1dbe37-ce3b-41d9-a4d0-8c2d67f99b39",
				"--userType", "mojang",
				"--versionType", "release"
		};
		FlameLauncher.main(args);
	}
}
//<:GWchadThink:787396691032539197>
//LOL