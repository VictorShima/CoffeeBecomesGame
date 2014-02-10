package com.md.mechevo.game;

public class Simulator {
	/**
	 * Time between rounds is in seconds
	 */
	public static final float TIME_BETWEEN_ROUNDS = 0.1f;

	private Simulator() {

	}

	private static boolean gameHasFinished() {
		// TODO
		return false;
	}

	public static Report runGame(State initialState) {
		Report report = new Report();
		report.update(initialState);

		int i = 0;
		State state = initialState;

		// game loop
		while (!gameHasFinished() && (i++) < 5) {
			state.update(TIME_BETWEEN_ROUNDS);
		}

		return report;
	}
}
