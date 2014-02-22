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
