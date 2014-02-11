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
	 * @todo : collision should be with entire box, right now its only with pivot point
	 */
	private void checkAndCorrectBorderCollision(Solid s) {
		Position solidCenterPos = s.getCenterPoint();
		Position[] solidPos = s.getBoundingBox();
		assert solidPos.length == 2 : "Solid.getBoundingBox doesn't return a 2-element vector";

		if (solidPos[0].getX() < MIN_WIDTH) {
			// move solid center to left corner
			s.setPosition(new Position(s.getWidth() / 2, solidCenterPos.getY()));
		}

		if (solidPos[1].getX() > MAX_WIDTH) {
			// move solid center to right corner
			s.setPosition(new Position(MAX_WIDTH - s.getWidth() / 2, solidCenterPos.getY()));
		}

		if (solidPos[0].getY() < MIN_HEIGHT) {
			// move solid center to top corner (hint: inverted y-axis)
			s.setPosition(new Position(solidCenterPos.getX(), s.getHeight() / 2));
		}

		if (solidPos[1].getY() > MAX_HEIGHT) {
			// move solid center to bottom corner (hint: inverted y-axis)
			s.setPosition(new Position(solidCenterPos.getX(), MAX_HEIGHT - s.getHeight() / 2));
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
	 *	@return True if they collide
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
		for (int i = 0; i < elements.size(); i++) {
			for (int j = i; j < elements.size(); j++) {
				if (this.checkCollision(this.elements.get(i), this.elements.get(j))) {
					elements.get(i).accept(elements.get(j), state);
					elements.get(j).accept(elements.get(i), state);
				}
			}
		}

	}
}
