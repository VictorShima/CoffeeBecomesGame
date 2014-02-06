package com.md.mechevo.game;


public abstract class Solid {
	/**
	 * This is the left-most and top-most coordinate.
	 */
	private Coordinate coordinate;
	private float width;
	private float height;
	private float speed;
	private float angle;

	protected Solid(Coordinate coordinate, float width, float height, float speed, float angle) {
		this.coordinate = coordinate;
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

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}


	/**
	 * @return a coordinate with the center point
	 */
	Coordinate getCenterPoint() {
		return new Coordinate(coordinate.getX() + width / 2, coordinate.getY() + height / 2);
	}

	/**
	 * @return two coordinates where the top-most and left-most coordinate and the bottom-most and
	 *         right-most coordinate.
	 */
	Coordinate[] getBoundingBox() {
		Coordinate[] coordinates = new Coordinate[2];
		coordinates[0] = this.getCoordinate();
		coordinates[1] = new Coordinate(coordinate.getX() + width, coordinate.getY() + height);

		return coordinates;
	}

	/**
	 * Implements the visitor design pattern to enable new operations on solids.
	 * 
	 * @param s the visitor
	 */
	public abstract void accept(CollisionVisitor s);

	Coordinate nextPosition(float time) {
		float x = (float) Math.cos(getAngle()) * getSpeed() * time;
		float y = (float) Math.sin(getAngle()) * getSpeed() * time;
		return new Coordinate(x, y);
	}
}
