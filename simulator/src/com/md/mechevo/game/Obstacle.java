package com.md.mechevo.game;

import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public class Obstacle extends Solid {
	protected Obstacle(int id, Position centerPosition, float width, float height, float speed,
					int health, float angle) {
		super(width, height, speed, angle, id);
		super.setPosition(centerPosition);
	}


	public void begin(State state) {
		// TODO: notify it is created
	}

	public void update(State state, float dtime) {}

	public void end(State state) {}



	@Override
	public void collidesWith(State state, Player p) {}

	@Override
	public void collidesWith(State state, Projectile p) {}

	@Override
	public void collidesWith(State State, Obstacle o) {}

	@Override
	public void collidesWith(State state, Sentry s) {}

	@Override
	public void accept(CollisionVisitor s, State state) {
		s.collidesWith(state, this);
	}
}
