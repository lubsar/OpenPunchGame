package svk.opg.game.character.skeletal.animation;

import svk.opg.game.character.skeletal.Skelet;

/**
 * @author Lubomir Hlavko
 *
 */

public class LinearSkeletAnimator {
	public Skelet skeleton;
	private SkeletalAnimation animation;
	
	private LinearTransition transition;
	
	private boolean running = false;
	private int poseIndex = 0;
	private boolean loop = false;
	private float speed = 1.0f;
	
	public LinearSkeletAnimator(Skelet skeleton) {
		this.skeleton = skeleton;
		this.transition = new LinearTransition(skeleton);
	}
	
	public void stop() {
		running = false;
	}
	
	public void playAnimation(SkeletalAnimation animation, boolean loop) {
		playAnimation(animation, loop, 1.0f);
	}
	
	public void playAnimation(SkeletalAnimation animation, boolean loop, float speed) {
		this.speed = speed;
		this.animation = animation;
		this.speed = speed;
		
		this.running = true;
		this.poseIndex = 0;
		this.loop = loop;
	}
	
	public void update () {
		if(!running) {
			return;
		}
		
		if(!transition.isInProgress()) {
			KeyPose p = animation.poses.get(poseIndex++);

			if(poseIndex >= animation.poses.size){
				if(loop) {
					poseIndex = 0;
				} else {
					running = false;
				}
				return;
			}
			
			transition.transformTo(p, (int) (p.transitionFrames / speed));
		}
		
		transition.update();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}
}
