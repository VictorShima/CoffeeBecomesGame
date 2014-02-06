package com.md.mechevo.game;

import java.util.ArrayList;

/**
 * Holds all the information about the current state of game. Includes the map, units on field,
 * teams, ...
 */
public class State {
	private Map map;
	private ArrayList<Team> teams;
	private float time;

	public State(Map map, ArrayList<Team> teams, float time) {
		this.map = map;
		this.teams = teams;
		this.time = time;
	}

	public Map getMap() {
		return map;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public float getTime() {
		return time;
	}

	/**
	 * 
	 * @param id
	 * @return a player if a player with the id exists in any team, null otherwise
	 */
	public Player findPlayerById(int id) {
		for (Team t : teams) {
			Player p;
			if ((p = t.getPlayer(id)) != null) {
				return p;
			}
		}

		return null;
	}
}
