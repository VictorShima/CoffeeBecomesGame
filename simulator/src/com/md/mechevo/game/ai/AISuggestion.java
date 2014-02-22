package com.md.mechevo.game.ai;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Position;
import com.md.mechevo.game.State;
import com.md.mechevo.game.action.Action;

/**
 * AISuggestion is the result of choosing an order from the AI
 */
public final class AISuggestion {

	/**
	 * Preferred target. Takes priority over preferredPosition
	 */
	private Player preferredTarget;


	/**
	 * Preferred position. Effective if no preferredTarget is mentioned.
	 */
	private Position preferredPosition;


	/**
	 * Reference to AiEntry to know if player is performing the same Order or a new one.
	 */
	private AIEntry aiEntry;


	/**
	 * Current Action being performed.
	 */
	private Action action;

	/**
	 * Time since the begin of current action.
	 */
	private double currentActionTime;


	/**
	 * Constructor with specified owner.
	 */
	public AISuggestion(AIEntry entry, Player target) {
		this.aiEntry = entry;
		this.preferredTarget = target;
		this.action = (entry.getActions().size() > 0) ? entry.getActions().get(0) : null;
		this.currentActionTime = 0;
	}


	/**
	 * Constructor with specified position.
	 */
	public AISuggestion(AIEntry entry, Position position) {
		this.aiEntry = entry;
		this.preferredPosition = position;
		this.action = (entry.getActions().size() > 0) ? entry.getActions().get(0) : null;
		this.currentActionTime = 0;
	}


	/**
	 * Add time to the current action
	 */
	public void addActionTime(double dtime) {
		this.currentActionTime += dtime;
	}

	public double getCurrentActionTime() {
		return currentActionTime;
	}

	/**
	 * Discover if action is on start.
	 * 
	 * @return True if action is starting.
	 */
	public boolean isActionStart() {
		return (this.action != null && this.currentActionTime == 0);
	}

	/**
	 * Retrieve the first action that can be performed or null if none exists
	 *
	 * @return Current action in the entry
	 */
	public Action getAction(State state) {
		return this.action;
	}

	/**
	 * @param state the current state of the game
	 * @return the next action that can be executed or null if none exists
	 */
	public Action switchAction(State state) {
		this.currentActionTime = 0;
		if (this.action != null) {
			this.action = this.action.getNext();
		}
		return this.action;
	}

	/**
	 * Get the preferred target Player
	 */
	public Player getPreferredTarget() {
		return this.preferredTarget;
	}

	/**
	 * Get the preferred position. Can be fetched from target Player or directly from the
	 * position.
	 */
	public Position getPreferredPosition() {
		return this.preferredTarget != null
				? this.preferredTarget.getPosition()
				: this.preferredPosition;
	}


	/**
	 * Get AI entry
	 */
	public AIEntry getAiEntry() {
		return this.aiEntry;
	}

}
