package com.tfc.flamemc.API;

import com.tfc.flame.API.utils.logging.Logger;
import entries.FlameAPI.Main;
import tfc.flame.FlameURLLoader;
import tfc.flameasm.remapper.MappingApplicator;
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
//					if (version.contains("forge")) {
//						FlameLauncher.downloadDep("cpw/mods/modlauncher/8.0.9/modlauncher-8.0.9.jar", "https://files.minecraftforge.net/maven/cpw/mods/modlauncher/8.0.9/modlauncher-8.0.9.jar");
//						FlameLauncher.downloadDep("org/apache/logging/log4j/log4j-api/2.11.2/log4j-api-2.11.2.jar", "https://files.minecraftforge.net/maven/org/apache/logging/log4j/log4j-api/2.11.2/log4j-api-2.11.2.jar");
//						FlameLauncher.downloadDep("net/jodah/typetools/0.8.3/typetools-0.8.3.jar", "https://files.minecraftforge.net/maven/net/jodah/typetools/0.8.3/typetools-0.8.3.jar");
//						FlameLauncher.downloadDep("org/apache/maven/maven-artifact/3.6.3/maven-artifact-3.6.3.jar", "https://files.minecraftforge.net/maven/org/apache/maven/maven-artifact/3.6.3/maven-artifact-3.6.3.jar");
//						FlameLauncher.downloadDep("org/jline/jline/3.12.1/jline-3.12.1.jar", "https://files.minecraftforge.net/maven/org/jline/jline/3.12.1/jline-3.12.1.jar");
//						FlameLauncher.downloadDep("com/electronwill/night-config/toml/3.6.3/toml-3.6.3.jar", "https://files.minecraftforge.net/maven/com/electronwill/night-config/toml/3.6.3/toml-3.6.3.jar");
//						FlameLauncher.downloadDep("com/electronwill/night-config/core/3.6.3/core-3.6.3.jar", "https://files.minecraftforge.net/maven/com/electronwill/night-config/core/3.6.3/core-3.6.3.jar");
//						FlameLauncher.downloadDep("net/minecraftforge/unsafe/0.2.0/unsafe-0.2.0.jar", "https://files.minecraftforge.net/maven/net/minecraftforge/unsafe/0.2.0/unsafe-0.2.0.jar");
//						FlameLauncher.downloadDep("net/minecraftforge/coremods/4.0.6/coremods-4.0.6.jar", "https://files.minecraftforge.net/maven/net/minecraftforge/coremods/4.0.6/coremods-4.0.6.jar");
//						FlameLauncher.downloadDep("net/minecraftforge/forgespi/3.2.0/forgespi-3.2.0.jar", "https://files.minecraftforge.net/maven/net/minecraftforge/forgespi/3.2.0/forgespi-3.2.0.jar");
//						FlameLauncher.downloadDep("net/minecraftforge/eventbus/4.0.0/eventbus-4.0.0.jar", "https://files.minecraftforge.net/maven/net/minecraftforge/eventbus/4.0.0/eventbus-4.0.0.jar");
//						FlameLauncher.downloadDep("org/antlr/antlr4-runtime/4.9.1/antlr4-runtime-4.9.1.jar", "https://files.minecraftforge.net/maven/org/antlr/antlr4-runtime/4.9.1/antlr4-runtime-4.9.1.jar");
//						FlameLauncher.downloadDep("net/minecraftforge/accesstransformers/3.0.1/accesstransformers-3.0.1.jar", "https://files.minecraftforge.net/maven/net/minecraftforge/accesstransformers/3.0.1/accesstransformers-3.0.1.jar");
//						FlameLauncher.downloadDep("cpw/mods/grossjava9hacks/1.3.3/grossjava9hacks-1.3.3.jar", "https://files.minecraftforge.net/maven/cpw/mods/grossjava9hacks/1.3.3/grossjava9hacks-1.3.3.jar");
//						FlameLauncher.downloadDep("org/apache/logging/log4j/log4j-core/2.11.2/log4j-core-2.11.2.jar", "https://files.minecraftforge.net/maven/org/apache/logging/log4j/log4j-core/2.11.2/log4j-core-2.11.2.jar");
//						FlameLauncher.downloadDep("net/minecrell/terminalconsoleappender/1.2.0/terminalconsoleappender-1.2.0.jar", "https://files.minecraftforge.net/maven/net/minecrell/terminalconsoleappender/1.2.0/terminalconsoleappender-1.2.0.jar");
//						FlameLauncher.downloadDep("net/sf/jopt-simple/jopt-simple/5.0.4/jopt-simple-5.0.4.jar", "https://files.minecraftforge.net/maven/net/sf/jopt-simple/jopt-simple/5.0.4/jopt-simple-5.0.4.jar");
//						FlameLauncher.downloadDep("org/spongepowered/mixin/0.8.2/mixin-0.8.2.jar", "https://files.minecraftforge.net/maven/org/spongepowered/mixin/0.8.2/mixin-0.8.2.jar");
//						FlameLauncher.downloadDep("net/minecraftforge/nashorn-core-compat/15.1.1.1/nashorn-core-compat-15.1.1.1.jar", "https://files.minecraftforge.net/maven/net/minecraftforge/nashorn-core-compat/15.1.1.1/nashorn-core-compat-15.1.1.1.jar");
//						MappingApplicator.targetMappings = "SEARGE";
//						Main.addDep("https://repo1.maven.org/maven2/", "com.fasterxml.jackson.core", "jackson-core", "2.12.3");
//						Main.addDep("https://repo1.maven.org/maven2/", "com.fasterxml.jackson.core", "jackson-databind", "2.12.3");
//						Main.addDep("https://repo1.maven.org/maven2/", "com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", "2.12.3");
//						Main.addDep("https://repo1.maven.org/maven2/", "javax.servlet", "javax.servlet-api", "3.0.1");
//						Main.addDep("https://repo1.maven.org/maven2/", "org.apache.logging.log4j", "log4j-web", "2.14.1");
//					}
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
						MappingApplicator.targetMappings = "INTERMEDIARY";
						version = version.substring("fabric-loader-".length());
						version = version.substring(version.indexOf("-") + 1);
					}
					String ver = "";
					for (char c : version.toCharArray()) {
						if (c == '.' || Character.isDigit(c)) ver += c;
						else break;
					}
					version = ver;
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
