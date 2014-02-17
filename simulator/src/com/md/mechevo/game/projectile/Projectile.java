package com.md.mechevo.game.projectile;

import com.md.mechevo.game.*;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public abstract class Projectile extends Solid implements EventObservable {
	private Weapon weapon;

	protected Projectile(int id, Position position, double radius, double speed, double angle,
			Weapon weapon) {
		super(id, position, radius, speed, angle);
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * Notify the report that it has been created.
	 */
	public void begin(State state) {
		if (this.getReport() != null) {
			EventData event =
					new EventData("createProjectile")
							.addAttribute("id", this.getId())
							.addAttribute("x", this.getPosition().getX())
							.addAttribute("y", this.getPosition().getY())
							.addAttribute("type", this.getClass().getName());
			this.notifyEventObserver(event);
		}
	}

	/**
	 * Notify the report that it has been destroyed.
	 */
	public void end(State state) {
		if (this.getReport() != null) {
			EventData event =
					new EventData("eraseProjectile").addAttribute("id", this.getId());
			this.notifyEventObserver(event);
		}
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
}
