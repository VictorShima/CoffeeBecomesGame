package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Move or sprint to target or to closest enemy.
 */
public class MoveToEnemy extends Action {
	private static final double MARGIN_ERROR = 0.01;
	private static final boolean CANCELABLE = true;

	private Mode mode;
	private double distance;
	private double distanceAlreadyMoved;
	private Player target;

	// TODO How do i get the target??
	// TODO This action can be a "FaceOpponent" Followed by a "MoveInLIne"


	public MoveToEnemy(Player owner) {
		super(owner, CANCELABLE);
		this.distanceAlreadyMoved = 0f;
		convertParam();
	}

	private void convertParam() throws InvalidActionParameter {
		ArrayList<String> params = this.getParam();
		if (params.size() != 1) {
			throw new InvalidActionParameter(MoveToEnemy.class.getName());
		}

		try {
			this.mode = Mode.valueOf(params.get(0));
		} catch (IllegalArgumentException e) {
			throw new InvalidActionParameter(MoveToEnemy.class.getName());
		}
	}

	/**
	 * Check if the the action has already finished.
	 */
	@Override
	public boolean hasFinished() {
		return false;
	}

	/**
	 * Check if the required condition for an action applies.
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
		return true;
	}

	/**
	 * Begin the execution of the action. Will be called once at the start of the action.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void begin(State state) {
		// this.target = owner.getIaSuggestion.getTarget;

	}

	/**
	 * Execute the action.
	 *
	 * @param state Current State of the game
	 * @param dtime Duration of the round
	 */
	@Override
	public void update(State state, double dtime) {

	}

	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void end(State state) {

	}

	private enum Mode {
		MOVE, SPRINT
	}
}
