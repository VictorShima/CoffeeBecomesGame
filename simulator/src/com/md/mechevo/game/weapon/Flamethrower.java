package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.Flame;
import com.md.mechevo.game.projectile.Projectile;

public class Flamethrower extends Weapon {
	public Flamethrower(Player player) {
		super(20, 0.3f, player);
	}

	@Override
	public void fire(State state, Solid target) {
		Projectile proj =
				(target != null) ? new Flame(state.getNextId(), this.getOwner().getPosition(), this
						.getOwner().getAngle() + super.getAngleToTarget(target), this) : new Flame(
						state.getNextId(), this.getOwner().getPosition(), this.getOwner()
								.getAngle(), this);
		proj.registerEventObserver(state.getReport());
		proj.begin(state);
		state.addProjectile(proj);

		this.increaseCurrentcooldown();
	}
}
