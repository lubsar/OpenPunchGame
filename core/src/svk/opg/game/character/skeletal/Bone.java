package svk.opg.game.character.skeletal;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;


public class Bone {
	public Vector2 position;
	
	public float length = 0.0f;
	public float angleDeg = 0.0f;
	public Bone parrent = null;
	
	public List<Bone> children;
	
	public Bone() {
		this.position = new Vector2();
		this.children = new ArrayList<Bone>();
	}
	
	public void updatePosition() {
		if(parrent != null){
			position.x = (float) (parrent.position.x + Math.cos(Math.toRadians(angleDeg)) * length);
			position.y = (float) (parrent.position.y + Math.sin(Math.toRadians(angleDeg)) * length);				
		}
		
		for(Bone child : children) {
			child.updatePosition();
		}
	}
	
	public void rotateArroundParrent(float angle, boolean lockother) {
		if(parrent == null) {
			System.out.println("no parrent to rotate arround");
			return;
		}
		
		angleDeg += angle;
		
		position.x = (float) (parrent.position.x + Math.cos(Math.toRadians(angleDeg)) * length);
		position.y = (float) (parrent.position.y + Math.sin(Math.toRadians(angleDeg)) * length);
		
		if(lockother) {
			for(Bone child : children) {
				child.rotateArroundParrent(angle, lockother);
			}
		} else {
			updatePosition();
		}
	}
	
	public void rotateChildren(float angle, boolean lockother) {
		for(Bone child : children) {
			child.rotateArroundParrent(angle, lockother);
		}
	}
	
	public void rotateParrentAround(float angle) {
		if(parrent == null) {
			System.out.println("no parrent to rotate");
			return;
		}
		
		rotateChildren(angle, true);
		
		Bone bone = this;
		Vector2 trans = null;
		while(bone.parrent != null) {
			//adjust angle
			bone.angleDeg += angle;
			
			//calculate for translating the rest of the bones
			Vector2 old = new Vector2(bone.parrent.position);
			if(trans != null) {
				bone.parrent.position.add(trans);
			}
			
			bone.parrent.position.rotateAround(bone.position, angle);
			
			for(Bone child : bone.parrent.children) {
				if(child != bone) {
					child.rotateArroundParrent(angle, true);					
				}
			}
			
			trans = new Vector2(bone.parrent.position).sub(old);
			
			bone = bone.parrent;
		}
		
		bone.updatePosition();
	}
}
