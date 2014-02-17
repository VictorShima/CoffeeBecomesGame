package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.ConfusingRay;
import com.md.mechevo.game.projectile.Projectile;

public class Confusor extends Weapon {
	public Confusor(Player player) {
		super(10, 8, player);
	}

	@Override
	public void fire(State state, Solid target) {
		if (target != null) {
			double angle = super.getAngleToTarget(target);
			Projectile proj =
					new ConfusingRay(state.getNextId(), this.getPlayer().getPosition(), this
							.getPlayer().getAngle() + angle, this);
			state.addProjectile(proj);
		} else {
			Projectile proj =
					new ConfusingRay(state.getNextId(), this.getPlayer().getPosition(), this
							.getPlayer().getAngle(), this);
			state.addProjectile(proj);
		}

	}
}
