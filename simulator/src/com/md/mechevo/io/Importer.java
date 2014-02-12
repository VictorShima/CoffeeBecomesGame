package com.md.mechevo.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.md.mechevo.game.*;
import com.md.mechevo.game.action.Action;
import com.md.mechevo.game.action.ActionFactory;
import com.md.mechevo.game.ai.AIAlgorithm;
import com.md.mechevo.game.ai.AIEntry;
import com.md.mechevo.game.condition.Condition;
import com.md.mechevo.game.condition.ConditionFactory;
import com.md.mechevo.game.weapon.Weapon;
import com.md.mechevo.game.weapon.WeaponFactory;

/**
 * Imports the data received in JSON and converts to Java Objects
 */
public final class Importer {
	/**
	 * Creates the initial state from the data received in JSON. The data must be in the following
	 * structure: 
	 *   {
	 *     "map" : { "width":double, "height":double },
	 *     "obstacles : [ { "x":double, "y":double, "radius":double }, ... ],
	 *     "players" : [ {
	 *       "teamId":int, 
	 *       "weapon":[string,string,string],
	 *       "color":string,
	 *       "x":double,
	 *       "y":double,
	 *       "algorithm": [ { 
	 *         "conditions": [ { "name":string, "param":string }, ... ],
	 *         "actions": [ { "name":string, "param":string }, ... ]
	 *       }]
	 *     }]
	 *   }
	 * 
	 * @param json the data in JSON
	 * @return the initial state
	 */
	public static State createInitialState(String json) {
		// Generate JSON tree
		JsonParser parser = new JsonParser();
		JsonObject tree = parser.parse(json).getAsJsonObject();

		// Deal with map
		JsonObject mapJson = tree.get("map").getAsJsonObject();
		double mapWidth = mapJson.get("width").getAsDouble();
		double mapHeight = mapJson.get("height").getAsDouble();
		Map map = new Map(mapWidth, mapHeight);
		State state = new State(map);

		// Deal with obstacles
		JsonArray obstaclesJson = tree.get("obstacles").getAsJsonArray();
		for (int obstacleId = 0; obstacleId < obstaclesJson.size(); obstacleId++) {
			JsonObject obstacleJson = obstaclesJson.get(obstacleId).getAsJsonObject();
			double obstacleX = obstacleJson.get("x").getAsDouble();
			double obstacleY = obstacleJson.get("y").getAsDouble();
			double obstacleRadius = obstacleJson.get("radius").getAsDouble();
			Obstacle obstacle =
					new Obstacle(state.getNextId(), new Position(obstacleX, obstacleY),
							obstacleRadius);
			map.addSolid(obstacle);
		}

		// Deal with Player
		JsonArray players = tree.get("players").getAsJsonArray();
		for (int playerId = 0; playerId < players.size(); playerId++) {
			// Enough arguments to create a Player object
			JsonObject playerJson = players.get(playerId).getAsJsonObject();
			int teamId = playerJson.get("teamId").getAsInt();
			double playerX = playerJson.get("x").getAsDouble();
			double playerY = playerJson.get("y").getAsDouble();
			Player player = new Player(state.getNextId(), teamId, new Position(playerX, playerY));
			state.addPlayer(player);

			// Deal with weapons
			JsonArray weaponJson = playerJson.get("weapons").getAsJsonArray();
			for (int weaponId = 0; weaponId < weaponJson.size(); weaponId++) {
				Weapon weapon =
						WeaponFactory.createWeapon(weaponJson.get(weaponId).getAsString(), player);
				player.equipWeapon(weapon, Weapon.WeaponSlot.values()[weaponId]);
			}

			// Deal with AI algorithm
			AIAlgorithm algorithm = new AIAlgorithm(player);
			player.setAlgorithm(algorithm);
			JsonArray algorithmJson = playerJson.get("algorithm").getAsJsonArray();
			for (int entryId = 0; entryId < algorithmJson.size(); entryId++) {
				AIEntry entry = new AIEntry(algorithm);
				algorithm.addEntry(entry);

				// Deal with AI Conditions
				JsonArray conditions =
						algorithmJson.get(entryId).getAsJsonObject().get("conditions")
								.getAsJsonArray();
				for (int conditionId = 0; conditionId < conditions.size(); conditionId++) {
					JsonObject conditionJson = conditions.get(conditionId).getAsJsonObject();
					Condition condition =
							ConditionFactory.createCondition(conditionJson.get("name")
									.getAsString(), player, conditionJson.get("param")
									.getAsString());
					entry.addCondition(condition);
				}

				// Deal with AI Actions
				JsonArray actions =
						algorithmJson.get(entryId).getAsJsonObject().get("actions")
								.getAsJsonArray();
				for (int actionId = 0; actionId < actions.size(); actionId++) {
					JsonObject actionJson = actions.get(actionId).getAsJsonObject();
					Action action =
							ActionFactory.createAction(actionJson.get("name").getAsString(),
									player, actionJson.get("param").getAsString());
					entry.addAction(action);
				}
			}
		}

		return state;
	}
}
