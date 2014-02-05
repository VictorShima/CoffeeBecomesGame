package com.md.mechevo.game;

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

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}

	public void play(float time) {
		// TODO
	}
}
