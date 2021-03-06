package tfc.flamemc.API.utils.mapping;

import tfc.flamemc.API.GameInstance;
import tfc.mappings.structure.FlameMapHolder;
import tfc.mappings.structure.MappingsClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Flame {
	public static final FlameMapHolder holder;
	
	static {
		try {
			holder = new FlameMapHolder(readUrl("https://raw.githubusercontent.com/GiantLuigi4/FlameAPI-MC-Rewrite/master/mappings/flame_mappings.mappings"));
		} catch (Throwable err) {
			throw new RuntimeException(err);
		}
	}
	
	private static String readUrl(String urlString) throws IOException {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder builder = new StringBuilder();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				builder.append(chars, 0, read);
			
			return builder.toString();
			
		} finally {
			if (reader != null)
				reader.close();
		}
	}
	
	public static MappingsClass getFromObsf(String obsf) {
		return getFromObsf(GameInstance.INSTANCE.versionMap, obsf);
	}
	
	public static MappingsClass getFromObsf(String version, String obsf) {
		MappingsClass clazz = Intermediary.getClassFromObsf(version, obsf);
		if (clazz != null) {
			MappingsClass clazz2 = Intermediary.getClassFromInter("1.16.4", clazz.getPrimaryName());
			if (clazz2 != null) {
				MappingsClass from = holder.getFromPrimaryName(clazz2.getPrimaryName());
				if (from != null) return from;
				from = holder.getFromSecondaryName(clazz2.getPrimaryName());
				if (from != null) return from;
			}
		}
		clazz = Mojmap.getClassFromObsf(version, obsf);
		if (clazz != null) {
			MappingsClass clazz2 = Mojmap.getClassFromMojmap("1.16.4", clazz.getPrimaryName());
			if (clazz2 != null) {
				MappingsClass from = holder.getFromPrimaryName(clazz2.getPrimaryName());
				if (from != null) return from;
				from = holder.getFromSecondaryName(clazz2.getPrimaryName());
				if (from != null) return from;
			}
		}
		return null;
	}
	
	public static MappingsClass getFromMapped(String version, String mapped) {
		{
			MappingsClass clazz = Mapping.getWithoutFlame(version, mapped.replace("/", "."));
			if (clazz != null) {
				MappingsClass res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
			clazz = Mapping.getWithoutFlame(version, mapped.replace(".", "/"));
			if (clazz != null) {
				MappingsClass res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
			
			clazz = Mojmap.getClassFromMojmap(version, mapped);
			if (clazz != null) {
				MappingsClass res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
			clazz = Intermediary.getClassFromInter(version, mapped);
			if (clazz != null) {
				MappingsClass res = getFromObsf(version, clazz.getSecondaryName());
				if (res != null) return res;
			}
		}
		return null;
	}
	
	public static MappingsClass getFromMapped(String mapped) {
		return getFromMapped(GameInstance.INSTANCE.versionMap, mapped);
	}
	
	public static MappingsClass getFromFlame(String name) {
		MappingsClass clazz = holder.getFromSecondaryName(name.replace(".", "/"));
		if (clazz != null) return clazz;
		return holder.getFromSecondaryName(name.replace("/", "."));
	}
}
