package com.md.mechevo.game.condition;

import java.util.ArrayList;
import java.util.List;

import com.md.mechevo.game.*;

/**
 * Selects the closest/farthest visible enemy
 */
public class EnemySpotted extends Condition {
	private Player preferredEnemy;

	public EnemySpotted(Player owner, ArrayList<String> param) {
		super(owner, param);
	}

	public Player getPreferredEnemy() {
		return preferredEnemy;
	}

	public void setPreferredEnemy(Player preferredEnemy) {
		this.preferredEnemy = preferredEnemy;
	}

	/**
	 * This works because the target is already in the Field Of View
	 * @param target the target the player wants to see
	 * @param state the current state of the game
	 * @return true if there is no solid between the player and the target.
	 */
	private boolean isTargetVisible(Solid target, State state) {
		double angleToTarget = Map.getAngleToTarget(this.getOwner(), target);
		Position ownerPos = this.getOwner().getPosition();
		List<Obstacle> obstacles =
				this.getOwner().fieldOfViewObstacles(state, Player.FieldOfViewAngle.VIEW);

		/**
		 * To create a vector pointing to the target and see if it intersects with any obstacle,
		 * vector = cos( abs(angleToTarget - angleToObstacle) ) * distanceToObstacle
		 *
		 * To see if they intersect, just see if distance( Position(vector + ownerPos), obstaclePos ) < obstacleRadius
		 */
		for (Obstacle o : obstacles) {
			double angleToObstacle = Map.getAngleToTarget(this.getOwner(), o);
			double alpha = Math.abs(angleToObstacle - angleToTarget);
			Position obstaclePos = o.getPosition();
			double dstObstacle =
					Math.sqrt(Math.pow(obstaclePos.getX() - ownerPos.getX(), 2)
							+ Math.pow(obstaclePos.getY() - ownerPos.getY(), 2));
			double vectorToTargetDst = Math.cos(Math.toRadians(alpha)) * dstObstacle;

			double playerToTargetAngle = Math.abs(angleToTarget - this.getOwner().getAngle());
			Position vectorToTargetPos =
					new Position(ownerPos.getX() + Math.cos(Math.toRadians(playerToTargetAngle))
							* vectorToTargetDst, ownerPos.getY()
							+ Math.sin(Math.toRadians(playerToTargetAngle)) * vectorToTargetDst);

			// if the point intersects with the obstacle
			if (o.intersectsWith(vectorToTargetPos)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the condition applies.
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
		// TODO the preferredEnemy can be the closest or the farthest
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
					this.preferredEnemy = p;
				}
			}
		}
		return (this.preferredEnemy != null) && isTargetVisible(this.preferredEnemy, state);
	}

	/**
	 * Get the preferred target for this condition.
	 *
	 * @param state Current State of the game
	 * @return Reference to target Player or null if none
	 */
	@Override
	public Player getPreferredPlayer(State state) {
		return this.getPreferredEnemy();
	}
}
