package svk.opg.game.character.skeletal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Lubomir Hlavko
 *
 */
public class SkeletalCharacterRenderer {
	private static final float LINE_WIDTH = 3.0f;
	
	private ShapeRenderer renderer;
	private Batch batch;
	
	public SkeletalCharacterRenderer(ShapeRenderer renderer, Batch batch) {
		this.renderer = renderer;
		this.batch = batch;
	}
	
	public void renderSkelet(Skelet skeleton) {
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		for(Bone b:skeleton.bones) {
			if(b.parrent != null) {
				Vector2 bonePosition = b.getPosition();
				Vector2 parrentPosition = b.parrent.getPosition();
				
				renderer.setColor(Color.BLACK);
				renderer.rectLine(bonePosition, parrentPosition, LINE_WIDTH);
				renderer.setColor(Color.GREEN);
				renderer.rect(bonePosition.x, bonePosition.y, 1,1);
			
				renderer.setColor(Color.RED);
				renderer.circle(parrentPosition.x, parrentPosition.y, 4);
			}
		}
		
		renderer.end();
	}
	
	public void renderTextures(Skelet skeleton) {
		if(skeleton.textureOrderBuffer == null) {
			skeleton.prepareTextures();
		}
		
		batch.begin();
		
		for(int i : skeleton.textureOrderBuffer) {
			BoneTextureData text = skeleton.boneTextures.get(i);
			Bone bone = skeleton.getBone(text.boneName);
			
			Vector2 parrentPosition = bone.parrent.getPosition();
			
			batch.draw(text.sprite, parrentPosition.x - text.xMappingOffset, parrentPosition.y - text.yMappingOffset, text.xMappingOffset, text.yMappingOffset, text.sprite.getWidth(), text.sprite.getHeight(), text.flip ? -1 : 1, 1, bone.getAngle() + text.angleOffset);
		}
		
		batch.end();
	}
	
	public void render(Skelet skeleton) {
		renderSkelet(skeleton);
		renderTextures(skeleton);
	}
}
