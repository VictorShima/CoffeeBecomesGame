package com.md.mechevo.ai;


/**
 * AIAlgorithm will run its internal algorithm and pick an AIAction for the owner Solid to execute
 */
public final class AIAlgorithm {

	private Player owner;
	private ArrayList<AIEntry> entries;


	/**
	 * Constructor with specified owner.
	 * 
	 * @param owner Owner of the AI
	 */
	public AIAlgorithm() { }
	
	
	/**
	 * Constructor with specified owner.
	 * 
	 * @param owner Owner of the AI
	 */
	public AIAlgorithm(Player owner) {
		this.owner = owner;
	}


	/**
	 * Goes through all AI Entries in order and selects the best Entry.
	 * Returns the entry because in case of being the same as the last frame the actions
	 * will not be reset to the initial action again
	 * 
	 * @param state Current state of the game
	 * @return The proposed Suggestion
	 */
	public AIEntry calculateBestAction(State state) {
	
		for (AIEntry entry : this.entries) {
			if ( entry.checkRequirements(state) ) {
				return new AISuggestion(entry, entry.findPreferredTarget(state));
			}
		}
		return null;
		
	}



}
