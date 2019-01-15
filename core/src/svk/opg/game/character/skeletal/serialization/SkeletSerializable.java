package svk.opg.game.character.skeletal.serialization;

import com.badlogic.gdx.utils.Array;

import svk.opg.game.character.skeletal.Bone;
import svk.opg.game.character.skeletal.BoneTextureData;
import svk.opg.game.character.skeletal.Skelet;

public class SkeletSerializable {
	public Array<BoneSerializable> bones;
	public Array<BoneTextureDataSerializable> boneTextures;
	
	
	public SkeletSerializable(){}
	
	public SkeletSerializable(Skelet skeleton) {
		this.bones = new Array<BoneSerializable>(skeleton.bones.size);
		this.boneTextures = new Array<BoneTextureDataSerializable>(skeleton.boneTextures.size);
		
		for(int i = 1; i < skeleton.bones.size; i++) {
			Bone b = skeleton.bones.get(i);
			this.bones.add(new BoneSerializable(skeleton.bones.indexOf(b.parrent, true), skeleton.getBoneId(i), b.length, b.angleDeg));
		}
		
		for(BoneTextureData text: skeleton.boneTextures) {
			this.boneTextures.add(new BoneTextureDataSerializable(text.boneIndex, text.orderIndex, String.format("textures/%d.png", text.boneIndex), text.xMappingOffset, text.yMappingOffset, text.angleOffset, text.flip));
		}
	}
	
	public static class BoneSerializable {	
		public int parrentIndex;
		public String boneId = null;
		public float length = 0.0f;
		public float angleDeg = 0.0f;
		
		public BoneSerializable(){}
		
		public BoneSerializable(int parrentIndex, String boneId, float length, float angleDeg) {
			this.parrentIndex = parrentIndex;
			this.boneId = boneId;
			this.length = length;
			this.angleDeg = angleDeg;
		}
	}
	
	public static class BoneTextureDataSerializable {
		public int boneIndex;
		public int orderIndex;
		
		public String spritePath;
		
		public int xMappingOffset;
		public int yMappingOffset;
		
		public float angleOffset;
		public boolean flip;
		
		public BoneTextureDataSerializable() {}
		
		public BoneTextureDataSerializable(int boneIndex, int orderIndex, String spritePath, int xMappingOffset,
				int yMappingOffset, float angleOffset, boolean flip) {
			this.boneIndex = boneIndex;
			this.orderIndex = orderIndex;
			this.spritePath = spritePath;
			this.xMappingOffset = xMappingOffset;
			this.yMappingOffset = yMappingOffset;
			this.angleOffset = angleOffset;
			this.flip = flip;
		}
	}
}
