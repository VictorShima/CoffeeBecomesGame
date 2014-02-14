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
		//If target is null then return the player's angle
		if(target != null){
			//VecRight is a vector pointing right
			double vecRightX = -20;
			double vecRightY = 0;
			double vecX = this.getPlayer().getPosition().getX() - target.getPosition().getX();
			double vecY = this.getPlayer().getPosition().getY() - target.getPosition().getY();
			double cosValue = ((vecX * vecRightX) + (vecY * vecRightY)) / (Math.sqrt(Math.pow(vecRightX, 2) + Math.pow(vecRightY, 2))
					* Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2)));

			double angle = Math.acos(cosValue);
			return angle;
		} else {
			return player.getAngle();
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
