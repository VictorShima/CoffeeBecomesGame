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
	private float duration; ///< Duration of the action
	private boolean cancelable; ///< Whether or not the action can be canceled mid-action


	/**
	 * Class Constructor
	 */
	public Action(Player owner, float duration, boolean cancelable) {
		this.owner = owner;
		this.duration = duration;
		this.cancelable = cancelable;
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
	 * Get Duration in seconds
	 */
	public float getDuration() {
		return this.duration;
	}
	
	
	/**
	 * Check if it can be canceled
	 */
	public boolean isCancelable() {
		return this.cancelable;
	}


	/**
	 * Check if the required condition for an action applies.
	 * 
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	abstract public boolean check(State state);


	/**
	 * Begin the execution of the action.
	 * Will be called once at the start of the action.
	 *
	 * @param state Current state of the game
	 */
	abstract public void begin();


	/**
	 * Execute the action.
	 * 
	 * @param state Current State of the game (contains duration of round)
	 */
	abstract public void execute(State state);


}
