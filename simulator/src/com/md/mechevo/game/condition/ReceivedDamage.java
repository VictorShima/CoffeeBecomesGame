package com.md.mechevo.game.condition;

import java.util.ArrayList;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * ReceivedDamage (frontal, back, side) : true if just received damage
 */
public class ReceivedDamage extends Condition {
	private Face face;

	public ReceivedDamage(Player owner, ArrayList<String> param) {
		super(owner, param);
		this.convertParam();
	}

	public void convertParam() throws InvalidConditionParameter {
		if (this.getParam().size() != 1) {
			throw new InvalidConditionParameter(ReceivedDamage.class.getName());
		}

		switch (this.getParam().get(0).toLowerCase()) {
			case "front":
				this.setFace(Face.FRONT);
				break;
			case "back":
				this.setFace(Face.BACK);
				break;
			case "left":
				this.setFace(Face.LEFT);
				break;
			case "right":
				this.setFace(Face.RIGHT);
				break;
			default:
				throw new InvalidConditionParameter(ReceivedDamage.class.getName());

		}
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	/**
	 * Check if the condition applies. If this condition applies we should update the players lastHitAngle to null
	 * so that the player doesn't keep repeating this action
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
		// We add 180 degrees to the angle because the bullet travels in the opposite direction than
		// the players angle
		double hitAngle = this.getOwner().getLastHitAngle();
		double playerAngle = this.getOwner().getAngle() + 180f;
		if ((hitAngle > (this.getFace().getMinAngle() + playerAngle) % 360f)
				&& (hitAngle < (this.getFace().getMaxAngle() + playerAngle) % 360f)) {
			this.getOwner().setLastHitAngle(Double.NaN);
			return true;
		}
		return false;
	}

	/**
	 * Get the preferred target for this condition.
	 *
	 * @param state Current State of the game
	 * @return Reference to target Player or null if none
	 */
	@Override
	public Player getPreferredPlayer(State state) {
		return null;
	}

	public static enum Face {
		LEFT(45f, 135f), BACK(135f, 225f), FRONT(315f, 45f), RIGHT(225f, 315f);

		private final double minAngle;
		private final double maxAngle;

		Face(double minAngle, double maxAngle) {
			this.minAngle = minAngle;
			this.maxAngle = maxAngle;
		}

		private double getMinAngle() {
			return minAngle;
		}

		private double getMaxAngle() {
			return maxAngle;
		}
	}
}
