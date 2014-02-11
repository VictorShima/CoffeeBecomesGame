package com.md.mechevo.game.weapon;


import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

public class MineLauncher extends Weapon {
	public MineLauncher(Player player) {
		super(50, 7, player );
	}

    @Override
    public void fire(State state, Solid target) {

    }
}
