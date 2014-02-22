package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.Missile;
import com.md.mechevo.game.projectile.Projectile;

public class RocketLauncher extends Weapon {
	private static final int DAMAGE = 40;
	private static final int COOLDOWN = 10;

	public RocketLauncher(Player owner) {
		super(RocketLauncher.DAMAGE, RocketLauncher.COOLDOWN, owner);
	}

	@Override
	public void fire(State state, Solid target) {
		Projectile proj =
				new Missile(state.getNextId(), this.getOwner().getPosition(), this.getOwner()
						.getAngle(), this);
		proj.registerEventObserver(state.getReport());
		proj.begin(state);
		state.addProjectile(proj);

		this.increaseCurrentcooldown();
	}
}
