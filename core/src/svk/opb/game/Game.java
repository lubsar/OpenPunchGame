package svk.opb.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import svk.opg.game.entity.character.skeleton.Skeleton;
import svk.opg.game.entity.character.skeleton.SkeletonRenderer;
import svk.opg.game.level.entity.TestSkeleton;

public class Game extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;

	private Skeleton skeleton = new Skeleton();
	private SkeletonRenderer skeletalRenderer;
	
	private SpriteBatch batch;
	private TestSkeleton testSkeleton;
	
	@Override
	public void create () {
		shapeRenderer  = new ShapeRenderer();
		skeletalRenderer = new SkeletonRenderer(shapeRenderer);
		
		int chest = skeleton.addBone(0, 20, -90);
		int hip = skeleton.addBone(chest, 70, -90);
		skeleton.addBone(chest, 40, -45);
		skeleton.addBone(chest, 40, -135);
		int rightLeg = skeleton.addBone(hip, 20, -45);
		int leftLeg = skeleton.addBone(hip, 20, -135);
		skeleton.addBone(rightLeg, 30, -90);
		skeleton.addBone(leftLeg, 30, -90);

		skeleton.setPosition(new Vector2(300,300));
		
		batch = new SpriteBatch(10);
		testSkeleton = new TestSkeleton();
	}
	
	double time = 0.0;
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
		testSkeleton.update();
		testSkeleton.render(skeletalRenderer, batch);
		time += 0.1;
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
