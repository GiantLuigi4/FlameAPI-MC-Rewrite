package com.tfc.flamemc.API.utils.wrapper;

import com.tfc.bytecode.Compiler;
import com.tfc.bytecode.EnumCompiler;
import com.tfc.bytecode.compilers.Janino_Compiler;
import com.tfc.flamemc.FlameLauncher;

public class CompilerHelper {
	private static final Janino_Compiler compiler = new Janino_Compiler();
	
	static {
		Compiler.setReferenceLoader(FlameLauncher.getLoader());
	}
	
	public static byte[] compile(String classFile, String name) {
		Throwable janinoErr = null;
		Throwable javassistErr = null;
		for (EnumCompiler value : EnumCompiler.values()) {
			try {
				if (value == EnumCompiler.ASM || value == EnumCompiler.BCEL) {
					throw new RuntimeException("null");
				}
				byte[] bytes = Compiler.compile(value, classFile);
				System.out.println(value);
				return bytes;
			} catch (Throwable err1) {
				if (value == EnumCompiler.JANINO) {
					try {
						return compiler.compile(classFile, name);
					} catch (Throwable err) {
						janinoErr = err;
					}
				} else if (value == EnumCompiler.JAVASSIST) {
					javassistErr = err1;
				}
			}
		}
		janinoErr.printStackTrace();
		javassistErr.printStackTrace();
		return null;
	}
}
