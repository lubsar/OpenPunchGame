package svk.opg.game.character.skeletal;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import svk.opg.game.object.GameObject;

/**
 * @author Lubomir Hlavko
 *
 */
public class Skelet extends GameObject {
	private Bone root;
	
	public List<Bone> bones = new ArrayList<Bone>();
	
	public Skelet() {
		super();
		root = new Bone();
		root.position = this.position;
		root.angleDeg = 0;
		bones.add(root);
	}
	
	public int addBone(int parrentBoneIndex, float length, float angle) {
		Bone bone = new Bone();
		Bone parrent = bones.get(parrentBoneIndex);
		
		bone.length = length;
		bone.angleDeg = angle;
		bone.parrent = parrent;
		bone.updatePosition();
		
		bones.add(bone);
		parrent.children.add(bone);
		
		return bones.size() -1;
	}
	
	public int addNode(Bone node, int parrentIndex) {
		Bone parrent = bones.get(parrentIndex);
	
		node.parrent = parrent;
		node.updatePosition();
		
		bones.add(node);
		int index = bones.size() -1;
		bones.addAll(node.children);
		
		parrent.children.add(node);
		
		return index;
	}
	
	public Bone getBone(int index) {
		return bones.get(index);
	}
		
	@Override
	public void update() {
//		for(Bone node : bones){	
//			if(node.parrent == null) {
//				continue;
//			}
//			
////			if(length != position.distance(parrent.position)) {
////				Vec2f s = Vec2f.subtract(position, parrent.position);
////				
////				float t = (float) Math.sqrt(length * length / (s.x  * s.x + s.y * s.y));
////				position.x = s.x * t + parrent.position.x; 
////				position.y = s.y * t + parrent.position.y; 
////			}
////		
//		}
	}
	
	private void updateState() {
		root.updatePosition();
	}
	
	@Override
	public void setPosition(Vector2 position) {
		Vector2 translation = (new Vector2(this.position)).sub(position);
		
		this.position.x = position.x;
		this.position.y = position.y;
		
		for(int i = 1; i < bones.size(); i++) {
			bones.get(i).position.add(translation);
		}
		updateState();
	}
	
	@Override
	public void move(Vector2 vec) {
		for(Bone node : bones) {
			node.position.add(vec);
		}
		
		updateState();
	}
	
	@Override
	public void move(float x, float y) {
		for(Bone node : bones) {
			node.position.add(x, y);
		}
		
		updateState();
	}
	
	@Override
	public void setPosition(float x, float y) {
		Vector2 translation = (new Vector2(this.position)).sub(x,y);
		
		this.position.x = x;
		this.position.y = y;
		
		for(int i = 1; i < bones.size(); i++) {
			bones.get(i).position.add(translation);
		}
		updateState();
	}
	
	public void rotate(float angleDeg) {
		for(Bone node : bones) {
			node.angleDeg += angleDeg;
		}
		updateState();
	}
	
	public void rotateAroundParrent(int nodeIndex, float angle, boolean lockother) {
		Bone node = bones.get(nodeIndex);
		if(node.parrent == null) {
			throw new RuntimeException("cant rotate master node around itself");
		}
		node.rotateArroundParrent(angle, lockother);
	}
	
	public void rotateChildren(int nodeIndex, float angle, boolean lockother) {
		bones.get(nodeIndex).rotateChildren(angle, lockother);
	}
	
	public void setRotation(int nodeIndex, float angle) {
		Bone node = bones.get(nodeIndex);
		node.angleDeg = angle;
		node.updatePosition();
	}
	
	public void setRotations(float angles[]) {
		for(int i = 0; i < bones.size(); i++) {
			bones.get(i).angleDeg = angles[i];
		}
		
		updateState();
	}
	
	public static class Bone {
		public Vector2 position;
		
		public float length = 0.0f;
		public float angleDeg = 0.0f;
		public Bone parrent = null;
		
		public List<Bone> children;
		
		protected Bone() {
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
	}
}