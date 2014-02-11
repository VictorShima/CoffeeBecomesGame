package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class ParalyseShotLauncher extends Weapon {
	public ParalyseShotLauncher(Player player) {
		super(15, 5, player );
	}

    @Override
    public void fire(State state, Solid target) {

    }
}
