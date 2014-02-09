package com.md.mechevo.game;

/**
 * A coordinate represents a 2D position that is composed by a x and y positions.
 */
public final class Position {
	private float x;
	private float y;

	public Position(float x, float y) {
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

	public boolean equals(Object o) {
		if (o instanceof Position) {
			Position p = (Position) o;
			return this.x == p.x && this.y == p.y;
		}
		return false;
	}
}
