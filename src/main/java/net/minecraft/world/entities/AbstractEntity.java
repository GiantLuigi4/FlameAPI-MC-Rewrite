package net.minecraft.world.entities;

import net.minecraft.util.vecmath.Vector3d;

@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class AbstractEntity {
	AbstractEntity(Object entityTypeIn, Object worldIn) {
	}
	
		public final float getStandingEyeHeight() {
		return 0;
	}
	
	public Vector3d getMot() {
		return new Vector3d(0,0,0);
	}
	
	public Vector3d getPos() {
		return new Vector3d(0,0,0);
	}
	
	public double getPosX() {
		return 0;
	}
	
	public double getPosY() {
		return 0;
	}
	
	public double getPosZ() {
		return 0;
	}
	
	public void setMot(Vector3d motion) {
	}
	
	public abstract void registerData();
}
