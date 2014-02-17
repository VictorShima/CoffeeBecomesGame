package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.HomingBomb;
import com.md.mechevo.game.projectile.Projectile;

public class HomingBombLauncher extends Weapon {
	public HomingBombLauncher(Player player) {
		super(50, 12, player);
	}

	@Override
	public void fire(State state, Solid target) {
		double angle = super.getAngleToTarget(target);
		Projectile proj =
				new HomingBomb(state.getNextId(), this.getPlayer().getPosition(), this.getPlayer().getAngle() + angle, this,
						target);
		state.addProjectile(proj);
	}
}
