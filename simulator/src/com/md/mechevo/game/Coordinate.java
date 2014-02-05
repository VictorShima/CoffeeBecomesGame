package com.md.mechevo.game;

/**
 * A coordinate represents a 2D position that is composed by a x and y positions.
 */
public final class Coordinate {
	private float x;
	private float y;

	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;

	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
