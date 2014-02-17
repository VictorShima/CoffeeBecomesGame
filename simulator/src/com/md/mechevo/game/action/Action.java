package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.*;


/**
 * An Action is the current order the mech will perform. Actions can be linked with other actions to
 * know which one will be next. All subclasses must implement the empty constructor.
 */
public abstract class Action implements EventObservable {

	private Player owner; // /< Player that controls object with condition
	private ArrayList<String> param; // /< Action dependant attribute
	private Action next; // /< Next action to execute after this one
	private boolean cancelable; // /< Whether or not the action can be canceled mid-action

	private EventObserver report;

	protected Action(Player owner, boolean cancelable) {
		this.owner = owner;
		this.cancelable = cancelable;
	}

	protected Action(Player owner, ArrayList<String> param, boolean cancelable) {
		this.owner = owner;
		this.param = param;
		this.cancelable = cancelable;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * 
	 * @return the owner of this action
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Get the param attribute
	 */
	public ArrayList<String> getParam() {
		return this.param;
	}

	/**
	 * Set the param attribute
	 */
	public void setParam(ArrayList<String> param) {
		this.param = param;
	}


	/**
	 * Get the next Action
	 */
	public Action getNext() {
		return this.next;
	}

	/**
	 * Set the next Action
	 */
	public void setNext(Action next) {
		this.next = next;
	}

	/**
	 * Check if it can be canceled
	 */
	public boolean isCancelable() {
		return this.cancelable;
	}

	/**
	 * Check if the the action has already finished.
	 */
	public abstract boolean hasFinished();

	/**
	 * Check if the required condition for an action applies.
	 * 
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	public abstract boolean check(State state);


	/**
	 * Begin the execution of the action. Will be called once at the start of the action.
	 * 
	 * @param state Current state of the game
	 */
	public abstract void begin(State state);


	/**
	 * Execute the action.
	 *
	 * @param state Current State of the game
	 * @param dtime Duration of the round
	 */
	public abstract void update(State state, double dtime);


	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 * 
	 * @param state Current state of the game
	 */
	public abstract void end(State state);

	// interface EventObservable
	public void registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
	}

	public void notifyEventObserver(EventData eventData) {
		if (this.report != null) {
			this.report.notify(eventData);
		}
	}

	public void notifyEventObserver(EventData eventData, Double time) {
		if (this.report != null) {
			this.report.notify(eventData, time);
		}
	}
}
