package com.md.mechevo.game;

import java.util.ArrayList;

public class Simulator {
	private Map map;
	private ArrayList<Team> teams;

	public Simulator() {}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	private boolean gameHasFinished() {
		// TODO
		return false;
	}

	public Report runGame(State initialState) {
		Report report = new Report();
		report.addState(initialState);

		// initialize variables with the initial state
		this.map = initialState.getMap();
		this.teams = initialState.getTeams();

		// game loop
		while (!gameHasFinished()) {
			State lastState = report.getLastState();
			float time = System.currentTimeMillis();

			map.update(lastState, time - lastState.getTime());
			// State currentState = (Map) map.clone();
			// report.addState(currentState);
		}

		return report;
	}
}
