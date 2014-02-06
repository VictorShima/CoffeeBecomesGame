package com.md.mechevo.ai;


import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * AIAlgorithm will run its internal algorithm and pick an AIAction for the owner Solid to execute
 */
public final class AIAlgorithm {

	private Player owner;

	public AIAlgorithm() {

	}


	/**
	 * Constructor with specified owner.
	 * 
	 * @param owner Owner of the AI
	 */
	public AIAlgorithm(Player owner) {
		this.owner = owner;
	}


	/**
	 * Goes through all AI Entries in order and selects the best Action
	 * 
	 * @param state Current state of the game
	 * @return The proposed Action
	 */
	public AIAction calculateBestAction(State state) {
		// TODO
		return null;
	}



}
