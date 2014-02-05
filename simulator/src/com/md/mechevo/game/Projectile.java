package com.md.mechevo.game;

public abstract class Projectile extends Solid implements CollisionVisitor {
	public Projectile(float width, float height, Coordinate initialCoordinate) {
		super(width, height, initialCoordinate);
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
	void accept(CollisionVisitor s) {
		s.collidesWith(this);
	}
}
