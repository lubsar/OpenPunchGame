package svk.opg.game.character.skeletal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import svk.opg.game.object.GameObject;

/**
 * @author Lubomir Hlavko
 */
public class Skelet extends GameObject {
        public static final String ROOT_BONE_NAME = "root";
    
	private Bone root;
        
	//TODO change to standard arrays ?
	public Array<Bone> bones;	
	private ObjectMap<String, Integer> boneIds;
	
	public Array<BoneTextureData> boneTextures;
	protected int[] textureOrderBuffer = null; 

	public Skelet() {
		super();
		
		bones = new Array<Bone>();
		boneIds = new ObjectMap<String, Integer>();
		boneTextures = new Array<BoneTextureData>();
		
		root = new Bone(ROOT_BONE_NAME);
		root.setPosition(position);
		root.setAngle(0);
		bones.add(root);
		
		boneIds.put(root.getName(), 0);
	}
	
	public Bone addBone(String name, String parentBone, float length, float angle) {
		//TODO legacy support, remove later
		Bone parrent = null;
		if(parentBone == null ) {
			parrent = root;
		} else {
			parrent = bones.get(boneIds.get(parentBone));			
		}
		
		Bone bone = new Bone(name, parrent, length, angle);
		
		bones.add(bone);
		boneIds.put(name, bones.size - 1);
		
		parrent.children.add(bone);
                
                return bone;
	}
        
        private void removeTree(Bone bone) {
            bones.removeValue(bone, false);
            boneIds.remove(bone.getName());
            
            for(Bone child : bone.children) {
                removeTree(child);
            }
        }
        
        public void removeBone(String name) {
            if(name.equals(Skelet.ROOT_BONE_NAME)) {
                return;
            }
   
            Bone toRemove = getBone(name);
            
            //remove bone from parrent children
            toRemove.parrent.children.remove(toRemove);
            
            //remove bone and its ancestors
            removeTree(toRemove);
            
            //fix indices
            for(int i = 0; i < bones.size; i++) {
            	boneIds.put(bones.get(i).getName(), i);
            }
        }
	
	public Bone getBone(String name) {
		return bones.get(boneIds.get(name));
	}
	
	public int getBoneIndex(String id) {
		if(boneIds.containsKey(id)) {
			return boneIds.get(id);
		} else {
			return -1;
		}
	}
	
	public String getBoneName(int boneIndex) {
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
	
	protected void prepareTextures() {
		textureOrderBuffer = new int[boneTextures.size];
		
		for(int i = 0; i < boneTextures.size; i++) {
			BoneTextureData texture = boneTextures.get(i);
			
			textureOrderBuffer[texture.orderIndex] = i;
			texture.loadTexture();
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
		root.move(vec);
		
		updateState();
	}
	
	@Override
	public void move(float x, float y) {
		root.move(x, y);
		
		updateState();
	}
	
	@Override
	public void setPosition(float x, float y) {	
		root.setPosition(x, y);
		
		updateState();
	}
	
	public void rotateAroundParrent(int nodeIndex, float angle, boolean lockother) {
		Bone node = bones.get(nodeIndex);
		//TODO reconsider
		if(node.parrent == null) {
			throw new RuntimeException("cant rotate root bone around itself");
		}
		node.rotateArroundParrent(angle, lockother);
	}
	
	public void rotateParentArount(int nodeIndex, float angle) {
		Bone node = bones.get(nodeIndex);
		if(node.parrent == null) {
			throw new RuntimeException("Bone does not have the parent");
		}
		node.rotateParrentAround(angle);
	}
	
	public void setBoneAngle(int boneIndex, float angleDeg) {
		Bone node = bones.get(boneIndex);
		node.setAngle(angleDeg);
	}
	
	public void setPose(Pose pose) {
		for(int i = 0; i < bones.size; i++) {
			bones.get(i).setAngleNoUpdate(pose.angles[i]);
		}
		
		updateState();
	}
	
	public void getPoseData(Pose pose) {
		for(int i = 0; i < bones.size; i++) {
			pose.angles[i] = bones.get(i).getAngle();
		}
	}
}