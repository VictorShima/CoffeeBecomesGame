package com.md.mechevo.game;

import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public interface CollisionVisitor {
	void collidesWith(State state, Player p);

	void collidesWith(State state, Projectile p);

	void collidesWith(State State, Obstacle o);

	void collidesWith(State state, Sentry s);
}
