package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;

public class ActionFactory {
	public static Action createAction(String name, Player player, String param) {
		switch (name) {
			case "AttackAction":
				return new AttackAction(player, param);
			case "MoveStraightAction":
				return new MoveStraightAction(player, param);
			default:
				throw new UnknownAction(name);
		}
	}
}
