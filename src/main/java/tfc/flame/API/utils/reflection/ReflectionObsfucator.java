package tfc.flame.API.utils.reflection;

import tfc.flameasm.remapper.MappingApplicator;
import tfc.flameasm.remapper.MappingsSteps;

import java.lang.reflect.Method;

public class ReflectionObsfucator {
	public static Method getMethod(
			Class<?> clazz, String name, Class<?>... args
	) {
		System.out.println(clazz.getName());
		MappingsSteps steps = MappingApplicator.getSteps(MappingApplicator.targetMappings, "FLAME");
		System.out.println(MappingApplicator.classMapper.apply(clazz.getName(), steps));
		return null;
	}
}
