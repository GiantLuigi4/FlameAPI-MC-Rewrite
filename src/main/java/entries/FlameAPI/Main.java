package entries.FlameAPI;

import com.tfc.API.flame_asm.annotations.Unmodifiable;
import com.tfc.API.flamemc.GameInstance;
import com.tfc.flame.IFlameAPIMod;

@Unmodifiable
public class Main implements IFlameAPIMod {
	private static String[] gameArgs;
	
	public static String[] getArgs() {
		return gameArgs;
	}
	
	@Override
	public void setupAPI(String[] args) {
		gameArgs = args;
		GameInstance.init();
	}
	
	@Override
	public void preinit(String[] strings) {
	}
	
	@Override
	public void init(String[] strings) {
	}
	
	@Override
	public void postinit(String[] strings) {
	}
}
