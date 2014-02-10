package com.md.mechevo.game.weapon;

import com.md.mechevo.game.State;

public abstract class Weapon {
	private int damage;
	private float cooldown;

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

	public void fire(State state) {
		// TODO
	}
	
	
	
	
}
