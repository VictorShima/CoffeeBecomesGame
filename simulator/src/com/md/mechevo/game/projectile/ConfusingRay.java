package com.md.mechevo.game.projectile;

import com.md.mechevo.game.Obstacle;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.Position;
import com.md.mechevo.game.State;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class ConfusingRay extends Projectile {
	public static final double RADIUS = 30;
	public static final double SPEED = 30;

	public ConfusingRay(int id, Position position, double angle, Weapon weapon) {
		super(id, position, RADIUS, SPEED, angle, weapon);
	}

	@Override
	public void update(State state, double dtime) {
		super.move(this.getAngle(), this.getSpeed(), dtime, true);
	}

	@Override
	public void collidesWith(State state, Player p) {
		p.takeDamage(this.getWeapon().getDamage(), this.getAngle());
		p.confuse();
		this.setDestroyed(true);
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
