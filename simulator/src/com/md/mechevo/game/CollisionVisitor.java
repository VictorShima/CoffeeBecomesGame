package com.md.mechevo.game;

public interface CollisionVisitor {
	void collidesWith(Player p);

	void collidesWith(Projectile p);

	void collidesWith(Obstacle o);

	void collidesWith(Sentry s);
}
