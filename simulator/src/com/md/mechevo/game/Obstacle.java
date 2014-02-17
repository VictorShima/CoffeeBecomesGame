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
		EventData event =
				new EventData("createObstacle")
						.addAttribute("id", this.getId())
						.addAttribute("x", this.getPosition().getX())
						.addAttribute("y", this.getPosition().getY());
		this.notifyEventObserver(event);
	}

	@Override
	public void end(State state) {
		EventData event =
				new EventData("eraseObstacle").addAttribute("id", this.getId());
		this.notifyEventObserver(event);
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
