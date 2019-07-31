package svk.opg.game.character.skeletal.serialization;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import svk.opg.game.character.skeletal.animation.KeyPose;
import svk.opg.game.character.skeletal.animation.SkeletalAnimation;

/**
 * @author Lubomir Hlavko
 *
 */

public class SkeletAnimationIO {
	public static void serializeAnimation(FileHandle file, SkeletalAnimation animation) {
		SkeletalAnimationSerializable toSerialize = new SkeletalAnimationSerializable(animation);
		Json js = new Json();
		
		file.writeString(js.prettyPrint(toSerialize), false);
	}
	
	public static SkeletalAnimation deserializeAnimation(FileHandle file) {
		Json js = new Json();
		SkeletalAnimationSerializable serializable = js.fromJson(SkeletalAnimationSerializable.class, file);
		
		SkeletalAnimation result = new SkeletalAnimation();
		for(KeyPose pose : serializable.poses) {
			//result.poses.put(pose.transitionFrames, pose.pose);
			result.poses.add(pose);
		}
		
		return result;
	}
	
	private static  class SkeletalAnimationSerializable {
		public Array<KeyPose> poses; 
		
		public SkeletalAnimationSerializable() {
			poses = new Array<KeyPose>();
		}
		
		public SkeletalAnimationSerializable(SkeletalAnimation animation) {
			poses = new Array<KeyPose>(animation.poses);
		}
	}
}
