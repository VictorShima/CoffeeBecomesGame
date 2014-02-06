package com.md.mechevo.game.projectile;

import com.md.mechevo.game.*;
import com.md.mechevo.game.sentry.Sentry;

public abstract class Projectile extends Solid implements CollisionVisitor {
	protected Projectile(Position position, float width, float height, float speed, float angle) {
		super(position, width, height, speed, angle);
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

	public void play(State state) {

	}
}
