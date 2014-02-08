package com.md.mechevo.game;


import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public abstract class Solid implements CollisionVisitor {
	/**
	 * This is the left-most and top-most position.
	 */
	private Position position;
	private float width;
	private float height;
	private float speed;
	private float angle;
	private boolean destroy;
	private int id;

	protected Solid(float width, float height, float speed, float angle, int id) {
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.angle = angle;
		this.destroy = false;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void rotateBy(float angle) {
		this.angle += angle;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	/**
	 * @return a position with the center point
	 */
	Position getCenterPoint() {
		return new Position(position.getX() + width / 2, position.getY() + height / 2);
	}

	/**
	 * @return two coordinates where the top-most and left-most position and the bottom-most and
	 *         right-most position.
	 */
	Position[] getBoundingBox() {
		Position[] positions = new Position[2];
		positions[0] = this.getPosition();
		positions[1] = new Position(position.getX() + width, position.getY() + height);

		return positions;
	}

	/**
	 * Implements the visitor design pattern to enable new operations on solids.
	 * 
	 * @param s the visitor
	 * @param state the state after moving
	 */
	public abstract void accept(CollisionVisitor s, State state);


	/**
	 * Default case for updating position. Used in everything that isn't homing.
	 * 
	 * @param state
	 */
	public void update(State state) {
		float velX = (float) Math.cos(angle);
		float velY = (float) Math.sin(angle);
		/* Normalization of the velocity vector */
		float vel = velX + velY;
		velX = (velX / vel) * state.getTime() * (speed / 1000);
		velY = -(velY / vel) * state.getTime() * (speed / 1000);

		this.setPosition(new Position(this.getPosition().getX() + velX, this.getPosition().getY()
						+ velY));
	}

	@Override
	public abstract void collidesWith(State state, Player p);

	@Override
	public abstract void collidesWith(State state, Projectile p);

	@Override
	public abstract void collidesWith(State State, Obstacle o);

	@Override
	public abstract void collidesWith(State state, Sentry s);
}
