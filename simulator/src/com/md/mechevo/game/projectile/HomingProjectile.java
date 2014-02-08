package com.md.mechevo.game.projectile;

import com.md.mechevo.game.*;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class HomingProjectile extends Projectile {
	private Solid target;
	private float rotVel; // Rotation Velocity

	protected HomingProjectile(Position position, float width, float height, float speed,
					float angle, Weapon weapon, Solid target, float rotVel) {
		super(position, width, height, speed, angle, weapon);
		this.setTarget(target);
		this.setRotVel(rotVel);
	}

	public Solid getTarget() {
		return target;
	}

	public void setTarget(Solid target) {
		this.target = target;
	}

	public float getRotVel() {
		return rotVel;
	}

	public void setRotVel(float rotVel) {
		this.rotVel = rotVel;
	}

	/**
	 * This is the standard collider for homing projectiles (homing projectiles with special
	 * atributes should override this method)
	 */
	@Override
	public void collidesWith(State state, Player p) {
		p.takeDamage(this.getWeapon().getDamage());
		this.setDestroy(true);
	}

	@Override
	public void collidesWith(State state, Projectile p) {

	}

	@Override
	public void collidesWith(State State, Obstacle o) {
		this.setDestroy(true);
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
	public void update(State state) {
		float vecToTargetX = this.getTarget().getPosition().getX() - this.getPosition().getX();
		float vecToTargetY = this.getTarget().getPosition().getY() - this.getPosition().getY();

		float vecX = (float) Math.cos(this.getAngle());
		float vecY = (float) Math.sin(this.getAngle());

		float cosValue =
						((vecToTargetX * vecX) + (vecToTargetY * vecY))
										/ ((float) Math.sqrt(Math.pow(vecX, 2f)
														+ Math.pow(vecY, 2f)) * (float) Math
															.sqrt(Math.pow(vecToTargetX, 2f)
                                                                    + Math.pow(vecToTargetY,
                                                                    2f)));

		float rotationAngle = (float) Math.acos(cosValue) * 180 / (float) Math.PI;
		rotationAngle *= state.getTime() ;

		// Rotate clockwise/counter-clockwise is determined by sign of cross-product
		float crossProd = (vecToTargetX * vecY) - (vecToTargetY * vecX);


		if (rotationAngle < rotVel *  state.getTime() ) {
			if (crossProd < 0) {
				this.setAngle(this.getAngle() + rotationAngle);
			} else {
				this.setAngle(this.getAngle() - rotationAngle);
			}
		} else {
			if (crossProd < 0) {
				this.setAngle(this.getAngle() + rotVel *  state.getTime() );
			} else {
				this.setAngle(this.getAngle() - rotVel *  state.getTime() );
			}
		}

        //After calculating the new angle we simply move the missile like a regular solid
        float velX = (float) Math.cos(this.getAngle());
        float velY = (float) Math.sin(this.getAngle());
		/* Normalization of the velocity vector */
        float vel = velX + velY;
        velX = (velX / vel) *  state.getTime()  * (this.getSpeed() / 1000);
        velY = -(velY / vel) *  state.getTime()  * (this.getSpeed() / 1000);

        this.setPosition(new Position(this.getPosition().getX() + velX, this.getPosition().getY()
                + velY));
	}

}