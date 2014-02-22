package com.md.mechevo.game.weapon;


import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;
import com.md.mechevo.game.projectile.Bullet;
import com.md.mechevo.game.projectile.Projectile;

public class Minigun extends Weapon {
	private static final int DAMAGE = 20;
	private static final double COOLDOWN = 0.5;

	public Minigun(Player player) {
		super(Minigun.DAMAGE, Minigun.COOLDOWN, player);
	}

	public void fire(State state, Solid target) {
		Projectile proj =
				new Bullet(state.getNextId(), this.getOwner().getPosition(), this.getOwner()
						.getAngle(), this);
		proj.registerEventObserver(state.getReport());
		proj.begin(state);
		state.addProjectile(proj);

		this.increaseCurrentcooldown();
	}
}
