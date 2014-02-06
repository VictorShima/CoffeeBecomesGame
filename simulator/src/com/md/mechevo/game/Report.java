package com.md.mechevo.game;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Report {
	private LinkedList<State> gameState;

	public Report() {}

	public void addState(State s) {
		gameState.add(s);
	}

	public State getLastState() {
		return gameState.getLast();
	}

	/**
	 * 
	 * @return this report in the JSON format
	 */
	public String convertToJSON() {
		Gson gson = new Gson();
		Type type = new TypeToken<Collection<State>>() {}.getType();
		return gson.toJson(gameState, type);
	}
}
