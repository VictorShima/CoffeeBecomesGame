package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * A turn requires a direction (LEFT, RIGHT) and an angle (multiple of 45).
 * If the angle isn't a multiple of 45, it defaults to 0.
 */
public class Turn extends Action {
	private static final double MARGIN_ERROR = 0.01;
	private static final boolean CANCELABLE = true;

	/**
	 * TurnAmount is in degrees.
	 */
	private double turnAmount;
	private double alreadyTurned;
	private Direction direction;

	/**
	 * @param param the angle of the turn
	 */
	public Turn(Player owner, ArrayList<String> param) {
		super(owner, param, CANCELABLE);
		this.alreadyTurned = 0;
		this.convertParams();
	}

	public double getTurnAmount() {
		return turnAmount;
	}

	public void setTurnAmount(double turnAmount) {
		if (((int) turnAmount) % 45 != 0) {
			this.turnAmount = 0;
		} else {
			this.turnAmount = turnAmount;
		}
	}

	public double getAlreadyTurned() {
		return alreadyTurned;
	}

	public void setAlreadyTurned(double alreadyTurned) {
		this.alreadyTurned = alreadyTurned;
	}

	public void convertParams() throws InvalidActionParameter {
		if (this.getParam().size() != 2) {
			throw new InvalidActionParameter(Turn.class.getSimpleName());
		}
		try {
			this.direction = Direction.valueOf(this.getParam().get(0));
			this.setTurnAmount(Double.parseDouble(this.getParam().get(1)));
		} catch (Exception e) {
			throw new InvalidActionParameter(Turn.class.getSimpleName());
		}
	}

	/**
	 * Check if the the action has already finished.
	 */
	@Override
	public boolean hasFinished() {
		return Math.abs(this.getAlreadyTurned() - this.getTurnAmount()) < MARGIN_ERROR;
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
		EventData eventData =
				new EventData("startTurning").addAttribute("id", getOwner().getId()).addAttribute(
						"angspeed", this.getOwner().ROT_SPEED * ((turnAmount > 0f) ? 1f : -1f));
		this.notifyEventObserver(eventData);
	}

	/**
	 * Execute the action.
	 *
	 * @param state Current State of the game
	 * @param dtime Duration of the round
	 */
	@Override
	public void update(State state, double dtime) {
		Player owner = this.getOwner();
		double rotation = (owner.ROT_SPEED * dtime) * ((turnAmount > 0f) ? 1f : -1f);
		owner.turn(rotation);
		this.setAlreadyTurned(this.getAlreadyTurned() + rotation);
	}

	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void end(State state) {
		EventData eventData = new EventData("stopTurning").addAttribute("id", getOwner().getId());
		this.notifyEventObserver(eventData);
	}

	private enum Direction {
		LEFT, RIGHT
	}
}
