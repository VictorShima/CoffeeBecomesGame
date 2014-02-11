package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class HomingMissileLauncher extends Weapon {
	public HomingMissileLauncher(Player player) {
		super(20, 5, player);
	}

    @Override
    public void fire(State state, Solid target) {

    }
}
