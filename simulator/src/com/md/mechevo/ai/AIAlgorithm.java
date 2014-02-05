package com.md.mechevo.ai;


/**
 * AIAlgorithm will run its internal algorithm and pick an AIAction for the owner Solid to execute
 */
public final class AIAlgorithm {

	private Player owner;


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
	public calculateBestAction(State state) {}



}
