package svk.opg.game.character.skeletal;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//TODO move to Bone
public class BoneTextureData {
	public String boneName;
	public int orderIndex;
	
	public FileHandle spriteHandle;
	public Sprite sprite = null;
	
	public int xMappingOffset;
	public int yMappingOffset;
	
	public float angleOffset;
	public boolean flip;
	
	public BoneTextureData(String boneName, FileHandle spriteHandle, int xMappingOffset, int yMappingOffset, int orderIndex, boolean flip) {
		this.boneName = boneName;
		this.spriteHandle = spriteHandle;
		this.xMappingOffset = xMappingOffset;
		this.yMappingOffset = yMappingOffset;
		this.orderIndex = orderIndex;
		this.flip = flip;
	}
	
	public BoneTextureData(String boneName, FileHandle spriteHandle, int xMappingOffset, int yMappingOffset, int orderIndex, boolean flip, float angleOffset) {
		this.boneName = boneName;
		this.spriteHandle = spriteHandle;
		this.xMappingOffset = xMappingOffset;
		this.yMappingOffset = yMappingOffset;
		this.orderIndex = orderIndex;
		this.flip = flip;
		this.angleOffset = angleOffset;
	}
	
	public void loadTexture() {
		sprite = new Sprite(new Texture(spriteHandle));
	}
}
