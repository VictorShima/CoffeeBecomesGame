package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;


/**
 * An Action is the current order the mech will perform.
 * Actions can be linked with other actions to know which one will be next.
 */
public abstract class Action {

	private Player owner; ///< Player that controls object with condition
	private String extra; ///< Action dependant attribute
	private Action next; ///< Next action to execute after this one


	/**
	 * Class Constructor
	 */
	public Action(Player owner) {
		this.owner = owner;
	}
	
	
	/**
	 * Get the extra attribute
	 */
	public String getExtra() {
		return this.extra;
	}
	
	/**
	 * Set the extra attribute
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	
	/**
	 * Get the next Action
	 */
	public Action getNext() {
		return this.next;
	}
	
	/**
	 * Set the next Action
	 */
	public void setNext(Action next) {
		this.next = next;
	}


	/**
	 * Check if the required condition for an action applies.
	 * 
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	abstract public boolean check(State state);


	/**
	 * Execute the action.
	 * 
	 * @param state Current State of the game
	 * @return Reference to target Player or null if none
	 */
	abstract public void execute(State state);


}
