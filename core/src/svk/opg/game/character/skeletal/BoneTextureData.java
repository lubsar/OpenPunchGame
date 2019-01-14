package svk.opg.game.character.skeletal;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BoneTextureData {
	public int boneIndex;
	public int orderIndex;
	
	public Sprite sprite;
	
	public int xMappingOffset;
	public int yMappingOffset;
	
	public float angleOffset;
	public boolean flip;
	
	public BoneTextureData(int boneIndex, Sprite sprite, int xMappingOffset, int yMappingOffset, int orderIndex, boolean flip) {
		this.boneIndex = boneIndex;
		this.sprite = sprite;
		this.xMappingOffset = xMappingOffset;
		this.yMappingOffset = yMappingOffset;
		this.orderIndex = orderIndex;
		this.flip = flip;
	}
	
	public BoneTextureData(int boneIndex, Sprite sprite, int xMappingOffset, int yMappingOffset, int orderIndex, boolean flip, float angleOffset) {
		this.boneIndex = boneIndex;
		this.sprite = sprite;
		this.xMappingOffset = xMappingOffset;
		this.yMappingOffset = yMappingOffset;
		this.orderIndex = orderIndex;
		this.flip = flip;
		this.angleOffset = angleOffset;
	}
}
