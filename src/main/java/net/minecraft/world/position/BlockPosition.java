package net.minecraft.world.position;

import net.minecraft.util.vecmath.Vector3d;
import net.minecraft.util.vecmath.Vector3i;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class BlockPosition extends Vector3i {
	public BlockPosition(int x, int y, int z) {
		super(x, y, z);
	}
	
	public BlockPosition(double x, double y, double z) {
		super(x, y, z);
	}
	
	public BlockPosition(Vector3d vec) {
		this(vec.x, vec.y, vec.z);
	}
	
	public BlockPosition(Vector3i source) {
		super(source.getX(), source.getY(), source.getZ());
	}
}
