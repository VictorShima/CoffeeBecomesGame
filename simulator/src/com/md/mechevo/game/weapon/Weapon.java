package com.md.mechevo.game.weapon;

import com.md.mechevo.game.*;

public abstract class Weapon implements EventObservable {
	private Player player;
	private int damage;
	private double cooldown;
	// TODO: maybe specify which slots the weapon may be used
	// private ArrayList<WeaponSlot> possibleSlots;

	// Variable weapon attributes
	private double currentCooldown;
	private WeaponSlot currentSlot;

	private EventObserver report;


	protected Weapon(int damage, double cooldown, Player player) {
		this.damage = damage;
		this.cooldown = cooldown;
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getDamage() {
		return damage;
	}

	public double getCooldown() {
		return cooldown;
	}

	public double getAngleToTarget(Solid target) {
		// TODO Implement this
		return 0f;
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
