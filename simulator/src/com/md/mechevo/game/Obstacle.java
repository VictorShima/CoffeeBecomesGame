package com.md.mechevo.game;

import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public class Obstacle extends Solid {
	private static final double SPEED = 0;
	private static final double ANGLE = 0;

	public Obstacle(int id, Position position, double radius) {
		super(id, position, radius, Obstacle.SPEED, Obstacle.ANGLE);
	}

	@Override
	public void begin(State state) {
		// TODO: notify when it's created
	}

	@Override
	public void end(State state) {
		// TODO: notify when it's destroyed
	}

	@Override
	public void update(State state, double dtime) {
		// Empty on purpose
	}


	@Override
	public void collidesWith(State state, Player p) {
		// Empty on purpose
	}

	@Override
	public void collidesWith(State state, Projectile p) {
		// Empty on purpose
	}

	@Override
	public void collidesWith(State State, Obstacle o) {
		// Empty on purpose
	}

	@Override
	public void collidesWith(State state, Sentry s) {
		// Empty on purpose
	}

	@Override
	public void accept(CollisionVisitor s, State state) {
		s.collidesWith(state, this);
	}
}
