/*
 * Created by Lukas Papik
 * 28.12.18 17:49.
 */

/*
 * Created by Lukas Papik
 * 28.12.18 17:49.
 */

package svk.opb.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import svk.opg.game.entity.character.skeleton.Skeleton;

public class Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    private Skeleton skeleton;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        skeleton = new Skeleton(100, 100, Gdx.files.internal("resources/characters/player-spritemap-v9.png"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        skeleton.update();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
