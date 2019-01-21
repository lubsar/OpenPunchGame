package svk.opg.game.character.skeletal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
				renderer.setColor(Color.BLACK);
				renderer.rectLine(b.position, b.parrent.position, LINE_WIDTH);
				renderer.setColor(Color.GREEN);
				renderer.rect(b.position.x, b.position.y, 1,1);
			}
			
			if(b.parrent == null) {
				renderer.setColor(Color.RED);
				renderer.circle(b.position.x, b.position.y, 4);
			}
		}
		
		renderer.end();
	}
	
	public void renderTextures(Skelet skeleton) {
		if(skeleton.textureOrderBuffer == null) {
			skeleton.createTextureOrderBuffer();
		}
		
		batch.begin();
		
		for(int i : skeleton.textureOrderBuffer) {
			BoneTextureData text = skeleton.boneTextures.get(i);
			Bone bone = skeleton.bones.get(text.boneIndex);
			batch.draw(text.sprite, bone.parrent.position.x - text.xMappingOffset, bone.parrent.position.y - text.yMappingOffset, text.xMappingOffset, text.yMappingOffset, text.sprite.getWidth(), text.sprite.getHeight(), text.flip ? -1 : 1, 1, bone.angleDeg + text.angleOffset);
		}
		
		batch.end();
	}
	
	public void render(Skelet skeleton) {
		renderSkelet(skeleton);
		renderTextures(skeleton);
	}
}
