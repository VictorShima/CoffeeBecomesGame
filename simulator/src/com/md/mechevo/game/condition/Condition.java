package com.md.mechevo.game.condition;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;


/**
 * A Condition evaluates if something can be done. Conditions can be used by AIEntries to know if
 * the actions should be executed, or by Weapons to know if its viable to fire a weapon.
 */
public abstract class Condition {

	private Player owner; // /< Player that controls object with condition
	private String param; // /< Condition dependant attribute

	public Condition(Player owner) {
		this.owner = owner;
	}

	protected Condition(Player owner, String param) {
		this.owner = owner;
		this.param = param;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * Check if the condition applies.
	 * 
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	abstract public boolean check(State state);


	/**
	 * Get the preferred target for this condition.
	 * 
	 * @param state Current State of the game
	 * @return Reference to target Player or null if none
	 */
	abstract public Player getPreferredPlayer(State state);


}
