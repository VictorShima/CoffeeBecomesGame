package com.md.mechevo.game.weapon;

import com.md.mechevo.game.*;

public abstract class Weapon implements EventObservable {
	private Player player;
	private int damage;
	private float cooldown;
	// TODO: maybe specify which slots the weapon may be used
	// private ArrayList<WeaponSlot> possibleSlots;

	// Variable weapon attributes
	private float currentCooldown;
	private WeaponSlot currentSlot;

	private EventObserver report;


	protected Weapon(int damage, float cooldown, Player player) {
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

	public float getCooldown() {
		return cooldown;
	}

	public float getAngleToTarget(Solid target) {
		// TODO Implement this
		return 0f;
	}

	public abstract void fire(State state, Solid target);

	public float getCurrentCooldown() {
		return this.currentCooldown;
	}

	public void setCurrentCooldown(float currentCooldown) {
		this.currentCooldown = currentCooldown;
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
