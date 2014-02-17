package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Sideways dash. (LEFT or RIGHT)
 */
public class Dash extends Action {
	private static final double DURATION = 0.2;
	private static final boolean CANCELABLE = false;
	private static final double HEAT_INCREASE = 70; // /< the increase in the player's heat by executing this action
	private static final double DISTANCE = 60;

	private Side side;

	/**
	 * @param param the direction of the dash (LEFT or RIGHT)
	 */
	public Dash(Player owner, ArrayList<String> param) {
		super(owner, param, Dash.CANCELABLE);
		convertParam();
	}

	private void convertParam() throws InvalidActionParameter {
		ArrayList<String> params = this.getParam();
		if (params.size() != 1) {
			throw new InvalidActionParameter(Dash.class.getSimpleName());
		}

		try {
			this.side = Side.valueOf(params.get(0));
		} catch (IllegalArgumentException e) {
			throw new InvalidActionParameter(MoveInLine.class.getSimpleName());
		}
	}

	/**
	 * Check if the the action has already finished.
	 */
	@Override
	public boolean hasFinished() {
		return Dash.DURATION <= this.getOwner().getCurrentOrder().getCurrentActionTime();
	}

	/**
	 * Check if the required condition for an action applies.
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
		return this.getOwner().getHeat() + this.HEAT_INCREASE < this.getOwner().getMaxHeat();
	}

	/**
	 * Begin the execution of the action. Will be called once at the start of the action.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void begin(State state) {
		this.getOwner().increaseHeat(Dash.HEAT_INCREASE);

		EventData eventData =
				new EventData("startDashing").addAttribute("id", getOwner().getId()).addAttribute(
						"side", this.side.toString());
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
		double angle =
				(this.side == Side.LEFT) ? ((this.getOwner().getAngle() + 90) % 360) : ((this
						.getOwner().getAngle() - 90) % 360);
		this.getOwner().move(angle, Dash.DISTANCE * dtime / Dash.DURATION, true);
	}

	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void end(State state) {
		EventData eventData = new EventData("stopDashing").addAttribute("id", getOwner().getId());
		this.notifyEventObserver(eventData);
	}

	private enum Side {
		LEFT, RIGHT
	}
}
