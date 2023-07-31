import tfc.flamemc.FlameLauncher;
import tfc.flamemc.FlameUtils;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		FlameUtils.isDev = true;

		FlameLauncher.main(new String[]{
				"--username", "GiantLuigi4",
				"--version", "bta-1.7.7.0",
				"--uuid", "ad1dbe37-ce3b-41d9-a4d0-8c2d67f99b39",
				"--main_class", "net.minecraft.client.Minecraft"
		});
	}
}
//<:GWchadThink:787396691032539197>
//LOL