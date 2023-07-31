package entries.FlameAPI;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import tfc.flame.loader.FlameLoader;
import tfc.flame.loader.IFlameLoader;
import tfc.flame.loader.asm.ClassTransformer;
import tfc.flame.loader.asm.ITransformerEntry;
import tfc.flame.loader.asm.Phase;
import tfc.hookin.HookParser;
import tfc.hookin.HookinPatcher;
import tfc.hookin.platform.PlatformCalls;
import tfc.hookin.platform.Setup;
import tfc.hookin.util.tree.BytecodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HookinSetup extends Setup implements ITransformerEntry {
	public static void init() {
		new HookinSetup().setup();
	}
	
	private static ClassTransformer INSTANCE;
	
	@Override
	public void setup() {
		IFlameLoader loader = (IFlameLoader) HookinSetup.class.getClassLoader();
		PlatformCalls.runSetup(this);
		
		HookParser parser = new HookParser();
		HookinPatcher patcher = new HookinPatcher();
		
		((FlameLoader) loader).transformers.add(
				Phase.FIRST, INSTANCE = new ClassTransformer() {
					@Override
					public ClassNode accept(ClassNode classNode) {
						if (patcher.patchClass(classNode)) {
							try {
								File fl = new File(classNode.name + ".txt");
								if (!fl.getParentFile().exists()) fl.getParentFile().mkdirs();
								BytecodeWriter.write(classNode, fl);
								
								ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
								classNode.accept(writer);
								
								FileOutputStream outputStream = new FileOutputStream(classNode.name + ".class");
								outputStream.write(writer.toByteArray());
								outputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							return classNode;
						}
						return null;
					}
				}
		);
		
		try {
			ClassNode node = new ClassNode();
			ClassReader reader = new ClassReader(
					loader.getBytecode(
							"test.hooks.TestHookin", true,
							INSTANCE
					)
			);
			reader.accept(node, ClassReader.EXPAND_FRAMES);
			patcher.accept(parser.parse(node));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ClassTransformer getTransformer() {
		return INSTANCE;
	}
	
	@Override
	public void run(PlatformCalls calls) {
		calls.requireClassName = false;
		calls.requireDescriptor = false;
		calls.classMapper = (name) -> name;
		calls.methodMapper = (owner, name, desc) -> name;
		calls.fieldMapper = (owner, name, desc) -> name;
		
		calls.logger = (level, text) -> {
			switch (level) {
				case 0:
					System.out.println(text);
					break;
				case 1:
				case 2:
				case 3:
					System.err.println(text);
					break;
				default:
					throw new RuntimeException("Invalid log level " + level);
			}
		};
	}
}
