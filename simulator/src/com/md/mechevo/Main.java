package com.md.mechevo;

import com.md.mechevo.game.Report;
import com.md.mechevo.game.Simulator;
import com.md.mechevo.game.State;
import com.md.mechevo.io.Importer;

// debug
import com.google.gson.Gson;
import com.md.mechevo.game.*;

public class Main {

	public static void main(String[] args) {
		Main.testGaySON();
		if (true) return;
	
		State initialState = Importer.createInitialState(args);

		Report report = Simulator.runGame(initialState);
		String reportInJSON = report.exportToJson();

		System.out.println(reportInJSON);
	}
	
	
	public static void testGaySON() {
		Gson gson = new Gson();
		EventObserver report = new EventObserver();
		
		EventData event = new EventData("TestA")
				.addAttribute("playerId", 4)
				.addAttribute("action", "move");
		report.notify(event);
		
		event = new EventData("TestB")
				.addAttribute("playerId", 4)
				.addAttribute("action", "attack");
		report.setCurrentTime(0.3f);
		report.notify(event);
		
		System.out.println( report.generateReport() );
		System.out.println("===END OF SIMULATOR===");
		
	}
	
	
	public static void testPlay() {
		// importer should later do all this
		// initialize required game values
		State initialState = new State();
		initialState.registerEventObserver(new EventObserver());
		
		// create and append map
		
		// create and append players
		
		
	}
	
	
}
