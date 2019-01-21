package svk.opg.game.character.skeletal.animation;

import svk.opg.game.character.skeletal.Pose;
import svk.opg.game.character.skeletal.Skelet;

public class LinearTransition {
	public Skelet skeleton;

	private Pose prev;
	private Pose next;
	private int frames;

	private boolean inProgress = false;
	private int step = 0;
	private Pose buffer;

	public LinearTransition(Skelet skeleton) {
			this.skeleton = skeleton;
			
			prev = new Pose("prev", skeleton);
			buffer = new Pose("buffer", new float[skeleton.bones.size]);
		}

	public void stop() {
		inProgress = false;
	}

	public void transformTo(Pose pose, int frames) {
		skeleton.getPoseData(prev);
		this.next = pose;
		this.frames = frames;

		step = 0;
		inProgress = true;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void update() {
		if (!inProgress) {
			return;
		}

		if (step == frames) {
			skeleton.setPose(next);
			inProgress = false;
			return;
		}

		for (int i = 0; i < prev.angles.length; i++) {
			float preva = prev.angles[i];
			float nexta = next.angles[i];

			// float delta = ((float)(nexta - preva) / frames);

			buffer.angles[i] = preva + ((float) (nexta - preva) / frames) * step;
		}

		skeleton.setPose(buffer);
		step++;
	}

	public boolean isInProgress() {
		return inProgress;
	}
}
