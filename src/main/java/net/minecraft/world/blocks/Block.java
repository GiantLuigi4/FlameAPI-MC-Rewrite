package net.minecraft.world.blocks;
public class Block {
	//Methods
	public float getFriction() {
		return 0;
	}
	
	public float speedFactor() {
		return 0;
	}
	
	public float jumpFactor() {
		return 0;
	}
	
	public float explosionResistance() {
		return 0;
	}
	
	public boolean blocksRespawning() {
		return false;
	}
	
	
	//Fields
	public final net.minecraft.world.blocks.Block getProperties() {
		return null;
	}
	
	public Block(BlockPropertiesE properties) {
	}
}
