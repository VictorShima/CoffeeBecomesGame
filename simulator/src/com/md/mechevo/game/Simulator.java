package com.md.mechevo.game;

import com.rits.cloning.Cloner;

public class Simulator {
	public static final long TIME_BETWEEN_ROUNDS = 100;

	private Simulator() {

	}

	private static boolean gameHasFinished() {
		// TODO
		return false;
	}

	public static Report runGame(State initialState) {
		Report report = new Report();
		report.addState(initialState);

		Cloner cloner = new Cloner();

		// game loop
		while (!gameHasFinished()) {
			long timeSpentOnUpdate = -System.currentTimeMillis();

			// Deep copy so it doesn't change the previous states
			State previousState = report.getLastState();
			State newState = cloner.deepClone(previousState);
			newState.setTime(TIME_BETWEEN_ROUNDS);
			// TODO problem here - time must be absolute

			Map map = newState.getMap();
			map.update(newState);
			report.addState(newState);

			// add interval between rounds
			try {
				timeSpentOnUpdate += System.currentTimeMillis();
				System.out.format("Time spent on update: %ld ms\n", timeSpentOnUpdate);

				Thread.sleep(TIME_BETWEEN_ROUNDS - timeSpentOnUpdate);
			} catch (InterruptedException e) {
				// Not a problem
			}
		}

		return report;
	}
}
