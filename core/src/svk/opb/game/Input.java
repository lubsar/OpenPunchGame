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
 * 28.12.18 15:47.
 */

package svk.opb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import svk.opg.game.entity.character.skeleton.SkeletonAnimation;

public class Input implements InputProcessor {

    boolean space = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;
    private FileHandle sprite;
    private Sprite skeleton;
    private SkeletonAnimation skeletonAnimation;
    private int speed = 300;

    public Input(FileHandle sprite) {
        this.sprite = sprite;
        this.skeleton = new Sprite(new Texture(sprite));
        this.skeletonAnimation = new SkeletonAnimation(new Texture(sprite), 8, 4, 2, 4, 4, 8, 0.070f, 7);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.SPACE:
                space = true;
                break;
            case com.badlogic.gdx.Input.Keys.A:
                left = true;
                break;
            case com.badlogic.gdx.Input.Keys.S:
                down = true;
                break;
            case com.badlogic.gdx.Input.Keys.D:
                right = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.SPACE:
                space = false;
                break;
            case com.badlogic.gdx.Input.Keys.A:
                left = false;
                break;
            case com.badlogic.gdx.Input.Keys.S:
                down = false;
                break;
            case com.badlogic.gdx.Input.Keys.D:
                right = false;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void update(boolean debug) {

        if (space) {
            skeletonAnimation.jumpAnimation(true);
        } else if (left && !space) {
            skeleton.translateX(-speed * Gdx.graphics.getDeltaTime());
            skeletonAnimation.runAnimation(true);

        } else if (right && !space) {
            skeleton.translateX(speed * Gdx.graphics.getDeltaTime());
            skeletonAnimation.runAnimation(false);
        } else {
            skeletonAnimation.idleAnimation();
        }

        skeletonAnimation.update((int) skeleton.getX(), (int) skeleton.getY());
    }
}
