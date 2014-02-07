package com.md.mechevo.game.projectile;

import com.md.mechevo.game.*;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public abstract class Projectile extends Solid implements CollisionVisitor {
	private Weapon weapon;


	protected Projectile(Position position, float width, float height, float speed, float angle,
					Weapon weapon) {
		super(width, height, speed, angle);
		super.setPosition(position);
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
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
