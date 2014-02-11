package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class NullWeapon extends Weapon {
	private static final int DAMAGE = 0;
	private static final int COOLDOWN = 0;

	public NullWeapon(Player player) {
		super(DAMAGE, COOLDOWN, player);
	}

	@Override
	public void fire(State state, Solid target) {
		// empty on purpose
	}
}
