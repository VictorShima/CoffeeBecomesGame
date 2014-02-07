package com.md.mechevo.game.ai;

import com.md.mechevo.game.State;

public interface AIEntry {

	private AIAlgorithm algorithm;
	private ArrayList<AICondition> conditions;
	private ArrayList<AIAction> actions;


	/**
	 * Constructor.
	 * 
	 * @param aiAlgo AI Algorithm that this entry is inserted.
	 */
	public AIEntry(AIAlgorithm aiAlgo) {
		this.algorithm = aiAlgo;
		this.conditions = new ArrayList<AICondition>();
		this.actions = new ArrayList<AIAction>();
	}

	/**
	 * Check if the current state fulfills all the conditions.
	 * 
	 * @param state Current state of the game
	 * @return True if it fulfills all the conditions
	 */
	public boolean checkRequirements(State state) {
		boolean pass = true;
		for ( AICondition cond : this.conditions ) {
			pass = pass && cond.check(state);
			if (!pass) {
				break;
			}
		}
		return pass;
	}

	/**
	 * Since Conditions may change the target, ask them which one should be targetted. This function
	 * expects that checkRequirements to return true
	 * 
	 * @param state Current state of the game
	 * @return Player reference if there is one, else its null
	 * @todo Optimize the preferredTarget retrieval
	 */
	public Player findPreferredTarget(State state) {
		Player preferred = null;
		for ( AICondition cond : this.conditions ) {
			if (cond.getPreferredTarget(State) != null) {
				preferred = cond.getPreferredTarget(State);
			}
		}
		return preferred;
	}

	/**
	 * Retrieve the first action of the entry.
	 * 
	 * @return First action in the delay
	 */
	public AIAction getFirstAction() {
		return (actions.length() > 0) ? actions[0] : null;
	}
}
