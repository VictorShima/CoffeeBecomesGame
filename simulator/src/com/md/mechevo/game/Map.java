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

    public boolean canSolidMove(Solid s, double angle, boolean forward) {
        // TODO
        return true;
    }

	/**
	 * Check collision between 2 solids
	 * 
	 * @param s1 Solid A
	 * @param s2 Solid B
	 * @return True if they collide
	 */
	public boolean checkCollision(Solid s1, Solid s2) {
		// TODO to implement
		return false;
	}

	/**
	 * Updates all elements in the map and resolves all existing collisions after.
	 * 
	 * @param state the current state of the map
	 * @param dtime Delta Time since the last state
	 */
	public void update(State state, double dtime) {
		// update all elements
		for (Solid s : elements) {
			s.update(state, dtime);
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

	}
}
