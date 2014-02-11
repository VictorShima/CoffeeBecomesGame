package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Player.MovementState;
import com.md.mechevo.game.State;


/**
 * An Action is the current order the mech will perform.
 * Actions can be linked with other actions to know which one will be next.
 */
public class MoveStraightAction extends Action {


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
		super(owner, MoveStraightAction.DURATION, MoveStraightAction.CANCELABLE);
	}
	

	public boolean check(State state) {
		return true; // always valid
	}

	@Override
	public void begin(State state) {
		getOwner().setMovementState(MovementState.MOVING);
		getOwner().setSpeed(Player.INITIAL_SPEED);
	}
	
	
	public void update(State state, float dtime) {
		//TODO: do the actual moving
	}
		
		
	public void end(State state) { }


}
