package com.md.mechevo.game;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.md.mechevo.game.projectile.Projectile;

/**
 * Holds all the information about the current state of game. Includes the map, units on field,
 * teams, ...
 */
public class State implements EventObservable {
	private Map map;

	/**
	 * The list of players in the game. Can belong to any team.
	 */
	private ArrayList<Player> players;

	/**
	 * The list of projectiles in the game. All the projectiles are here.
	 */
	private ArrayList<Projectile> projectiles;

	/**
	 * The id generator.
	 */
	private int nextId;

	/**
	 * Total time passed since beginning.
	 */
	private double totalTime;

	/**
	 * Variable that holds the EventObserver, later will be converted to report
	 */
	private EventObserver report;

	public State(Map map) {
		this.map = map;
		this.players = new ArrayList<>();
		this.projectiles = new ArrayList<>();
		this.totalTime = 0;
		this.nextId = 0;
	}

	public int getNextId() {
		return nextId++;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void addProjectile(Projectile p) {
		this.projectiles.add(p);
		map.addSolid(p);
	}

      public void addPlayer(Player p) {
		this.players.add(p);
		this.map.addSolid(p);
	}


	public void addObstacle(Obstacle o) {
		this.map.addSolid(o);
	}

    public Map getMap() {
        return map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public double getTotalTime() {
		return totalTime;
	}

	public EventObserver getReport() {
		return report;
	}

	/**
	 * Update the current state to the next one
	 * 
	 * @param dtime Time since the last state
	 */
	public void update(double dtime) {
		this.report.setCurrentTime(this.totalTime);
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



	/**
	 * Build the event report. Get the events from the EventObserver
	 */
	public JsonElement buildEventReport() {
		if (this.report != null) {
			return this.report.generateReport();
		}
		return new JsonArray();
	}


	// interface EventObservable

	/**
	 * This registerEventObserver will try to recursively set the event observer on himself and its
	 * Players
	 */
	public void registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
		// recursive set to its players (probably not needed)
		// for ( Player p : this.getPlayers() ) {
		// p.registerEventObserver(eventObserver);
		// }
	}


	public void notifyEventObserver(EventData eventData) {
		if (this.report != null) {
			this.report.notify(eventData);
		}
	}

}
