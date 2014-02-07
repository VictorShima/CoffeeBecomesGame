package com.md.mechevo.game.ai;
import com.md.mechevo.game.Player;

/**
 * AISuggestion is the result of choosing an action from the AI
 */
public final class AISuggestion {

	private Player preferredTarget;
	private AIEntry entry;


	/**
	 * Constructor with specified owner.
	 * 
	 * @param owner Owner of the AI
	 */
	public AISuggestion(AIEntry entry, Player preferredTarget) {
		this.entry = entry;
		this.preferredTarget = preferredTarget;
	}
	
}
