package com.md.mechevo.game;

import java.util.ArrayList;

import com.md.mechevo.game.projectile.Projectile;

/**
 * Holds all the information about the current state of game. Includes the map, units on field,
 * teams, ...
 */
public class State {
	private Map map;
	private ArrayList<Player> players;
	private ArrayList<Projectile> projectiles;

	/**
	 * time passed since the last state. time is in milliseconds.
	 */
	private float time;

	public State() {
		this.players = new ArrayList<Player>();
		this.projectiles = new ArrayList<Projectile>();
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player p) {
        players.add(p);
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	/**
	 * 
	 * @param id the player id
	 * @return a player if a player with the id exists in any team, null otherwise
	 */
	public Player findPlayerById(int id) {
		for (Player player : this.players) {
			if (player.getId() == id) {
				return player;
			}
		}

		return null;
	}
}
