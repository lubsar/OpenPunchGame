package svk.opg.game.character.skeletal;

/**
 * @author Lubomir Hlavko
 *
 */
public class Pose {
	public float[] angles;
	public String id;
	
	public Pose() {
		this.angles = null;
		this.id = null;
	}
	
	public Pose(String id, float[] angles) {
		this.id = id;
		this.angles = angles;
	}
	
	public Pose(String id, Skelet skelet) {
		this.id = id;
		this.angles = new float[skelet.bones.size];
		
		skelet.getPoseData(this);
	}
}
