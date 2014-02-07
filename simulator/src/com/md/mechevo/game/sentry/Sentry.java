package com.md.mechevo.game.sentry;

import com.md.mechevo.game.*;
import com.md.mechevo.game.projectile.Projectile;

public abstract class Sentry extends Solid implements CollisionVisitor {
	private Player owner;
	private int damage;
	private float cooldown;
	private float timeToLive;

	protected Sentry(Position position, float width, float height, float speed, float angle,
					Player owner, float timeToLive, int damage) {
		super(width, height, speed, angle);
		super.setPosition(position);

		this.owner = owner;
		this.timeToLive = timeToLive;
		this.damage = damage;
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}

	public Player getOwner() {
		return owner;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public float getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(float timeToLive) {
		this.timeToLive = timeToLive;
	}

	@Override
	public abstract void collidesWith(State state, Player p);

	@Override
	public abstract void collidesWith(State state, Projectile p);

	@Override
	public abstract void collidesWith(State State, Obstacle o);

	@Override
	public abstract void collidesWith(State state, Sentry s);

	@Override
	public void accept(CollisionVisitor s, State state) {
		s.collidesWith(state, this);
	}

	private void updateTTL(float time) {
		timeToLive -= time;
	}

	private boolean timeToDie() {
		return timeToLive <= 0;
	}

	public void update(State state) {
		this.updateTTL(state.getTime());
		if (this.timeToDie()) {
			owner.removeSentry(this);
			return;
		}

		// TODO
	}
}
