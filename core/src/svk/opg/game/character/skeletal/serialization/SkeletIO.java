package svk.opg.game.character.skeletal.serialization;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Json;

import svk.opg.game.character.skeletal.BoneTextureData;
import svk.opg.game.character.skeletal.Skelet;
import svk.opg.game.character.skeletal.serialization.SkeletSerializable.BoneSerializable;
import svk.opg.game.character.skeletal.serialization.SkeletSerializable.BoneTextureDataSerializable;

public class SkeletIO {
	public static void serializeSkelet(FileHandle file, Skelet skeleton) {
		SkeletSerializable toSerialize = new SkeletSerializable(skeleton);
		Json js = new Json();
		
		file.writeString(js.prettyPrint(toSerialize), false);
		
		for(int i = 0; i < toSerialize.boneTextures.size; i++) {
			TextureData imageData = skeleton.boneTextures.get(i).sprite.getTexture().getTextureData();
			imageData.prepare();
			
			PixmapIO.writePNG(file.parent().child(toSerialize.boneTextures.get(i).spritePath), imageData.consumePixmap());
		}
	}
	
	public static Skelet deserializeSkelet(FileHandle file) {
		Json js = new Json();
		SkeletSerializable serializable = js.fromJson(SkeletSerializable.class, file);
		
		Skelet result = new Skelet();
		
		for(BoneSerializable bone : serializable.bones) {
			result.addBone(bone.parrentIndex, bone.length, bone.angleDeg, bone.boneId);
		}
		
		for(BoneTextureDataSerializable text : serializable.boneTextures) {
			result.addTexture(new BoneTextureData(text.boneIndex, new Sprite(new Texture(file.parent().child(text.spritePath))), text.xMappingOffset, text.yMappingOffset, text.orderIndex, text.flip, text.angleOffset));
		}
		
		return result;
	}
}
