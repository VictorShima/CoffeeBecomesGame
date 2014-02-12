package com.md.mechevo.game;

/**
 * A coordinate represents a 2D position that is composed by a x and y positions.
 */
public final class Position {
	private double x;
	private double y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;

	}

	public double getX() {
		return x;
	}

	public double getY() {
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
