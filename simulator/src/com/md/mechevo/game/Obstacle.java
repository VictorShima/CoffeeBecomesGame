package com.md.mechevo.game;

import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public abstract class Obstacle extends Solid implements CollisionVisitor {
	protected Obstacle(Position centerPosition, float width, float height, float speed,
					int health, float angle) {
		super(centerPosition, width, height, speed, angle);
	}

	@Override
	public abstract void collidesWith(Player p);

	@Override
	public abstract void collidesWith(Projectile p);

	@Override
	public abstract void collidesWith(Obstacle o);

	@Override
	public abstract void collidesWith(Sentry s);

	@Override
	public void accept(CollisionVisitor s) {
		s.collidesWith(this);
	}
}
