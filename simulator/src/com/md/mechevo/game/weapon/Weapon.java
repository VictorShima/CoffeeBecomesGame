package com.md.mechevo.game.weapon;

import java.util.ArrayList;

import com.md.mechevo.game.State;

import com.md.mechevo.game.EventObserver;
import com.md.mechevo.game.EventObservable;
import com.md.mechevo.game.EventData;

public abstract class Weapon implements EventObservable {

	// Static weapon attributes
	private int damage;
	private float cooldown;
	// TODO: maybe specify which slots the weapon may be used
	//private ArrayList<WeaponSlot> possibleSlots;
	
	// Variable weapon attributes
	private float currentCooldown;
	private WeaponSlot currentSlot;
	
	private EventObserver report;
	

	protected Weapon(int damage, float cooldown) {
		this.damage = damage;
		this.cooldown = cooldown;
	}

	public int getDamage() {
		return damage;
	}

	public float getCooldown() {
		return cooldown;
	}
	
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
	

	public abstract void fire(State state);
	
	
	
	// interface EventObservable
	
	public void registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
	}
	
	public void notifyEventObserver(EventData eventData) {
		if ( this.report != null ) {
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
