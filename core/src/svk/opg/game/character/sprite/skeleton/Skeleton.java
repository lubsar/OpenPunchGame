package svk.opg.game.character.sprite.skeleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import svk.opg.game.Input;
import svk.opg.game.object.GameObject;

/**
 * @author Lukas Papik
 *
 */
public class Skeleton extends GameObject {

	public Sprite sprite;
	SpriteBatch spriteBatch;
	float stateTime;
	private Input input;

	public Skeleton(int x, int y, FileHandle sprite) {

		input = new Input(sprite);
		Gdx.input.setInputProcessor(input);

		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}


	@Override
	public void update() {
		stateTime += Gdx.graphics.getDeltaTime();
		//spriteBatch.begin();
		//sprite.draw(spriteBatch);
		//	spriteBatch.end();
		input.update(true);
	}
}