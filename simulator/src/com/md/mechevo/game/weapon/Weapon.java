package com.md.mechevo.game.weapon;

import com.md.mechevo.game.*;

public abstract class Weapon implements EventObservable {
	private Player owner;
	private int damage;
	private double cooldown;
	private double currentCooldown;
	private WeaponSlot currentSlot;
	private EventObserver report;

	protected Weapon(int damage, double cooldown, Player owner) {
		this.damage = damage;
		this.cooldown = cooldown;
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player player) {
		this.owner = player;
	}

	public int getDamage() {
		return damage;
	}

	public double getCooldown() {
		return cooldown;
	}

	public double getAngleToTarget(Solid target) {
		// If target is null then return the player's angle
		if (target != null) {
			//rightPoint is a point always 20px to the absolute right of the player
			double rightPointX = this.getOwner().getPosition().getX() + 20;
			double rightPointY = this.getOwner().getPosition().getY();

			double distBA = Math.sqrt(Math.pow((this.getOwner().getPosition().getX() - target.getPosition().getX()), 2) + (Math.pow((this.getOwner().getPosition().getY() - target.getPosition().getY()), 2)));
			double distBC = Math.sqrt(Math.pow((this.getOwner().getPosition().getX() - rightPointX), 2) + (Math.pow((this.getOwner().getPosition().getY() - rightPointY), 2)));
			double dotProd = ((rightPointX - this.getOwner().getPosition().getX()) * (target.getPosition().getX() - this.getOwner().getPosition().getX())
					+ (rightPointY - this.getOwner().getPosition().getY()) * (target.getPosition().getY() - this.getOwner().getPosition().getY()));
			double cosValue = (dotProd / (distBA * distBC));
			double angle = Math.toDegrees(Math.acos(cosValue));

			// Rotate clockwise/counter-clockwise is determined by sign of cross-product
			double crossProd = ((rightPointX - this.getOwner().getPosition().getX()) * (target.getPosition().getY() - this.getOwner().getPosition().getY()) - (rightPointY - this.getOwner().getPosition().getY()) * (target.getPosition().getX() - this.getOwner().getPosition().getX()));

			return (crossProd > 0) ? angle : -angle;
		} else {
			return owner.getAngle();
		}
	}

	public abstract void fire(State state, Solid target);

	public double getCurrentCooldown() {
		return this.currentCooldown;
	}

	public void updateCurrentCooldown(double dtime) {
		this.currentCooldown -= dtime;
		if (this.currentCooldown < 0) {
			this.currentCooldown = 0;
		}
	}

	public void increaseCurrentcooldown() {
		this.currentCooldown += this.cooldown;
	}

	public WeaponSlot getCurrentSlot() {
		return this.currentSlot;
	}

	public void setCurrentSlot(WeaponSlot currentSlot) {
		this.currentSlot = currentSlot;
	}

	// interface EventObservable
	public void registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
	}

	public void notifyEventObserver(EventData eventData) {
		if (this.report != null) {
			this.report.notify(eventData);
		}
	}

	/**
	 * Maps the Slot name to a number
	 */
	public static enum WeaponSlot {
		LEFT, RIGHT, CENTER
	}

}
