package com.md.mechevo.game.projectile;

import com.md.mechevo.game.State;

import com.md.mechevo.game.Obstacle;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.Position;
import com.md.mechevo.game.State;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class Bullet extends Projectile {
	public static final int INITIAL_WIDTH = 30;
	public static final int INITIAL_HEIGHT = 30;
	public static final int INITIAL_SPEED = 30;

	public Bullet(int id, Position position, float angle, Weapon weapon) {
		super(id, position, INITIAL_WIDTH, INITIAL_HEIGHT, INITIAL_SPEED, angle, weapon);
	}



	public void update(State state, float dtime) {
        super.moveForward(this.getAngle(), this.getSpeed(), dtime);
    }


	@Override
	public void collidesWith(State state, Player p) {
		p.takeDamage(this.getWeapon().getDamage());
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
