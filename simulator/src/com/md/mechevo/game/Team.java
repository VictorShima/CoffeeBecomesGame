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

	public void play(float time) {
		for (Player p : players) {
			p.play(time);
		}
	}
}
