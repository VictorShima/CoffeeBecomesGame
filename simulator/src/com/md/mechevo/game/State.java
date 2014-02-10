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
	 * total time passed since beginning
	 */
	private float totalTime;


	public State() {
		this.players = new ArrayList<Player>();
		this.projectiles = new ArrayList<Projectile>();
		this.totalTime = 0;
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
		this.projectiles.add(p);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player p) {
		this.players.add(p);
	}

	public float getTotalTime() {
		return totalTime;
	}
	
	
	/**
	 * Update the current state to the next one
	 *
	 * @param time Time since the last state
	 */
	public void update(float dtime) {
		this.map.update(this, dtime);
		this.totalTime += dtime;
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
