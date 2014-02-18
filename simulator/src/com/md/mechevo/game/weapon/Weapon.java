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
			// VecRight is a vector pointing right
			double vecRightX = -20;
			double vecRightY = 0;
			double vecX = this.getOwner().getPosition().getX() - target.getPosition().getX();
			double vecY = this.getOwner().getPosition().getY() - target.getPosition().getY();
			double cosValue =
					((vecX * vecRightX) + (vecY * vecRightY))
							/ (Math.sqrt(Math.pow(vecRightX, 2) + Math.pow(vecRightY, 2)) * Math
									.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2)));

			double angle = Math.toDegrees(Math.acos(cosValue));

			// Rotate clockwise/counter-clockwise is determined by sign of cross-product
			double crossProd = (vecX * vecRightY) - (vecX * vecRightX);
			return (crossProd < 0) ? angle : -angle;
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
