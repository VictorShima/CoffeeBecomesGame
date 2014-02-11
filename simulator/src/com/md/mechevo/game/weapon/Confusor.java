package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class Confusor extends Weapon {
	public Confusor(Player player) {
		super(10, 8, player );
	}

    @Override
    public void fire(State state, Solid target) {
        
    }
}
