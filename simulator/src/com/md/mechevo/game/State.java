package com.md.mechevo.game;

import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonArray;

import com.md.mechevo.game.projectile.Projectile;

/**
 * Holds all the information about the current state of game. Includes the map, units on field,
 * teams, ...
 */
public class State implements EventObservable {
	private Map map;
	private ArrayList<Player> players;
	private ArrayList<Projectile> projectiles;
    private int nextId = 0;

	/**
	 * total time passed since beginning
	 */
	private float totalTime;
	
	/**
	 * Variable that holds the EventObserver, later will be converted to report
	 */
	private EventObserver report;
	


	public State() {
		this.players = new ArrayList<Player>();
		this.projectiles = new ArrayList<Projectile>();
		this.totalTime = 0;
	}

    public int getNextId() {
        return nextId++;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
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
		map.addSolid(p);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player p) {
		this.players.add(p);
	}
	
	
	public void addObstacle(Obstacle p) {
		this.map.addSolid(p);
	}

	public float getTotalTime() {
		return totalTime;
	}


	/**
	 * Update the current state to the next one
	 * 
	 * @param dtime Time since the last state
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
	
	
	
	/**
	 * Build the event report. Get the events from the EventObserver 
	 *
	 * @report Json Object that defines the event report
	 */
	public JsonElement buildEventReport() {
		if (this.report != null) {
			return this.report.generateReport();
		}
		return new JsonArray();
	}
	
	
	// interface EventObservable
	
	/**
	 * This registerEventObserver will try to recursively set the event observer on himself
	 * and its Players
	 */
	public void registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
		// recursive set to its players (probably not needed)
		//for ( Player p : this.getPlayers() ) {
		//	p.registerEventObserver(eventObserver);
		//}
	}
		
	
	public void notifyEventObserver(EventData eventData) {
		if ( this.report != null ) {
			this.report.notify(eventData);
		}
	}
	
}
