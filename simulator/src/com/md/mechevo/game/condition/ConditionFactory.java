package com.md.mechevo.game.condition;

import java.util.ArrayList;

import com.md.mechevo.game.Player;

public class ConditionFactory {
	public static Condition createCondition(String name, Player player, ArrayList<String> param) {
		switch (name) {
			case "DistanceToEnemy":
				return new DistanceToEnemy(player, param);
			case "EnemyDisappear":
				return new EnemyDisappear(player);
			case "EnemySpotted":
				return new EnemySpotted(player, param);
			case "ReceivedDamage":
				return new ReceivedDamage(player, param);
			case "TrueCondition":
				return new TrueCondition(player);
			case "WeaponReady":
				return new WeaponReady(player, param);
			default:
				throw new UnknownCondition(name);
		}
	}
}
