package com.md.mechevo.game.projectile;

import com.md.mechevo.game.Position;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.weapon.Weapon;

public class HomingBomb extends HomingProjectile {
	public static final double RADIUS = 30;
	public static final double SPEED = 30;
	public static final double ROT_VEL = 7;

	public HomingBomb(int id, Position position, double angle, Weapon weapon, Solid target) {
		super(id, position, RADIUS, SPEED, angle, weapon, target, ROT_VEL);
	}
}
