package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Move or sprint to the closest enemy.
 */
public class MoveToEnemy extends Action {
	private static final double MARGIN_ERROR = 0.01;
	private static final boolean CANCELABLE = true;

	private double speed;
	private Player target;
	private double distanceToTarget;

	public MoveToEnemy(Player owner) {
		super(owner, CANCELABLE);
		this.distanceToTarget = 0.0;
		convertParam();
	}

	private void convertParam() throws InvalidActionParameter {
		ArrayList<String> params = this.getParam();
		if (params.size() != 1) {
			throw new InvalidActionParameter(MoveToEnemy.class.getSimpleName());
		}

		try {
			Mode mode = Mode.valueOf(params.get(0));
			this.speed = (mode.equals(Mode.MOVE) ? Player.MOVE_SPEED : Player.SPRINT_SPEED);
		} catch (IllegalArgumentException e) {
			throw new InvalidActionParameter(MoveToEnemy.class.getSimpleName());
		}
	}

	/**
	 * Check if the the action has already finished.
	 */
	@Override
	public boolean hasFinished() {
		return this.distanceToTarget < 0 + MARGIN_ERROR;
	}

	/**
	 * Check if the required condition for an action applies.
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
		return this.getOwner().getCurrentOrder().getPreferredTarget() != null;
	}

	/**
	 * Begin the execution of the action. Will be called once at the start of the action.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void begin(State state) {
		this.target = this.getOwner().getCurrentOrder().getPreferredTarget();
	}

	/**
	 * Execute the action.
	 *
	 * @param state Current State of the game
	 * @param dtime Duration of the round
	 */
	@Override
	public void update(State state, double dtime) {

		// Turn at half the speed
		double angleToTarget = this.getOwner().getAngleToTarget(target);
		double updatedAngle = this.getOwner().getAngle();
		double rotation = dtime * Player.ROT_SPEED / 2;

		if (Math.abs(angleToTarget) < rotation) {
			updatedAngle += angleToTarget;
		} else {
			updatedAngle += rotation * ((angleToTarget > 0) ? 1 : -1);
		}
		this.getOwner().setAngle(updatedAngle);

		EventData eventData =
				new EventData("startTurning").addAttribute("id", getOwner().getId()).addAttribute(
						"angspeed",
						this.getOwner().ROT_SPEED / 2 * ((angleToTarget > 0.0) ? 1f : -1f));
		this.notifyEventObserver(eventData);

		eventData = new EventData("stopTurning").addAttribute("id", getOwner().getId());
		this.notifyEventObserver(eventData);



		// Move at half the speed
		double distanceToTarget = state.getMap().getDistance(this.getOwner(), this.target);
		double moveDistance = dtime * this.speed / 2;

		if (distanceToTarget < moveDistance) {
			this.getOwner().move(this.getOwner().getAngle(), moveDistance, true);
		} else {
			this.getOwner().move(this.getOwner().getAngle(), this.speed / 2, dtime, true);
		}
	}

	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void end(State state) {
		// Empty on purpose
	}

	private enum Mode {
		MOVE, SPRINT
	}
}
