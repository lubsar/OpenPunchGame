package svk.opg.game.character.skeletal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import svk.opg.game.object.GameObject;

/**
 * @author Lubomir Hlavko
 */
public class Skelet extends GameObject {
	private Bone root;
	
	//TODO change to arrays ?
	public Array<Bone> bones = new Array<Bone>();
	
	public Array<BoneTextureData> boneTextures = new Array<BoneTextureData>();
	protected int[] textureOrderBuffer = null; 
	
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
		
		return bones.size -1;
	}
	
	public void addTexture(BoneTextureData data) {
		boneTextures.add(data);
	}
	
	public Bone getBone(int index) {
		return bones.get(index);
	}
		
	@Override
	public void update() {
		updateState();
		
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
	
	protected void createTextureOrderBuffer() {
		textureOrderBuffer = new int[boneTextures.size];
		
		for(int i = 0; i < boneTextures.size; i++) {
			textureOrderBuffer[boneTextures.get(i).orderIndex] = i;
		}
	}
	
	@Override
	public void setPosition(Vector2 position) {
		this.position.x = position.x;
		this.position.y = position.y;
		
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
	
	public void setPosition(float x, float y, int boneInxed) {
		Vector2 translation = new Vector2(x,y).sub(bones.get(boneInxed).position);
		
		this.position.add(translation);		
		updateState();
	}
	
	@Override
	public void setPosition(float x, float y) {	
		this.position.x = x;
		this.position.y = y;
		
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
		for(int i = 0; i < bones.size; i++) {
			bones.get(i).angleDeg = angles[i];
		}
		
		updateState();
	}
}