package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

public class AttackMainL extends Action {
	/**
	 * Duration of the action
	 * 
	 * TODO: Actual duration will depend on extra attribute
	 */
	private static final int DURATION = 1000;


	/**
	 * Whether or not the action is cancelable
	 */
	private static final boolean CANCELABLE = true;


	/**
	 * Class Constructor
	 */
	public AttackMainL(Player owner) {
		super(owner, AttackMainL.DURATION, AttackMainL.CANCELABLE);
	}


	public boolean check(State state) {
		return true; // always valid
	}

	@Override
	public void begin(State state) {
		getOwner().setMovementState(Player.MovementState.MOVING);
		getOwner().setSpeed(Player.SPEED);
	}


	public void update(State state, double dtime) {
		// TODO: do the actual moving
	}


	public void end(State state) {}


}
