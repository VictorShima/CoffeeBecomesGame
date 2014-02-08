package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;


/**
 * An Action is the current order the mech will perform.
 * Actions can be linked with other actions to know which one will be next.
 */
public class MoveStraightAction extends Action {


	/**
	 * Class Constructor
	 */
	public MoveStraightAction(Player owner) {
		this.owner = owner;
	}
	

	/**
	 * Check if the required condition for an action applies.
	 * 
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	public boolean check(State state) {
		return true; // always valid
	}


	/**
	 * Execute the action.
	 * 
	 * @param state Current State of the game
	 * @return Reference to target Player or null if none
	 */
	abstract public void execute(State state) {
		this.owner.setMovementState(this.owner.MovementState.MOVING);
	}


}
