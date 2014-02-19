package com.md.mechevo;

import java.io.InputStream;
import java.util.Scanner;

import com.md.mechevo.game.Report;
import com.md.mechevo.game.Simulator;
import com.md.mechevo.game.State;
import com.md.mechevo.io.Importer;

public class Main {

	static String convertStreamToString(java.io.InputStream is) {
		 java.util.Scanner s = new java.util.Scanner(is,"UTF-8").useDelimiter("\\A");
		 return s.hasNext() ? s.next() : "";
	}

	public static void main(String[] args) {
	
		// Read the JSON from stdin, more robust since now the JSON can include any spaces
		String content = Main.convertStreamToString(System.in);
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
