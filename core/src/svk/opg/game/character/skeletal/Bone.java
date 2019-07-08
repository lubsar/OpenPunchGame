package svk.opg.game.character.skeletal;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Lubomir Hlavko
 *
 */
public class Bone {
	private Vector2 position;
	
	private float length = 0.0f;
	private float angleDeg = 0.0f;
	private String name = null;
	
	public Bone parrent = null;
	
	public List<Bone> children;
	
	public Bone(String name) {
		this.position = new Vector2();
		this.children = new ArrayList<Bone>();
		
		this.name = name;
	}
	
	public Bone(String name, Bone parrent, float length, float angleDeg) {
		this.name = name;
		this.parrent = parrent;
		this.length = length;
		this.angleDeg = angleDeg;
		
		if(parrent != null){
			this.position = new Vector2((float) (parrent.position.x + Math.cos(Math.toRadians(angleDeg)) * length), (float) (parrent.position.y + Math.sin(Math.toRadians(angleDeg)) * length));		
		} else {
			this.position = new Vector2();
		}
		
		this.children = new ArrayList<Bone>();
	}
	
	protected void updatePosition() {
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
	
	public void setAngle(float angleDeg) {
		this.angleDeg = angleDeg;
		
		updatePosition();
	}
	
	public void setAngleNoUpdate(float angleDeg) {
		this.angleDeg = angleDeg;
	}
	
	public void setLength(float length) {
		this.length = length;
		
		updatePosition();
	}
	
	public float getAngle() {
		return this.angleDeg;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public void move(Vector2 movement) {
		this.position.add(movement);
	}
	
	public void move(float xa, float ya) {
		this.position.add(xa, ya);
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	public float getLength() {
		return this.length;
	}

	public String getName() {
		return name;
	}
}
