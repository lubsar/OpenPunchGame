package svk.opg.game.level.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import svk.opg.game.entity.character.skeleton.Skeleton;
import svk.opg.game.entity.character.skeleton.SkeletonRenderer;

public class TestSkeleton extends Skeleton {
	private int neckI;
	
	private int leftArmI;
	private int rightArmI;
	private int leftHandI;
	private int rightHandI;
	
	private int spineI;
	private int leftHipI;
	private int rightHipI;
	
	private int leftLegI;
	private int rightLegI;
	
	private double time = 0.0;
	
	private Sprite head;
	private Sprite hand;
	private Sprite torso;
	private Sprite leg; 
	
	private boolean isTextured = true;
	private boolean pressed;
	
	public TestSkeleton() {
		setPosition(new Vector2(300,300));
		
		neckI = addBone(0, 32, -90);
		leftArmI = addBone(neckI, 25, 0);
		leftHandI = addBone(leftArmI, 100, -45);
		rightArmI = addBone(neckI, 25, 180);
		rightHandI = addBone(rightArmI, 100, 225);
		
		spineI = addBone(neckI, 80, -90);
		leftHipI = addBone(spineI, 22, 0);
		leftLegI = addBone(leftHipI, 80, -90);
		rightHipI = addBone(spineI, 22, 180);
		rightLegI = addBone(rightHipI, 80, -90);
		
		head = new Sprite(new Texture(Gdx.files.internal("test/head.png")));
		hand = new Sprite(new Texture(Gdx.files.internal("test/rightHand.png")));
		torso = new Sprite(new Texture(Gdx.files.internal("test/torso.png")));
		leg = new Sprite(new Texture(Gdx.files.internal("test/rightLeg.png")));
	}
	
	public void update() {
		super.update();
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			if(!pressed) {
				isTextured = !isTextured;
				pressed = true;
			}
		} else {
			pressed = false;
		}
		
		bones.get(leftHandI).rotateArroundParrent((float) Math.sin(time)*4, true);
		bones.get(rightHandI).rotateArroundParrent((float) Math.cos(time*2)*10, true);
		bones.get(leftLegI).rotateArroundParrent((float) Math.sin(time) *3, true);
		bones.get(rightLegI).rotateArroundParrent((float) -Math.sin(time) *3, true);
		bones.get(spineI).rotateArroundParrent((float) -Math.cos(time)*2, true);
		
		move(0,(float) (Math.sin(time)*3));
		
		time += 0.1;
	}
	
	public void render(SkeletonRenderer render, SpriteBatch batch) {
		Bone neck = bones.get(neckI);
		Bone spine = bones.get(spineI);
		
		Bone leftLeg = bones.get(leftLegI);
		Bone righLeg = bones.get(rightLegI);
		
		Bone leftHand = bones.get(leftHandI);
		Bone rightHand = bones.get(rightHandI);
		
		render.render(this);
		
		if(!isTextured) {
			return;
		}
		
		batch.begin();
		
		batch.draw(leg, righLeg.parrent.position.x-15, righLeg.parrent.position.y-80, 15, 80, leg.getWidth(), leg.getHeight(),1,1, righLeg.angleDeg + 95);
		batch.draw(leg, leftLeg.parrent.position.x-15, leftLeg.parrent.position.y-80, 15, 80, leg.getWidth(), leg.getHeight(),-1,1, leftLeg.angleDeg + 85);
		
		batch.draw(hand, rightHand.parrent.position.x-85, rightHand.parrent.position.y-71, 85, 71, hand.getWidth(), hand.getHeight(),1,1, rightHand.angleDeg + 144);
		batch.draw(hand, leftHand.parrent.position.x-85, leftHand.parrent.position.y-71, 85, 71, hand.getWidth(), hand.getHeight(),-1,1, leftHand.angleDeg +35);
		
		batch.draw(torso, spine.parrent.position.x -40, spine.parrent.position.y-87, 40, 87, torso.getWidth(), torso.getHeight(),1,1, spine.angleDeg + 90);	
		batch.draw(head, neck.position.x-27, neck.position.y, 27, 0, head.getWidth(), head.getHeight(), 1,1, neck.angleDeg + 90);	
		
		batch.end();
	}
}
