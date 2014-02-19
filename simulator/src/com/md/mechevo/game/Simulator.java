package com.md.mechevo.game;

import com.google.gson.JsonObject;

public class Simulator {
	/**
	 * Time between rounds is in seconds
	 */
	public static final double TIME_BETWEEN_ROUNDS = 0.1f;
	
	/**
	 * Time limit to abort the simulation (in seconds)
	 */
	public static final double TIMELIMIT = 10;

	private Simulator() {}

	/**
	 * Run the game until end.
	 * 
	 * @param state The initial state to begin with
	 */
	public static Report runGame(State state) {
	
		double timeElapsed = 0;
		// game loop
		while (!state.gameHasFinished() && timeElapsed <= Simulator.TIMELIMIT) {
			state.update(TIME_BETWEEN_ROUNDS);
			timeElapsed += TIME_BETWEEN_ROUNDS;
		}
		state.end();

		return Simulator.buildFullReport(state);
	}


	/**
	 * Build the report. Get the events from the state and add some additional info to it.
	 * TODO: perhaps this should be inside the State
	 * 
	 * @param state State of the game (usually the final state)
	 * @return Json Object that defines the full report
	 */
	private static Report buildFullReport(State state) {
		JsonObject head = new JsonObject();
		head.addProperty("totalTime", state.getTotalTime());
		head.addProperty("mapWidth", state.getMap().getWidth());
		head.addProperty("mapHeight", state.getMap().getHeight());
		head.add("events", state.buildEventReport());
		return new Report(head);
	}



}
