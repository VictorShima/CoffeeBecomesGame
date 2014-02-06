package com.md.mechevo.game;

import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public interface CollisionVisitor {
	void collidesWith(Player p);

	void collidesWith(Projectile p);

	void collidesWith(Obstacle o);

	void collidesWith(Sentry s);
}
