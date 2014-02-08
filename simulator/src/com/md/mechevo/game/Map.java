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

	public void destroySolid(Solid s) {
		elements.remove(s);
	}

	private void correctIfCollision(Solid s) {
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
	 * Updates all elements in the map and resolves all existing collisions after.
	 * 
	 * @param state the current state of the map
	 */
	public void update(State state) {
		// update all elements
		for (Solid s : elements) {
			s.update(state);
		}

		// check collisions with map
		for (Solid s : elements) {
			correctIfCollision(s);
		}

		// check collisions for all elements
		for (int i = 0; i < elements.size(); i++) {
			for (int j = i; j < elements.size(); j++) {
				elements.get(i).accept(elements.get(j), state);
				elements.get(j).accept(elements.get(i), state);
			}
		}

	}
}
