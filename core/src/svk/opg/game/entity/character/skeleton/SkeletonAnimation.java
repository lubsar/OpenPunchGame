
/*
 * Created by Lukas Papik
 * 28.12.18 17:49.
 */

/*
 * Created by Lukas Papik
 * 28.12.18 17:49.
 */

/*
 * Created by Lukas Papik
 * 28.12.18 13:00.
 */

package svk.opg.game.entity.character.skeleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SkeletonAnimation {
	private int frameCols, frameRows;

	private Animation<TextureRegion> run;
	private Animation<TextureRegion> jump;
	private Animation<TextureRegion> idle;

	private Texture sheet;
	private SpriteBatch spriteBatch;
	private TextureRegion[][] textureRegions;
	private float stateTime;
	private float scale = 1;

	private int x;
	private int y;

	public SkeletonAnimation() {
		this.sheet = new Texture(Gdx.files.internal("resources/characters/player-spritemap-v9.png"));
		textureRegions = TextureRegion.split(sheet,
				sheet.getWidth() / 8,
				sheet.getHeight() / 4);

		run = new Animation<TextureRegion>(0.085f, animation(4, 8));
		jump = new Animation<TextureRegion>(0.085f, animation(4, 8));
		idle = new Animation<TextureRegion>(0.085f, animation(4, 8));

		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	public SkeletonAnimation(Texture spritemap, int spriteCols, int spriteRows, int iR, int iC, int wR, int wC, float duration, float scale) {
		this.sheet = spritemap;
		this.frameCols = spriteCols;
		this.frameRows = spriteRows;
		this.scale = scale;

		textureRegions = TextureRegion.split(sheet,
				sheet.getWidth() / frameCols,
				sheet.getHeight() / frameRows);

		run = new Animation<TextureRegion>(duration, animation(wR, wC));
		jump = new Animation<TextureRegion>(duration * 2, animation(iR, iC));
		idle = new Animation<TextureRegion>(0.08f, animation(1, 1));

		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	public void update(int x, int y) {
		this.x = x;
		this.y = y;
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		//runAnimation(false);
	}

	public TextureRegion[] animation(int r, int c) {
		TextureRegion[] animation = new TextureRegion[1 * c];
		for (int i = 0; i < c; i++) {
			animation[i] = textureRegions[r - 1][i];
		}
		return animation;
	}

	public void runAnimation(boolean flip) {
		spriteBatch.begin();
		TextureRegion currentFrame = run.getKeyFrame(stateTime, true);
		spriteBatch.draw(currentFrame, flip ? x + currentFrame.getRegionWidth() : x, y, 0, 0, flip ? -currentFrame.getRegionWidth() : currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), scale, scale, 0);
		spriteBatch.end();
	}

	public void jumpAnimation(boolean flip) {
		spriteBatch.begin();
		TextureRegion currentFrame = jump.getKeyFrame(stateTime, true);
		spriteBatch.draw(currentFrame, flip ? x + currentFrame.getRegionWidth() : x, y + 50, 0, 0, flip ? -currentFrame.getRegionWidth() : currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), scale, scale, 0);
		spriteBatch.end();
	}

	public void idleAnimation() {
		spriteBatch.begin();
		TextureRegion currentFrame = idle.getKeyFrame(stateTime, true);
		spriteBatch.draw(currentFrame, x, y, 0, 0, currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), scale, scale, 0);
		spriteBatch.end();
	}


}
