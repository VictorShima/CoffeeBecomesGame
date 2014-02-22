package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Map;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Turns against the opponent if he is in the Field of View.
 */
public class FaceOpponent extends Action {
	private static final double MARGIN_ERROR = 1; // /< margin error is bigger because we don't need
													// it to be dead center
	private static final boolean CANCELABLE = true;

	private double turnAmount;
	private boolean turnLeft;

	public FaceOpponent(Player owner) {
		super(owner, CANCELABLE);
	}

	/**
	 * Check if the the action has already finished.
	 */
	@Override
	public boolean hasFinished() {
		return this.turnAmount < MARGIN_ERROR;
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
		// Get the target preferred by the conditions
		Player target = this.getOwner().getCurrentOrder().getPreferredTarget();

		// If no target is preferred, choose the closest one
		if (target == null) {
			ArrayList<Player> players = getOwner().fieldOfView(state, Player.FieldOfViewAngle.VIEW);
			if (!players.isEmpty()) {
				double nearestDist = Double.MAX_VALUE;
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

		double angleToTarget = Map.getAngleToTarget(this.getOwner(), target);
		double diff = angleToTarget - this.getOwner().getAngle();
		this.turnLeft = (0 < diff && diff < 180) || (-360 < diff && diff < -180);
		if (this.turnLeft) {
			this.turnAmount = (angleToTarget - this.getOwner().getAngle() + 360) % 360;
		} else {
			this.turnAmount = (this.getOwner().getAngle() - angleToTarget + 360) % 360;
		}

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
		double rotation = Player.ROT_SPEED * dtime;
		if (turnAmount < rotation) {
			this.getOwner().turn((this.turnLeft ? turnAmount : -turnAmount));
			this.turnAmount = 0;
		} else {
			this.getOwner().turn(this.turnLeft ? rotation : -rotation);
			this.turnAmount -= rotation;
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
