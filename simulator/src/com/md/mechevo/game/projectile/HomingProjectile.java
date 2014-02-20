package com.md.mechevo.game.projectile;

import java.util.ArrayList;

import com.md.mechevo.game.*;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class HomingProjectile extends Projectile {
	private Solid target;
	private double rotSpeed; // Rotation Velocity
	private ArrayList<Position> path = new ArrayList<>();
	private double beginTime;

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
		if (p.getId() != this.getWeapon().getOwner().getId()) {
			p.takeDamage(this.getWeapon().getDamage(), this.getAngle());
			this.setDestroyed(true);
		}
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
		final Position myPos = this.getPosition();
		final Position targetPos = this.getTarget().getPosition();
		final double frontPointX =
				this.getPosition().getX() + Math.cos(Math.toRadians(this.getAngle()));
		final double frontPointY =
				this.getPosition().getY() + Math.sin(Math.toRadians(this.getAngle()));

		double distBA =
				Math.sqrt(Math.pow((myPos.getX() - targetPos.getX()), 2)
						+ (Math.pow((myPos.getY() - targetPos.getY()), 2)));
		double distBC =
				Math.sqrt(Math.pow((myPos.getX() - frontPointX), 2)
						+ (Math.pow((myPos.getY() - frontPointY), 2)));
		double dotProd =
				((frontPointX - myPos.getX()) * (targetPos.getX() - myPos.getX()) + (frontPointY - myPos
						.getY()) * (targetPos.getY() - myPos.getY()));
		double cosValue = (dotProd / (distBA * distBC));
		double rotationAngle = Math.toDegrees(Math.acos(cosValue)) * dtime;

		// Rotate clockwise/counter-clockwise is determined by sign of cross-product
		double vecToTargetX = targetPos.getX() - myPos.getX();
		double vecToTargetY = targetPos.getY() - myPos.getY();
		double crossProd = (vecToTargetX * frontPointY) - (vecToTargetY * frontPointX);

		if (crossProd > 0) {
			this.setAngle(this.getAngle()
					+ ((rotationAngle < rotSpeed * dtime) ? rotationAngle : rotSpeed * dtime));
		} else {
			this.setAngle(this.getAngle()
					- ((rotationAngle < rotSpeed * dtime) ? rotationAngle : rotSpeed*dtime));
		}

		// After calculating the new angle we simply move forward the missile like a regular solid
		super.move(this.getAngle(), this.getSpeed(), dtime, true);
		path.add(this.getPosition());
	}

	@Override
	public void begin(State state) {
		super.begin(state);
		beginTime = this.getReport().getCurrentTime();
		path.add(this.getPosition());
	}

	@Override
	public void end(State state) {
		String pathString = "M " + (int) this.path.get(0).getX() + " " + (int) this.path.get(0).getY();
		for (int i = 1; i < path.size(); i++) {
			pathString += " L " + (int) this.path.get(i).getX() + " " + (int) this.path.get(i).getY();
		}
		EventData event =
				new EventData("homingPath").addAttribute("id", this.getId()).addAttribute("path",
						pathString);
		this.notifyEventObserver(event, beginTime);
	}
}
