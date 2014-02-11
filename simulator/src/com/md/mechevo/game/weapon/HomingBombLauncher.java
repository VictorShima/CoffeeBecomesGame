package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class HomingBombLauncher extends Weapon {
	public HomingBombLauncher(Player player) {
		super(50, 12, player );
	}

    @Override
    public void fire(State state, Solid target) {

    }
}
