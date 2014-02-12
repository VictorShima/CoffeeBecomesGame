package com.md.mechevo.game.action;

import com.md.mechevo.game.*;



/**
 * An Action is the current order the mech will perform. Actions can be linked with other actions to
 * know which one will be next. All subclasses must implement the empty constructor.
 */
public abstract class Action implements EventObservable {

	private Player owner; // /< Player that controls object with condition
	private String param; // /< Action dependant attribute
	private Action next; // /< Next action to execute after this one
	private float duration; // /< Duration of the action
	private boolean cancelable; // /< Whether or not the action can be canceled mid-action

	private EventObserver report;

	/**
	 * Class Constructor
	 */
	public Action(Player owner, float duration, boolean cancelable) {
		this.owner = owner;
		this.duration = duration;
		this.cancelable = cancelable;
	}

	protected Action(Player owner, String param, float duration, boolean cancelable) {
		this.owner = owner;
		this.param = param;
		this.duration = duration;
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
	public String getParam() {
		return this.param;
	}

	/**
	 * Set the param attribute
	 */
	public void setParam(String param) {
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
	 * Get Duration in seconds
	 */
	public float getDuration() {
		return this.duration;
	}


	/**
	 * Check if it can be canceled
	 */
	public boolean isCancelable() {
		return this.cancelable;
	}


	/**
	 * Check if the required condition for an action applies.
	 * 
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	abstract public boolean check(State state);


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


}
