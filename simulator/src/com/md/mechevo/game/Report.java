package com.md.mechevo.game;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * A report contains a the entire log of the simulation plus some functions to export it
 */
public class Report {

	private JsonElement log;

	public Report(JsonElement log) {
		this.log = log;
	}
	
	public String exportToJson() {
		Gson gson = new Gson();
		return gson.toJson(log);
	}

}
