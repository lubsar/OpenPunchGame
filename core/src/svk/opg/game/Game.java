package svk.opg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import svk.opg.game.character.sprite.skeleton.Skeleton;

/**
 * @author Lubomir Hlavko, Lukas Papik
 *
 */
public class Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    private Skeleton skeleton;

//    private TestSkeletal lubsarTest;
    
    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        skeleton = new Skeleton(100, 100, Gdx.files.internal("core/assets/characters/sprite/player-spritemap-v9.png"));
        
//      lubsarTest = new TestSkeletal();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        skeleton.update();
        
//      lubsarTest.render();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        
//      lubsarTest.dispose();
    }
}
