package com.md.mechevo.game;


/**
 * Weapon Slot holds the Weapon for a player, specifiyng additional data such as current
 * cooldown and slot.
 */
public class WeaponSlot {
	
	private Weapon weapon;
	private float currentCooldown;
	private int slot;
	
	/**
	 * Basic Constructor
	 */
	public WeaponSlot(Weapon weapon, int slot) {
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
	public int slot() {
		return this.slot;
	}
	
	
}
