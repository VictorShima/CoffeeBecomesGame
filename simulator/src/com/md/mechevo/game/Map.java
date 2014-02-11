package com.md.mechevo.game;

import java.util.ArrayList;

public class Map {
	public static final int MIN_WIDTH = 0;
	public static final int MIN_HEIGHT = 0;
	public static final int MAX_WIDTH = 800;
	public static final int MAX_HEIGHT = 800;

	private ArrayList<Solid> elements;

	public Map() {
		elements = new ArrayList<Solid>();
	}

	public void addSolid(Solid s) {
		elements.add(s);
	}

	public ArrayList<Solid> getElements() {
		return elements;
	}

	public void removeSolid(Solid s) {
		elements.remove(s);
	}



	/**
	 * Collision done using center position and radius.
	 */
	private void checkAndCorrectBorderCollision(Solid s) {
		Position pos = s.getPosition();
		float radius = s.getRadius();

		if (pos.getX() - radius < MIN_WIDTH) {
			// moveForward solid center to left corner
			s.setPosition(new Position(radius, pos.getY()));
		}

		if (pos.getX() > MAX_WIDTH) {
			// moveForward solid center to right corner
			s.setPosition(new Position(MAX_WIDTH - radius, pos.getY()));
		}

		if (pos.getY() < MIN_HEIGHT) {
			// moveForward solid center to top corner (hint: inverted y-axis)
			s.setPosition(new Position(pos.getX(), radius));
		}

		if (pos.getY() > MAX_HEIGHT) {
			// moveForward solid center to bottom corner (hint: inverted y-axis)
			s.setPosition(new Position(pos.getX(), MAX_HEIGHT - radius));
		}
	}


	/**
	 * Check Collision between 2 solids
	 * 
	 * TODO: Collisions should really be circles, its difficult to make square collision when
	 * handling angles
	 * 
	 * @param s1 Solid A
	 * @param s2 Solid B
	 * @return True if they collide
	 */
	public boolean checkCollision(Solid s1, Solid s2) {
		return false;
	}



	/**
	 * Updates all elements in the map and resolves all existing collisions after.
	 * 
	 * @param state the current state of the map
	 * @param dtime Delta Time since the last state
	 */
	public void update(State state, float dtime) {
		// update all elements
		for (Solid s : elements) {
			s.update(state, dtime);
		}

		// check collisions with map boundaries
		for (Solid s : elements) {
			this.checkAndCorrectBorderCollision(s);
		}

		// check collisions between all elements
		// if
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
