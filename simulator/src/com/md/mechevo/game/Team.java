package com.md.mechevo.game;

import java.util.ArrayList;

public final class Team {
	private ArrayList<Player> players;

	public Team(ArrayList<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * 
	 * @param id
	 * @return a player if a player with the id exists in the team, null otherwise
	 */
	public Player getPlayer(int id) {
		for (Player p : players) {
			if (p.getID() == id) {
				return p;
			}
		}

		return null;
	}
}
