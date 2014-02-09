package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;


/**
 * Attack Action will shot the selected weapon, first by checking if it's cooldown is up.
 */
public class AttackAction extends Action {


	/**
	 * Duration of the action
	 * @todo Actual duration will depend on extra attribute
	 */
	private static final int DURATION = 1000;
	
	
	/**
	 * Whether or not the action is cancelable
	 */
	private static final boolean CANCELABLE = true;


	/**
	 * Class Constructor
	 */
	public MoveStraightAction(Player owner) {
		super(owner, this.DURATION, this.CANCELABLE);
	}
	

	public boolean check(State state) {
		return true; // always valid
	}


	public void begin(State state) {
		this.owner.setMovementState(this.owner.MovementState.MOVING);
		this.owner.setSpeed( this.owner.INITIAL_SPEED );
	}
	
	
	public void execute(State state) {
		//TODO: do the actual moving
	}
		


}
