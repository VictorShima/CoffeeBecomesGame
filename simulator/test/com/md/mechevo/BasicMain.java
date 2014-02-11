package com.md.mechevo;

import com.md.mechevo.game.*;

/**
 * In this example the mechas use the default moveForward (provided by the Solid class) in each round.
 */
public class BasicMain {
	public static void main(String[] args) {
		// create players
		Player p0 = new Player(0, 0);
		Player p1 = new Player(1, 1);
		p0.setPosition(new Position(50, 50));
		p1.setPosition(new Position(500, 500));

		// create map
		Map map = new Map();
		map.addSolid(p0);
		map.addSolid(p1);

		// create state
		State initialState = new State();
		initialState.setMap(map);
		initialState.addPlayer(p0);
		initialState.addPlayer(p1);

		Report report = Simulator.runGame(initialState);
		System.out.println(report.exportToJson());
	}
}
