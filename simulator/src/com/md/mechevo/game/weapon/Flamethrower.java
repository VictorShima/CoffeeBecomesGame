package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class Flamethrower extends Weapon {
	public Flamethrower(Player player) {
		super(20, 0.3f, player );
	}

    @Override
    public void fire(State state, Solid target) {
        
    }
}
