package tfc.flame.API.utils.logging;

import tfc.flame.FlameConfig;

public class Logger {
	public static void log(Object obj) {
		if (obj instanceof String) {
			System.out.print((String) obj);
			FlameConfig.field.append((String) obj);
		} else {
			System.out.print(obj.toString());
			FlameConfig.field.append(obj.toString());
		}
	}
	
	public static void logLine(Object obj) {
		if (obj instanceof String) {
			System.out.print(obj + "\n");
			FlameConfig.field.append(obj + "\n");
		} else {
			System.out.print(obj.toString() + "\n");
			FlameConfig.field.append(obj.toString() + "\n");
		}
	}
	
	public static void logErr(Throwable err) {
		FlameConfig.logError(err);
	}
	
	public static void logErrFull(Throwable err) {
		StringBuilder s = new StringBuilder();
		FlameConfig.field.append("\n\n");
		s.append("Flame encountered an error:\n");
		s.append(err.getClass().getName()).append(": ").append(err.getLocalizedMessage()).append("\n");
		for (StackTraceElement element : err.getStackTrace())
			s.append(element.toString()).append("\n");
		if (err.getCause() != null)
			logErrFullNoPrefixFull(err.getCause());
		System.out.print(s.toString());
		FlameConfig.field.append(s.toString());
		err.getStackTrace();
	}
	
	public static void logErrNoPrefix(Throwable err) {
		StringBuilder s = new StringBuilder();
		s.append(err.getClass().getName()).append(": ").append(err.getLocalizedMessage()).append("\n");
		for (StackTraceElement element : err.getStackTrace()) {
			s.append(element.toString()).append("\n");
		}
		FlameConfig.field.append(s.toString());
	}
	
	public static void logErrFullNoPrefixFull(Throwable err) {
		StringBuilder s = new StringBuilder();
		s.append(err.getClass().getName()).append(": ").append(err.getLocalizedMessage()).append("\n");
		for (StackTraceElement element : err.getStackTrace())
			s.append(element.toString()).append("\n");
		System.out.print(s.toString());
		FlameConfig.field.append(s.toString());
		if (err.getCause() != null)
			logErrFullNoPrefixFull(err.getCause());
	}
}
