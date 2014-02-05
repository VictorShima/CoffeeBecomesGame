package com.md.mechevo.game;

import java.util.ArrayList;

public class Simulator {
	private Map map;
	private ArrayList<Team> teams;

	public Simulator() {}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public void addTeam(Team t) {
		teams.add(t);
	}
}
