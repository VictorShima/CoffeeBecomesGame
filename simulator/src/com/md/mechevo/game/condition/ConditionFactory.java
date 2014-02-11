package com.md.mechevo.game.condition;

import com.md.mechevo.game.Player;

public class ConditionFactory {
	public static Condition createCondition(String name, Player player, String param) {
		switch (name) {
			case "TrueCondition":
				return new TrueCondition(player, param);
			default:
				throw new UnknownCondition(name);
		}
	}
}
