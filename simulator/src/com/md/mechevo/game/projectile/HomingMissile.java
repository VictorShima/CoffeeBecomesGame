package com.md.mechevo.game.projectile;

import com.md.mechevo.game.Position;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.weapon.Weapon;

public class HomingMissile extends HomingProjectile {
	public static final int INITIAL_WIDTH = 30;
	public static final int INITIAL_HEIGHT = 30;
	public static final int INITIAL_SPEED = 30;
	public static final float INITIAL_ROTVEL = 7;

	public HomingMissile(int id, Position position, float angle, Weapon weapon, Solid target) {
		super(id, position, INITIAL_WIDTH, INITIAL_HEIGHT, INITIAL_SPEED, angle, weapon, target,
						INITIAL_ROTVEL);
	}

}
