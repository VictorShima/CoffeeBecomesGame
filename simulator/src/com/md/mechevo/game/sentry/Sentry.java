package com.md.mechevo.game.sentry;

import com.md.mechevo.game.*;
import com.md.mechevo.game.projectile.Projectile;

public abstract class Sentry extends Solid {
	private Player owner;
	private int damage;
	private double cooldown;
	private double timeToLive;

	protected Sentry(int id, Position position, double radius, double speed, double angle,
			Player owner, double timeToLive, int damage) {
		super(id, position, radius, speed, angle);
		this.owner = owner;
		this.timeToLive = timeToLive;
		this.damage = damage;
	}

	public double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
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

	public double getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(double timeToLive) {
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

	@Override
	public void update(State state, double dtime) {
		this.timeToLive -= dtime;

		if (this.timeToLive <= 0) {
			owner.removeSentry(this);
			return;
		}

		// TODO
	}
}
