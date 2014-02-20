package com.md.mechevo;

import java.io.InputStream;
import java.util.Scanner;

import com.md.mechevo.game.Report;
import com.md.mechevo.game.Simulator;
import com.md.mechevo.game.State;
import com.md.mechevo.io.Importer;

public class Main {

	static String convertStreamToString(InputStream is) {
		Scanner s = new Scanner(is, "UTF-8").useDelimiter("\\A");
		 return s.hasNext() ? s.next() : "";
	}

	public static void main(String[] args) {
		String content;

		// Read from args if there's any argument
		if (args.length != 0) {
			content = args[0];
		} else {
			// Read the JSON from stdin, more robust since now the JSON can include any spaces
			content = Main.convertStreamToString(System.in);
		}

	State initialState = Importer.createInitialState(content);
	
		//if (args.length != 1) {
		//	System.out.println("Invalid number of arguments: " + args.length);
		//	System.exit(1);
		//}
		//State initialState = Importer.createInitialState(args[0]);
		
		Report report = Simulator.runGame(initialState);
		String reportInJSON = report.exportToJson();

		System.out.println(reportInJSON);
	}
}
