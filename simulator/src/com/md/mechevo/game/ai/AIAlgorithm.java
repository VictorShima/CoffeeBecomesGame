package com.md.mechevo.game.ai;

import java.util.ArrayList;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;
import com.md.mechevo.game.action.Action;
import com.md.mechevo.game.condition.Condition;

/**
 * AIAlgorithm will run its internal algorithm and pick an AIAction for the owner Solid to execute
 */
public final class AIAlgorithm {

	private Player owner;
	private ArrayList<AIEntry> entries;


	/**
	 * Default constructor
	 */
	public AIAlgorithm() {}


	/**
	 * Constructor with specified owner.
	 * 
	 * @param owner Owner of the AI
	 */
	public AIAlgorithm(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	/**
	 * Updates all owner variables to the same owner.
	 * 
	 * @param owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
		for (AIEntry entry : entries) {
			for (Condition cond : entry.getConditions()) {
				cond.setOwner(owner);
			}

			for (Action action : entry.getActions()) {
				action.setOwner(owner);
			}
		}
	}

	public void addEntry(AIEntry entry) {
		entries.add(entry);
	}

	/**
	 * Goes through all AI Entries in order and selects the best Entry. Returns the entry because in
	 * case of being the same as the last frame the actions will not be reset to the initial action
	 * again
	 * 
	 * @param state Current state of the game
	 * @return The proposed Suggestion
	 */
	public AISuggestion calculateBestAction(State state) {
		for (AIEntry entry : this.entries) {
			if (entry.checkRequirements(state)) {
				return new AISuggestion(entry, entry.findPreferredTarget(state));
			}
		}
		return null;

	}


	/**
	 * Get all Ai entries
	 */
	public ArrayList<AIEntry> getEntries() {
		return this.entries;
	}



}
