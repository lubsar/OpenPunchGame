package svk.opg.game.character.skeletal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author Lubomir Hlavko
 *
 */
public class SkeletalCharacterRenderer {
	private static final float LINE_WIDTH = 3.0f;
	
	private ShapeRenderer renderer;
	
	public SkeletalCharacterRenderer(ShapeRenderer renderer) {
		this.renderer = renderer;
	}
	
	public void render(Skelet skeleton) {
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		for(Skelet.Bone b:skeleton.bones) {
			if(b.parrent != null) {
				renderer.setColor(Color.BLACK);
				renderer.rectLine(b.position, b.parrent.position, LINE_WIDTH);
			}
			
			if(b.parrent == null) {
				renderer.setColor(Color.RED);
				renderer.circle(b.position.x, b.position.y, 4);
			}
		}
		
		renderer.end();
	}
}
