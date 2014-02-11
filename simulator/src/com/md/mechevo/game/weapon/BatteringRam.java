package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class BatteringRam extends Weapon {
	public BatteringRam(Player player) {
		super(30, 1, player );
	}

    @Override
    public void fire(State state, Solid target) {
        
    }
}
