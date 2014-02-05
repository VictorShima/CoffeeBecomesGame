package com.md.mechevo.game;

import java.util.ArrayList;

public class Map {
	private ArrayList<Obstacle> obstacles;

	public Map(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public void addObstacle(Obstacle o) {
		obstacles.add(o);
	}

	public void destroyObstacle(Obstacle o) {
		obstacles.remove(o);
	}
}
