package com.md.mechevo.game;

import java.util.ArrayList;

public class Map {
	private ArrayList<Solid> elements;

	public Map() {
		elements = new ArrayList<>();
	}

	public void addSolid(Solid s) {
		elements.add(s);
	}

	public void destroySolid(Solid s) {
		elements.remove(s);
	}

	public void update(State state, float elapsedTime) {
		for (Solid s : elements) {
			s.update(state, elapsedTime);
		}
	}
}
