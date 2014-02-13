package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;

public class ActionFactory {
	public static Action createAction(String name, Player player, String param) {
		switch (name) {
			case "Attack":
				return new Attack(player, param);
            case "Dash":
                return new Dash(player, param);
            case "FaceOpponent":
                return new FaceOpponent(player);
			case "MoveInLine":
				return new MoveInLine(player, param);
            case "MoveNearObstacle":
                return new MoveNearObstacle(player);
            case "MoveToEnemy":
                return new MoveToEnemy(player);
            case "Turn":
                return new Turn(player, param);
			default:
				throw new UnknownAction(name);
		}
	}
}
