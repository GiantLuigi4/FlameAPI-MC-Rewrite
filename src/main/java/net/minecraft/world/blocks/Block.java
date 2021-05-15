package net.minecraft.world.blocks;
public class Block extends AbstractBlock {
	public Block(BlockProperties properties) {
	}

	//Methods
	public float friction() {
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
}
