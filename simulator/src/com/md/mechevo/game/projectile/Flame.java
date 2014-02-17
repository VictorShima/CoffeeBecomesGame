package com.md.mechevo.game.projectile;

import com.md.mechevo.game.Obstacle;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.Position;
import com.md.mechevo.game.State;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class Flame extends Projectile {
	public static final double RADIUS = 15;
	public static final double SPEED = 15;

	public Flame(int id, Position position, double angle, Weapon weapon) {
		super(id, position, RADIUS, SPEED, angle, weapon);
	}

	public void update(State state, double dtime) {
		super.move(this.getAngle(), this.getSpeed(), dtime, true);
	}


	@Override
	public void collidesWith(State state, Player p) {
		if (p.getId() != this.getWeapon().getOwner().getId()) {
			p.takeDamage(this.getWeapon().getDamage(), this.getAngle());
			this.setDestroyed(true);
		}
	}

	@Override
	public void collidesWith(State state, Projectile p) {}

	@Override
	public void collidesWith(State State, Obstacle o) {
		this.setDestroyed(true);
	}

	@Override
	public void collidesWith(State state, Sentry s) {}
}
