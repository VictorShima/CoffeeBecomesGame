package com.md.mechevo.game.condition;

import java.util.ArrayList;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Receives the distance to the enemy
 */
public class DistanceToEnemy extends Condition {
	private Player target;
	private double distance;

	public DistanceToEnemy(Player owner, ArrayList<String> param) throws InvalidConditionParameter {
		super(owner, param);
		convertParam();
	}

	private void convertParam() throws InvalidConditionParameter {
		if (this.getParam().size() != 1) {
			throw new InvalidConditionParameter(DistanceToEnemy.class.getSimpleName());
		}

		try {
			this.distance = Double.valueOf(this.getParam().get(0));
		} catch (IllegalArgumentException e) {
			throw new InvalidConditionParameter(DistanceToEnemy.class.getSimpleName());
		}
	}

	public Player getTarget() {
		return target;
	}

	public void setTarget(Player target) {
		this.target = target;
	}

	public double getDistance() {
		return distance;
	}

	/**
	 * Check if the condition applies.
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
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
					this.target = p;
				}
			}
		}

		return this.target != null
				&& state.getMap().getDistance(this.getOwner(), this.target) < this.getDistance();
	}

	/**
	 * Get the preferred target for this condition.
	 *
	 * @param state Current State of the game
	 * @return Reference to target Player or null if none
	 */
	@Override
	public Player getPreferredPlayer(State state) {
		return this.target;
	}
}
