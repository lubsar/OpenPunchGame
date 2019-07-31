package svk.opg.game.character.skeletal.animation;

import com.badlogic.gdx.utils.Array;

/**
 * @author Lubomir Hlavko
 *
 */

//TODO merge with Skelet
public class SkeletalAnimation {
	public Array<KeyPose> poses;
	
//	private TreeMap<Integer, Pose> frameMap;
	
	public SkeletalAnimation() {
		poses = new Array<KeyPose>();
	}
	
//	private void buildFrameMap() {
//		frameMap = new TreeMap<Integer, Pose>();
//		
//		int frame = poses.get(0).transitionFrames;
//		for(KeyPose pose : poses) {
//			frameMap.put(frame, pose);
//			frame += pose.transitionFrames;
//		}
//	}
}
