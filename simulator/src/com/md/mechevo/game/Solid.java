package com.md.mechevo.game;


public abstract class Solid {
	/**
	 * This is the left-most and top-most position.
	 */
	private Position position;
	private float width;
	private float height;
	private float speed;
	private float angle;

	protected Solid(Position position, float width, float height, float speed, float angle) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.angle = angle;
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

	Position nextPosition(float time) {
		float x = (float) Math.cos(getAngle()) * getSpeed() * time;
		float y = (float) Math.sin(getAngle()) * getSpeed() * time;
		return new Position(x, y);
	}
}
