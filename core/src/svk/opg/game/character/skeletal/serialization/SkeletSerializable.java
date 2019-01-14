package svk.opg.game.character.skeletal.serialization;

import com.badlogic.gdx.utils.Array;

import svk.opg.game.character.skeletal.Bone;
import svk.opg.game.character.skeletal.BoneTextureData;

public class SkeletSerializable {
	public Array<BoneSerializable> bones;
	public Array<BoneTextureDataSerializable> boneTextures;
	
	
	public SkeletSerializable(){}
	
	public SkeletSerializable(Array<Bone> bones, Array<BoneTextureData> boneTextures) {
		this.bones = new Array<BoneSerializable>(bones.size);
		this.boneTextures = new Array<BoneTextureDataSerializable>(boneTextures.size);
		
		for(int i = 1; i < bones.size; i++) {
			Bone b = bones.get(i);
			this.bones.add(new BoneSerializable(bones.indexOf(b.parrent, true), b.length, b.angleDeg));
		}
		
		for(BoneTextureData text: boneTextures) {
			this.boneTextures.add(new BoneTextureDataSerializable(text.boneIndex, text.orderIndex, String.format("textures/%d.png", text.boneIndex), text.xMappingOffset, text.yMappingOffset, text.angleOffset, text.flip));
		}
	}
	
	public static class BoneSerializable {	
		public int parrentIndex;
		public float length = 0.0f;
		public float angleDeg = 0.0f;
		
		public BoneSerializable(){}
		
		public BoneSerializable(int parrentIndex, float length, float angleDeg) {
			this.parrentIndex = parrentIndex;
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
