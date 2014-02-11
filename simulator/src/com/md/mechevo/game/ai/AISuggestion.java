package com.md.mechevo.game.ai;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.action.Action;

/**
 * AISuggestion is the result of choosing an action from the AI
 */
public final class AISuggestion {

	private Player target;
	private AIEntry aiEntry;


	/**
	 * Constructor with specified owner.
	 */
	public AISuggestion(AIEntry entry, Player target) {
		this.aiEntry = entry;
		this.target = target;
	}


	/**
	 * Retrieve the first action of the entry.
	 * 
	 * @return First action in the entry
	 */
	public Action getFirstAction() {
		return (this.aiEntry.getActions().size() > 0) ? this.aiEntry.getActions().get(0) : null;
	}


	/**
	 * Get target Player
	 */
	public Player getTarget() {
		return this.target;
	}


	/**
	 * Get AI entry
	 */
	public AIEntry getAiEntry() {
		return this.aiEntry;
	}

}
