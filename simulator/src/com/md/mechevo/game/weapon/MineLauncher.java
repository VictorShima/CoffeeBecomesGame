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
		//double angle = super.getAngleToTarget(target);
		Projectile proj = new Mine(state.getNextId(), this.getPlayer().getPosition(), this.getPlayer().getAngle()/* + angle*/, this);
		state.addProjectile(proj);

	}
}
