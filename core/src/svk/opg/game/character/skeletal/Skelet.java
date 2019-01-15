package svk.opg.game.character.skeletal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import svk.opg.game.object.GameObject;

/**
 * @author Lubomir Hlavko
 */
public class Skelet extends GameObject {
	private Bone root;
	
	//TODO change to java arrays ?
	public Array<Bone> bones;	
	public ObjectMap<String, Integer> boneIds;
	
	public Array<BoneTextureData> boneTextures;
	protected int[] textureOrderBuffer = null; 

	public Skelet() {
		super();
		
		bones = new Array<Bone>();
		boneIds = new ObjectMap<String, Integer>();
		boneTextures = new Array<BoneTextureData>();
		
		root = new Bone();
		root.position = this.position;
		root.angleDeg = 0;
		bones.add(root);
	}
	
	public int addBone(int parrentBoneIndex, float length, float angle, String boneid) {
		Bone bone = new Bone();
		Bone parrent = bones.get(parrentBoneIndex);
		
		bone.length = length;
		bone.angleDeg = angle;
		bone.parrent = parrent;
		bone.updatePosition();
		
		bones.add(bone);
		
		parrent.children.add(bone);
		
		if(boneid != null) {
			boneIds.put(boneid, bones.size - 1);
		}
		
		return bones.size -1;
	}
	
	public Bone getBone(int index) {
		return bones.get(index);
	}
	
	public void addBoneIdentification(String id, int boneIndex) {
		//TODO handle case when id is already assigned
		boneIds.put(id, boneIndex);
	}
	
	public int getBoneIndex(String id) {
		if(boneIds.containsKey(id)) {
			return boneIds.get(id);
		} else {
			return -1;
		}
	}
	
	public String getBoneId(int boneIndex) {
		return boneIds.findKey(boneIndex, true);
	}
	
	public void addTexture(BoneTextureData data) {
		boneTextures.add(data);
	}
	
	@Override
	public void update() {
		updateState();
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