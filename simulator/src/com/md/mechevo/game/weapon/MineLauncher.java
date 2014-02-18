package com.md.mechevo.game.weapon;


import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.Mine;
import com.md.mechevo.game.projectile.Projectile;

public class MineLauncher extends Weapon {
	public MineLauncher(Player player) {
		super(50, 7, player);
	}

	@Override
	public void fire(State state, Solid target) {
		Projectile proj =
				new Mine(state.getNextId(), this.getOwner().getPosition(), this.getOwner()
						.getAngle(), this);
		proj.registerEventObserver(state.getReport());
		proj.begin(state);
		state.addProjectile(proj);

		this.increaseCurrentcooldown();
	}
}
