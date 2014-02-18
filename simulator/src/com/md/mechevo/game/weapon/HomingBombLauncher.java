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
		if (target != null) {
			Projectile proj =
					new HomingBomb(state.getNextId(), this.getOwner().getPosition(), this
							.getOwner().getAngle() + super.getAngleToTarget(target), this, target);
			proj.registerEventObserver(state.getReport());
			proj.begin(state);
			state.addProjectile(proj);

			this.increaseCurrentcooldown();
		}
	}
}
