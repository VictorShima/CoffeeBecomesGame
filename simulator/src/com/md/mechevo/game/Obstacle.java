package com.md.mechevo.game;

import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public abstract class Obstacle extends Solid implements CollisionVisitor {
	protected Obstacle(Position centerPosition, float width, float height, float speed,
					int health, float angle) {
		super(centerPosition, width, height, speed, angle);
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
