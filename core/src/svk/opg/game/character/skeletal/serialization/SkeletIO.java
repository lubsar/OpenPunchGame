package svk.opg.game.character.skeletal.serialization;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import svk.opg.game.character.skeletal.Bone;
import svk.opg.game.character.skeletal.BoneTextureData;
import svk.opg.game.character.skeletal.Skelet;

/**
 * @author Lubomir Hlavko
 *
 */
public class SkeletIO {
	public static void serializeSkelet(FileHandle file, Skelet skeleton) {
		SkeletSerializable toSerialize = new SkeletSerializable(skeleton);
		Json js = new Json();
		
		file.writeString(js.prettyPrint(toSerialize), false);
		
                //TODO sprite must be loaded
		for(int i = 0; i < toSerialize.boneTextures.size; i++) {
                        System.out.println(skeleton.boneTextures.get(i));
                        
			TextureData imageData = skeleton.boneTextures.get(i).sprite.getTexture().getTextureData();
			imageData.prepare();
			
			PixmapIO.writePNG(file.parent().child(toSerialize.boneTextures.get(i).spritePath), imageData.consumePixmap());
		}
	}
	
	public static Skelet deserializeSkelet(FileHandle file) {
		Json js = new Json();
		SkeletSerializable serializable = js.fromJson(SkeletSerializable.class, file);
		
		Skelet result = new Skelet();
		
		for(SkeletSerializable.BoneSerializable bone : serializable.bones) {
			result.addBone(bone.name, bone.parentName, bone.length, bone.angleDeg);
		}
		
		for(SkeletSerializable.BoneTextureDataSerializable text : serializable.boneTextures) {
			result.addTexture(new BoneTextureData(text.boneName, file.parent().child(text.spritePath), text.xMappingOffset, text.yMappingOffset, text.orderIndex, text.flip, text.angleOffset));
		}
		
		return result;
	}
	
	private static class SkeletSerializable {
		public Array<BoneSerializable> bones;
		public Array<BoneTextureDataSerializable> boneTextures;
		
		public SkeletSerializable(){}
		
		public SkeletSerializable(Skelet skeleton) {
			this.bones = new Array<BoneSerializable>(skeleton.bones.size);
			this.boneTextures = new Array<BoneTextureDataSerializable>(skeleton.boneTextures.size);
			
			for(int i = 1; i < skeleton.bones.size; i++) {
				Bone b = skeleton.bones.get(i);
				System.out.println(b.getName());
				this.bones.add(new BoneSerializable(b.getName(), b.parrent.getName(), b.getLength(), b.getAngle()));
			}
			
			for(BoneTextureData text: skeleton.boneTextures) {
				this.boneTextures.add(new BoneTextureDataSerializable(text.boneName, text.orderIndex, String.format("textures/%d.png", text.orderIndex), text.xMappingOffset, text.yMappingOffset, text.angleOffset, text.flip));
			}
		}
		
		public static class BoneSerializable {	
			public String name = null;
			public String parentName = null;
			public float length = 0.0f;
			public float angleDeg = 0.0f;
			
			public BoneSerializable(){}
			
			public BoneSerializable(String name, String parentName, float length, float angleDeg) {
				this.name = name;
				this.parentName = parentName;
				this.length = length;
				this.angleDeg = angleDeg;
			}
		}
		
		public static class BoneTextureDataSerializable {
			public String boneName;
			public int orderIndex;
			
			public String spritePath;
			
			public int xMappingOffset;
			public int yMappingOffset;
			
			public float angleOffset;
			public boolean flip;
			
			public BoneTextureDataSerializable() {}
			
			public BoneTextureDataSerializable(String boneName, int orderIndex, String spritePath, int xMappingOffset,
					int yMappingOffset, float angleOffset, boolean flip) {
				this.boneName = boneName;
				this.orderIndex = orderIndex;
				this.spritePath = spritePath;
				this.xMappingOffset = xMappingOffset;
				this.yMappingOffset = yMappingOffset;
				this.angleOffset = angleOffset;
				this.flip = flip;
			}
		}
	}
}
