package com.md.mechevo.game;

import java.util.ArrayList;

public class Map {
	public static final double MIN_WIDTH = 0;
	public static final double MIN_HEIGHT = 0;
	private double width = 800;
	private double height = 800;

	private ArrayList<Solid> elements;

	public Map(double width, double height) {
		this.elements = new ArrayList<>();
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public ArrayList<Solid> getElements() {
		return elements;
	}

	public void addSolid(Solid s) {
		elements.add(s);
	}

	/**
	 * Collision done using center position and radius.
	 */
	private void checkAndCorrectBorderCollision(Solid s) {
		Position pos = s.getPosition();
		double radius = s.getRadius();

		if (pos.getX() - radius < MIN_WIDTH) {
			// moveForward solid center to left corner
			s.setPosition(new Position(radius, pos.getY()));
		}

		if (pos.getX() > width) {
			// moveForward solid center to right corner
			s.setPosition(new Position(width - radius, pos.getY()));
		}

		if (pos.getY() < MIN_HEIGHT) {
			// moveForward solid center to top corner (hint: inverted y-axis)
			s.setPosition(new Position(pos.getX(), radius));
		}

		if (pos.getY() > height) {
			// moveForward solid center to bottom corner (hint: inverted y-axis)
			s.setPosition(new Position(pos.getX(), height - radius));
		}
	}

	/**
	 * Check collision between 2 solids
	 * 
	 * @param s1 Solid A
	 * @param s2 Solid B
	 * @return True if they collide
	 */
	private static boolean checkCollision(Solid s1, Solid s2) {
		return getDistance(s1.getPosition(), s2.getPosition()) < (s1.getRadius() + s2.getRadius());
	}

	/**
	 * @param p1 the first position
	 * @param p2 the second position
	 * @return the euclidean distance between the two positions.
	 */
	public static double getDistance(Position p1, Position p2) {
		double distX = p1.getX() - p2.getX();
		double distY = p1.getY() - p2.getY();
		return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
	}

	/**
	 * Updates all elements in the map and resolves all existing collisions after.
	 * 
	 * @param state the current state of the map
	 * @param dtime Delta Time since the last state
	 */
	public void update(State state, double dtime) {
		// update all elements
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).update(state, dtime);
		}

		// check collisions with map boundaries
		for (Solid s : elements) {
			this.checkAndCorrectBorderCollision(s);
		}

		// check collisions between all elements
		for (int i = 0; i < elements.size(); i++) {
			for (int j = 0; j < elements.size(); j++) {
				if (i == j) {
					continue;
				}

				if (this.checkCollision(this.elements.get(i), this.elements.get(j))) {
					elements.get(i).accept(elements.get(j), state);
				}
			}
		}

		// remove elements that are dead
		for (int i = 0; i < elements.size();) {
			if (elements.get(i).isDestroyed()) {
				elements.get(i).end(state);
				elements.remove(i);
			} else {
				i += 1;
			}
		}
	}

	/**
	 *
	 * @param s1 the first solid
	 * @param s2 the solid target where s1 wants to turn to
	 * @return the angle between the s1 and the s2
	 */
	public static double getAngleToTarget(Solid s1, Solid s2) {
		// If s2 is null then return the player's angle
		if (s2 != null) {
			// rightPoint is a point always 20px to the absolute right of the player
			double rightPointX = s1.getPosition().getX() + 20;
			double rightPointY = s1.getPosition().getY();

			double distBA =
					Math.sqrt(Math.pow((s1.getPosition().getX() - s2.getPosition().getX()), 2)
							+ (Math.pow((s1.getPosition().getY() - s2.getPosition().getY()), 2)));
			double distBC =
					Math.sqrt(Math.pow((s1.getPosition().getX() - rightPointX), 2)
							+ (Math.pow((s1.getPosition().getY() - rightPointY), 2)));
			double dotProd =
					((rightPointX - s1.getPosition().getX())
							* (s2.getPosition().getX() - s1.getPosition().getX()) + (rightPointY - s1
							.getPosition().getY())
							* (s2.getPosition().getY() - s1.getPosition().getY()));
			double cosValue = (dotProd / (distBA * distBC));
			double angle = Math.toDegrees(Math.acos(cosValue));

			// Rotate clockwise/counter-clockwise is determined by sign of cross-product
			double crossProd =
					((rightPointX - s1.getPosition().getX())
							* (s2.getPosition().getY() - s1.getPosition().getY()) - (rightPointY - s1
							.getPosition().getY())
							* (s2.getPosition().getX() - s1.getPosition().getX()));
			// Change signal when cross product is positive
			if (crossProd > 0) {
				angle *= -1;
			}

			return (angle + 360) % 360;

		} else {
			return s1.getAngle();
		}
	}
}
