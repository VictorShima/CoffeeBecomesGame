package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class TurretLauncher extends Weapon {
	public TurretLauncher(Player player) {
		super(8, 12, player);
	}

	@Override
	public void fire(State state, Solid target) {
        //TODO
	}
}
