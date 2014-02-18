package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.ParalyseShot;
import com.md.mechevo.game.projectile.Projectile;

public class ParalyseShotLauncher extends Weapon {
	public ParalyseShotLauncher(Player player) {
		super(15, 5, player);
	}

	@Override
	public void fire(State state, Solid target) {
		Projectile proj =
				(target != null)
						? new ParalyseShot(state.getNextId(), this.getOwner().getPosition(), this
								.getOwner().getAngle() + super.getAngleToTarget(target), this)
						: new ParalyseShot(state.getNextId(), this.getOwner().getPosition(), this
								.getOwner().getAngle(), this);
		proj.registerEventObserver(state.getReport());
		proj.begin(state);
		state.addProjectile(proj);

		this.increaseCurrentcooldown();
	}
}
