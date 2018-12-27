package svk.opg.game.entity.character.skeleton;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SkeletonRenderer {
	private static final float LINE_WIDTH = 3.0f;
	
	private ShapeRenderer renderer;
	
	public SkeletonRenderer(ShapeRenderer renderer) {
		this.renderer = renderer;
	}
	
	public void render(Skeleton skeleton) {
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.WHITE);
		for(Skeleton.Bone b:skeleton.bones) {
			if(b.parrent != null) {
				renderer.rectLine(b.position, b.parrent.position, LINE_WIDTH);
			}
		}
		
		renderer.end();
	}
}
