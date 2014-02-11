package com.md.mechevo.game;

import com.md.mechevo.game.weapon.Weapon;


/**
 * Weapon Slot holds the Weapon for a player, specifiyng additional data such as current cooldown
 * and slot.
 */
public class WeaponSlot {

	private Weapon weapon;
	private float currentCooldown;
	private SlotNumber slot;

	/**
	 * Basic Constructor
	 */
	public WeaponSlot(Weapon weapon, SlotNumber slot) {
		this.weapon = weapon;
		this.slot = slot;
		this.currentCooldown = 0;
	}


	/**
	 * Get the weapon
	 */
	public Weapon getWeapon() {
		return this.weapon;
	}


	/**
	 * Get the current Cooldown
	 */
	public float currentCooldown() {
		return this.currentCooldown;
	}


	/**
	 * Get the weapon slot
	 */
	public SlotNumber slot() {
		return this.slot;
	}


	/**
	 * Maps the Slot name to a number
	 */
	public static enum SlotNumber {
		LEFT, RIGHT, CENTER
	}


}
