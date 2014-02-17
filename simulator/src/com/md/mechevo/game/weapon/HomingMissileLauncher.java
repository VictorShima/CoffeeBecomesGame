package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.HomingMissile;
import com.md.mechevo.game.projectile.Projectile;

public class HomingMissileLauncher extends Weapon {
	public HomingMissileLauncher(Player player) {
		super(20, 5, player);
	}

	@Override
	public void fire(State state, Solid target) {
		if (target != null) {
			double angle = super.getAngleToTarget(target);
			Projectile proj =
					new HomingMissile(state.getNextId(), this.getOwner().getPosition(), this
							.getOwner().getAngle() + angle, this, target);
			state.addProjectile(proj);
		}
	}
}
