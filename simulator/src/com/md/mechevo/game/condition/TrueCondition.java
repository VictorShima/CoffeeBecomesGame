package com.md.mechevo.game.condition;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

public class TrueCondition extends Condition {
	/**
	 * Constructor with no owner.
	 */
	public TrueCondition() {
		super(null);
	}

	/**
	 * Class Constructor
	 */
	public TrueCondition(Player owner) {
		super(owner);
	}


	/**
	 * Check if the condition applies.
	 * 
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	public boolean check(State state) {
		return true;
	}


	/**
	 * Get the preferred target for this condition.
	 * 
	 * @param state Current State of the game
	 * @return Reference to target Player or null if none
	 */
	public Player getPreferredPlayer(State state) {
		return null;
	}



}
