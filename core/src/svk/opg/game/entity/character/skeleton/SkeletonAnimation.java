package svk.opg.game.entity.character.skeleton;

import java.util.Map.Entry;
import java.util.TreeMap;

public class SkeletonAnimation {
	private int step = 0;
	private float delta;
	private Skeleton skeleton;
	
	public TreeMap<Integer, Pose> poses;
	
	private boolean running;
	private float[] anglesBuffer;
	
	private Pose prev;
	private Pose next;
	
	public SkeletonAnimation(Skeleton skeleton) {
		this.skeleton = skeleton;
		poses = new TreeMap<Integer, Pose>();
	}
	
	public void stop() {
		running = false;
	}
	
	public void start() {
		prev = poses.firstEntry().getValue();
		
		anglesBuffer = new float[prev.angles.length];
		
		next = getNext(step);
		if(next == null) {
			running = false;
			return;
		}
		
		running = true;
	}
	
	public void setStep(int step) {
		this.step = step;
	}
	
	public void tick(double deltaTime) {
		if(!running) {
			return;
		}
		
		if(step >= next.step) {
			prev = next;
			next = getNext(step);
			
			if(next == null) {
				next = prev;
			}
		}
		
		if(next == prev) {
			running = false;
			
			for(int i = 0; i < prev.angles.length; i++) {
				anglesBuffer[i] = prev.angles[i];
			}
			
		} else {
			for(int i = 0; i < prev.angles.length; i++) {
				float preva = prev.angles[i];
				float nexta = next.angles[i];
			
				delta = ((float)(nexta - preva) / (next.step - prev.step));
				
				anglesBuffer[i] =  preva + ((float)(nexta - preva) / (next.step - prev.step)) * (step - prev.step +1);
			}
		}
		
		skeleton.setRotations(anglesBuffer);
		step += deltaTime;
	}
	
	private Pose getNext(int step) {
		Entry<Integer, Pose> entry = poses.higherEntry(step);
		if(entry == null) {
			return null;
		} else {
			return entry.getValue();			
		}
	}
	
	public Pose getPrev(int step) {
		return poses.lowerEntry(step).getValue();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public static class Pose {
		public float[] angles;
		public int step;
		
		public Pose(int step, float[] angles) {
			this.step = step;
			this.angles = angles;
		}
	}
}
