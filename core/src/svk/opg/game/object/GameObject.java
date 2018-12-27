package svk.opg.game.object;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	protected Vector2 position;
	protected Vector2 velocity;
	
	public GameObject() {
		position = new Vector2();
		velocity = new Vector2();
	}
	
//	public abstract void render();
	public abstract void update();
	
	public void setVelocity(Vector2 velocity) {
		this.velocity.set(velocity);
	}
	
	public void addVelocity(Vector2 velocity) {
		velocity.add(velocity);
	}
	
	public void move(Vector2 by) {
		position.add(by);
	}
	
	public void move(float x, float y) {
		position.x += x;
		position.y += y;
	}
	
	public void setPosition(Vector2 newPosition) {
		this.position.set(newPosition);
	}
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	
	public Vector2 getposition() {
		return new Vector2(position.x, position.y);
	}
}
