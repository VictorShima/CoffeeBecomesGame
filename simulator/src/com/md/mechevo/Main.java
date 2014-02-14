package com.md.mechevo;

import com.md.mechevo.game.*;
import com.md.mechevo.io.Importer;

public class Main {

	public static void main(String[] args) {
		if (args.length != 1) {
            System.out.println("Invalid number of arguments: " + args.length);
            System.exit(1);
        }

		State initialState = Importer.createInitialState(args[0]);
		Report report = Simulator.runGame(initialState);
		String reportInJSON = report.exportToJson();

        System.out.println(reportInJSON);
	}
}
