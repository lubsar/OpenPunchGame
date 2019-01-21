package svk.opg.game.character.skeletal.animation;

import svk.opg.game.character.skeletal.Pose;
import svk.opg.game.character.skeletal.Skelet;

public class KeyPose extends Pose {
	public int transitionFrames;

	public KeyPose(){
		super();
	}
	
	public KeyPose(String id, float[] angles, int transitionFrames) {
		super(id, angles);
		
		this.transitionFrames = transitionFrames;
	}

	public KeyPose(String id, Skelet skelet, int transitionFrames) {
		super(id, skelet);
		
		this.transitionFrames = transitionFrames;
	}
}
