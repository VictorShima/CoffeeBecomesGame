package com.md.mechevo.game.ai;

import com.md.mechevo.game.Player;

/**
 * AISuggestion is the result of choosing an action from the AI
 */
public final class AIAlgorithm {

	private Player preferredTarget;
	private AIEntry entry;


	/**
	 * Constructor with specified owner.
	 * 
	 * @param owner Owner of the AI
	 */
	public AIAlgorithm(AIEntry entry, Player preferredTarget = null) {
		this.entry = entry;
		this.preferredTarget = preferredTarget;
	}
	
}
