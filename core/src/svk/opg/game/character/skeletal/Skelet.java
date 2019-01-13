package svk.opg.game.character.skeletal;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import svk.opg.game.object.GameObject;

/**
 * @author Lubomir Hlavko
 */
public class Skelet extends GameObject {
	private Bone root;
	
	public List<Bone> bones = new ArrayList<Bone>();
	public List<BoneTextureData> boneTextures = new ArrayList<BoneTextureData>();
	
	/**
	 * Constructor initializes the root bone.
	 */
	public Skelet() {
		super();
		root = new Bone();
		root.position = this.position;
		root.angleDeg = 0;
		bones.add(root);
	}
	
	/**
	 * Creates and connects new bone to bone at parrentBoneIndex in the {@link #bones}.
	 * @param parrentBoneIndex index in {@link #bones} to which will be new bone connected
	 * @param length length of the new bone
	 * @param angle angle between parent bone and new bone
	 */
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
	
	public void addTexture(BoneTextureData data) {
		boneTextures.add(data);
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
	
	protected void updateState() {
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
	
	public void rotateAroundParrent(int nodeIndex, float angle, boolean lockother) {
		Bone node = bones.get(nodeIndex);
		if(node.parrent == null) {
			throw new RuntimeException("cant rotate master node around itself");
		}
		node.rotateArroundParrent(angle, lockother);
	}
	
	public void setBoneAngle(int boneIndex, float angle) {
		Bone node = bones.get(boneIndex);
		node.angleDeg = angle;
		node.updatePosition();
	}
	
	public void setBoneAngles(float angles[]) {
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
		
		public void rotateParrentAround(float angle) {
			if(parrent == null) {
				System.out.println("no parrent to rotate");
				return;
			}
			
			Bone bone = this;
			Vector2 trans = null;
			while(bone.parrent != null) {
				bone.angleDeg += angle;
				
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
	
	public static class BoneTextureData {
		public int boneIndex;
		
		public Sprite sprite;
		
		public int xMappingOffset;
		public int yMappingOffset;
		
		public float angleOffset;
		public boolean flip;
		
		public BoneTextureData(int boneIndex, Sprite sprite, int xMappingOffset, int yMappingOffset, boolean flip) {
			this.boneIndex = boneIndex;
			this.sprite = sprite;
			this.xMappingOffset = xMappingOffset;
			this.yMappingOffset = yMappingOffset;
			this.flip = flip;
		}
		
		public BoneTextureData(int boneIndex, Sprite sprite, int xMappingOffset, int yMappingOffset, boolean flip, float angleOffset) {
			this.boneIndex = boneIndex;
			this.sprite = sprite;
			this.xMappingOffset = xMappingOffset;
			this.yMappingOffset = yMappingOffset;
			this.flip = flip;
			this.angleOffset = angleOffset;
		}
	}
}