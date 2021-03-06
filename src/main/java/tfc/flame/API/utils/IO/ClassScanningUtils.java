package tfc.flame.API.utils.IO;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * just to reduce code redundancy, lol
 * also, it can make files vastly smaller if done correctly
 */
//probably gonna wind up removing this class
public class ClassScanningUtils {
	public static void forLittleFiles(JarFile file, BiConsumer<Scanner, JarEntry> textConsumer) {
		file.stream().forEach(f -> {
			long size = f.getCompressedSize();
			if (size < 6000 && !f.getName().startsWith("com/tfc") && f.getName().endsWith(".class")) {
				try {
					InputStream stream = file.getInputStream(f);
					Scanner sc = new Scanner(stream);
					textConsumer.accept(sc, f);
					sc.close();
					stream.close();
				} catch (Throwable ignored) {
				}
			}
		});
	}
	
	public static void forAllFiles(JarFile file, BiConsumer<Scanner, JarEntry> textConsumer, Function<String, Boolean> fileValidator) {
		file.stream().forEach(f -> {
			if (fileValidator.apply(f.getName())) {
				try {
					InputStream stream = file.getInputStream(f);
					Scanner sc = new Scanner(stream);
					textConsumer.accept(sc, f);
					sc.close();
					stream.close();
				} catch (Throwable ignored) {
				}
			}
		});
	}
	
	public static void forEachLine(Scanner sc, Consumer<String> textConsumer) {
		while (sc.hasNextLine()) {
			textConsumer.accept(sc.nextLine());
		}
	}
	
	public static boolean checkLine(String query, HashMap<String, Boolean> checks, String line) {
		if (!checks.containsKey(query)) {
			if (line.contains(query)) {
				checks.put(query, true);
				return true;
			}
		}
		return false;
	}
	
	public static String toClassName(String s) {
		return s.replace(".class", "").replace("/", ".");
	}
	
	public static Class<?> classFor(String clazz) throws ClassNotFoundException {
		return Class.forName(toClassName(clazz));
	}
}