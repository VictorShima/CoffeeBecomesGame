package com.md.mechevo;

import com.md.mechevo.game.Report;
import com.md.mechevo.game.Simulator;
import com.md.mechevo.game.State;
import com.md.mechevo.io.Importer;

public class Main {
	public static void main(String[] args) {
		State initialState = Importer.createInitialState(args);

		Report report = Simulator.runGame(initialState);
		String reportInJSON = report.print();

		System.out.println(reportInJSON);
	}
}
