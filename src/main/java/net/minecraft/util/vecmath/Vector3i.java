package net.minecraft.util.vecmath;

@SuppressWarnings("FieldMayBeFinal")
public class Vector3i {
	private int x;
	private int y;
	private int z;
	
	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3i(double x, double y, double z) {
		this((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
}
