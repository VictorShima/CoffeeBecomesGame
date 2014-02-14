package com.md.mechevo.game.projectile;

import com.md.mechevo.game.*;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class HomingProjectile extends Projectile {
	private Solid target;
	private double rotSpeed; // Rotation Velocity

	protected HomingProjectile(int id, Position position, double radius, double speed,
			double angle, Weapon weapon, Solid target, double rotSpeed) {
		super(id, position, radius, speed, angle, weapon);
		this.setTarget(target);
		this.setRotSpeed(rotSpeed);
	}

	public Solid getTarget() {
		return target;
	}

	public void setTarget(Solid target) {
		this.target = target;
	}

	public double getRotSpeed() {
		return rotSpeed;
	}

	public void setRotSpeed(double rotSpeed) {
		this.rotSpeed = rotSpeed;
	}

	/**
	 * This is the standard collider for homing projectiles (homing projectiles with special
	 * atributes should override this method)
	 */
	@Override
	public void collidesWith(State state, Player p) {
		p.takeDamage(this.getWeapon().getDamage(), this.getAngle());
		this.setDestroyed(true);
	}

	@Override
	public void collidesWith(State state, Projectile p) {

	}

	@Override
	public void collidesWith(State State, Obstacle o) {
		this.setDestroyed(true);
	}

	@Override
	public void collidesWith(State state, Sentry s) {

	}

	/**
	 * This is the standard update for homingprojectiles. This function SOFTLY adjusts the angle of
	 * the projectile (for homingprojectiles that always point to the target this method should be
	 * overriden)
	 */
	@Override
	public void update(State state, double dtime) {
		double vecToTargetX = this.getTarget().getPosition().getX() - this.getPosition().getX();
		double vecToTargetY = this.getTarget().getPosition().getY() - this.getPosition().getY();

		double vecX = Math.cos(Math.toRadians(this.getAngle()));
		double vecY = Math.sin(Math.toRadians(this.getAngle()));

		double cosValue =
				((vecToTargetX * vecX) + (vecToTargetY * vecY))
						/ (Math.sqrt(Math.pow(vecX, 2f) + Math.pow(vecY, 2f)) * Math.sqrt(Math.pow(
								vecToTargetX, 2f) + Math.pow(vecToTargetY, 2f)));

		double rotationAngle = Math.acos(cosValue) * 180 / Math.PI;
		rotationAngle *= dtime;

		// Rotate clockwise/counter-clockwise is determined by sign of cross-product
		double crossProd = (vecToTargetX * vecY) - (vecToTargetY * vecX);

		if (rotationAngle < rotSpeed * dtime) {
			if (crossProd < 0) {
				this.setAngle(this.getAngle() + rotationAngle);
			} else {
				this.setAngle(this.getAngle() - rotationAngle);
			}
		} else {
			if (crossProd < 0) {
				this.setAngle(this.getAngle() + rotSpeed * dtime);
			} else {
				this.setAngle(this.getAngle() - rotSpeed * dtime);
			}
		}

		// After calculating the new angle we simply move forward the missile like a regular solid
		super.move(this.getAngle(), this.getSpeed(), dtime, true);
	}

}
