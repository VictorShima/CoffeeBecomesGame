package com.md.mechevo.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;
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
	 * structure: { "players": [ {"teamId":"0", "weapon":["1","2","3"], "algorithm": [{"conditions":
	 * [{"name": "1", "param": "2"}], "actions": [{"name": "1", "param": "2"}]}] } ] }
	 * 
	 * @param json the data in JSON
	 * @return the initial state
	 */
	public static State createInitialState(String json) {
		State state = new State();

		JsonParser parser = new JsonParser();
		JsonObject tree = parser.parse(json).getAsJsonObject();
		JsonArray players = tree.get("players").getAsJsonArray();
		for (int playerId = 0; playerId < players.size(); playerId++) {
			JsonObject playerJson = players.get(playerId).getAsJsonObject();

			int teamId = playerJson.get("teamId").getAsInt();
			Player player = new Player(state.getNextId(), teamId);
			state.addPlayer(player);

			JsonArray weaponJson = playerJson.get("weapons").getAsJsonArray();
			for (int weaponId = 0; weaponId < weaponJson.size(); weaponId++) {
				Weapon weapon =
								WeaponFactory.createWeapon(weaponJson.get(weaponId).getAsString(),
												player);
				player.equipWeapon(weapon, Weapon.WeaponSlot.values()[weaponId]);
			}

			AIAlgorithm algorithm = new AIAlgorithm(player);
			player.setAlgorithm(algorithm);
			JsonArray algorithmJson = playerJson.get("algorithm").getAsJsonArray();
			for (int entryId = 0; entryId < algorithmJson.size(); entryId++) {
				AIEntry entry = new AIEntry(algorithm);
				algorithm.addEntry(entry);

				JsonArray conditions =
								algorithmJson.get(entryId).getAsJsonObject().get("conditions")
												.getAsJsonArray();
				for (int conditionId = 0; conditionId < conditions.size(); conditionId++) {
					JsonObject conditionJson = conditions.get(conditionId).getAsJsonObject();
					Condition condition =
									ConditionFactory.createCondition(conditionJson.get("name")
													.getAsString(), player,
													conditionJson.get("param").getAsString());
					entry.addCondition(condition);
				}

				JsonArray actions =
								algorithmJson.get(entryId).getAsJsonObject().get("actions")
												.getAsJsonArray();
				for (int actionId = 0; actionId < actions.size(); actionId++) {
					JsonObject actionJson = actions.get(actionId).getAsJsonObject();
					Action action =
									ActionFactory.createAction(
													actionJson.get("name").getAsString(), player,
													actionJson.get("param").getAsString());
					entry.addAction(action);
				}
			}
		}

		return state;
	}
}
