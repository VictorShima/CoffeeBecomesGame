package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Turns against the opponent if he is in the Field of View.
 */
public class FaceOpponent extends Action {
	private static final double MARGIN_ERROR = 5; // /< margin error is bigger because we don't need
													// it to be dead center
	private static final boolean CANCELABLE = true;

	private Player target;
	private double turnAmount;
	private double alreadyTurned;

	public FaceOpponent(Player owner) {
		super(owner, CANCELABLE);
		target = this.getOwner().getCurrentOrder().getPreferredTarget();
	}

	/**
	 * Check if the the action has already finished.
	 */
	@Override
	public boolean hasFinished() {
		return (Math.abs(turnAmount - alreadyTurned) < MARGIN_ERROR);
	}

	/**
	 * Check if the required condition for an action applies.
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
		return (this.getOwner().fieldOfView(state, Player.FieldOfViewAngle.VIEW) != null);
	}

	/**
	 * Begin the execution of the action. Will be called once at the start of the action.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void begin(State state) {
		EventData eventData =
				new EventData("startFacingOpponent").addAttribute("id", getOwner().getId());
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
		if (target == null) {
			ArrayList<Player> players = getOwner().fieldOfView(state, Player.FieldOfViewAngle.VIEW);
			if (!players.isEmpty()) {
				double nearestDist = 99999;
				double dist;
				for (Player p : players) {
					double distX = this.getOwner().getPosition().getX() - p.getPosition().getX();
					double distY = this.getOwner().getPosition().getY() - p.getPosition().getY();
					dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
					if (dist < nearestDist) {
						nearestDist = dist;
						target = p;
					}
				}
			}
		}
		turnAmount = this.getOwner().getAngleToTarget(target);

		if (turnAmount < this.getOwner().ROT_SPEED * dtime) {
			this.getOwner().turn(turnAmount);
			alreadyTurned += turnAmount;
		} else {
			this.getOwner().turn(this.getOwner().ROT_SPEED * dtime);
			alreadyTurned += this.getOwner().ROT_SPEED * dtime;
		}
	}

	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void end(State state) {
		EventData eventData =
				new EventData("stopFacingOpponent").addAttribute("id", getOwner().getId());
		this.notifyEventObserver(eventData);
	}
}
