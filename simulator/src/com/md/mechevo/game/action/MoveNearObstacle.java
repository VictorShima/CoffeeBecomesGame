package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.Obstacle;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Move to the nearest obstacle in a direct line
 */
public class MoveNearObstacle extends Action {
	private static final boolean CANCELABLE = true;

	private Obstacle target;

	public MoveNearObstacle(Player owner) {
		super(owner, CANCELABLE);
	}

	public Obstacle getTarget() {
		return target;
	}

	public void setTarget(Obstacle target) {
		this.target = target;
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
		final Player owner = this.getOwner();
		ArrayList<Obstacle> obstacles =
				this.getOwner().fieldOfViewObstacles(state, Player.FieldOfViewAngle.VIEW);

		double nearestDist = Double.MAX_VALUE;
		if (!obstacles.isEmpty()) {
			double dist;
			for (Obstacle o : obstacles) {
				double distX = owner.getPosition().getX() - o.getPosition().getX();
				double distY = owner.getPosition().getY() - o.getPosition().getY();
				dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
				if (dist < nearestDist) {
					nearestDist = dist;
					this.setTarget(o);
				}
			}
		}

		final double distToObstacle = nearestDist;
		Action firstAction = new Turn(this.getOwner(), new ArrayList<String>() {
			{
				add(Double.toString(owner.getAngleToTarget(target)));
			}
		});
		Action secondAction = new MoveInLine(this.getOwner(), new ArrayList<String>() {
			{
				add("MOVE");
				add("FORWARD");
				add(Double.toString(distToObstacle));
			}
		});
		this.setNext(firstAction);
		firstAction.setNext(secondAction);
	}

	/**
	 * Execute the action.
	 *
	 * @param state Current State of the game
	 * @param dtime Duration of the round
	 */
	@Override
	public void update(State state, double dtime) {
		// Empty on purpose
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
}
