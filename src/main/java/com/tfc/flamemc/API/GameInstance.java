package com.tfc.flamemc.API;

import com.tfc.flame.API.utils.logging.Logger;
import entries.FlameAPI.Main;
import org.objectweb.asm.ClassReader;
import tfc.flame.FlameURLLoader;
import tfc.flamemc.FlameLauncher;

import java.io.File;
import java.net.URL;

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
		
		FlameURLLoader test = new FlameURLLoader(new URL[0]);
		System.out.println(test.getParent().getClass());
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
					if (version.startsWith("fabric-loader")) {
						Main.addDep("https://maven.fabricmc.net/", "net.fabricmc", "fabric-loader", "0.11.3");
						Main.addDep("https://maven.fabricmc.net/", "net.fabricmc", "intermediary", "1.16.5");
//						Main.addDep("https://maven.fabricmc.net/", "org.ow2.asm", "asm-util", "9.1");
//						Main.addDep("https://maven.fabricmc.net/", "org.ow2.asm", "asm-tree", "9.1");
//						Main.addDep("https://maven.fabricmc.net/", "org.ow2.asm", "asm-analysis", "9.1");
//						Main.addDep("https://maven.fabricmc.net/", "org.ow2.asm", "asm", "9.1");
//						Main.addDep("https://maven.fabricmc.net/", "org.ow2.asm", "asm-commons", "9.1");
						Main.addDep("https://maven.fabricmc.net/", "com.google.jimfs", "jimfs", "1.2-fabric");
						Main.addDep("https://maven.fabricmc.net/", "net.fabricmc", "fabric-loader-sat4j", "2.3.5.4");
						Main.addDep("https://maven.fabricmc.net/", "net.fabricmc", "access-widener", "1.0.0");
						Main.addDep("https://maven.fabricmc.net/", "net.fabricmc", "tiny-remapper", "0.3.0.70");
						Main.addDep("https://maven.fabricmc.net/", "net.fabricmc", "sponge-mixin", "0.9.2+mixin.0.8.2");
						Main.addDep("https://maven.fabricmc.net/", "net.fabricmc", "tiny-mappings-parser", "0.2.2.14");
						Main.addDep("https://jitpack.io/", "com.github.cpw", "modlauncher", "8.1");
						Main.addDep("https://jitpack.io/", "com.github.Mojang", "LegacyLauncher", "a4801b70f8");
						version = version.substring("fabric-loader-".length());
						version = version.substring(version.indexOf("-") + 1);
						String ver = "";
						for (char c : version.toCharArray()) {
							if (c == '.' || Character.isDigit(c)) ver += c;
							else break;
						}
						version = ver;
					} else if (version.contains("OptiFine")) {
						String ver = "";
						for (char c : version.toCharArray()) {
							if (c == '.' || Character.isDigit(c)) ver += c;
							else break;
						}
						version = ver;
					} else if (version.contains("fabric")) {
						String ver = "";
						for (char c : version.toCharArray()) {
							if (c == '.' || Character.isDigit(c)) ver += c;
							else break;
						}
						version = ver;
					}
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
