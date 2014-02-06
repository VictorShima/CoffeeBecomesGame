package com.md.mechevo;

import com.md.mechevo.game.Report;
import com.md.mechevo.game.Simulator;
import com.md.mechevo.game.State;
import com.md.mechevo.io.Importer;

public class Main {
	public static void main(String[] args) {
		State initialState = Importer.createInitialState(args);

		Simulator simulator = new Simulator();
		Report report = simulator.runGame(initialState);
		String reportInJSON = report.convertToJSON();

		System.out.println(reportInJSON);
	}
}
