package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class MineDetector extends Weapon {
	public MineDetector(Player player) {
		super(0, 0, player);
	}

    @Override
    public void fire(State state, Solid target) {
        //TODO
    }
}
