package com.md.mechevo.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;
import com.md.mechevo.game.action.Action;
import com.md.mechevo.game.action.ActionDeserializer;
import com.md.mechevo.game.ai.AIAlgorithm;
import com.md.mechevo.game.condition.Condition;
import com.md.mechevo.game.condition.ConditionDeserializer;

/**
 * Imports the data received in JSON and converts to Java Objects
 */
public final class Importer {
	private static final int NOT_ENOUGH_ARGS_ERROR_CODE = -1;

	/**
	 * Creates the initial state from the data received in JSON. The data must be in the following
	 * structure: args[0] - MapID, Number of players For each player (p): args[2*p + 1] - Weapon
	 * details args[2*p + 2] - AIAlgorithm
	 * 
	 * @param args the data in JSON
	 * @return
	 */
	public static State createInitialState(String[] args) {
		if (args.length < 1) {
			System.out.println("Not enough arguments");
			System.exit(NOT_ENOUGH_ARGS_ERROR_CODE);
		}

		Gson gson =
						new GsonBuilder()
										.registerTypeAdapter(Action.class, new ActionDeserializer())
										.registerTypeAdapter(Condition.class,
														new ConditionDeserializer()).create();
		State state = new State();

		// read number of players
		int numPlayers = gson.fromJson(args[0], int.class);

		for (int playerID = 0; playerID < numPlayers; playerID++) {
			int teamID = 0;

			// reading weapons to player
			Player player = gson.fromJson(args[2 * playerID + 1], Player.class);
			player.setId(playerID);
			player.setTeamId(teamID);

			// reading algorithm
			AIAlgorithm algorithm = gson.fromJson(args[2 * playerID + 2], AIAlgorithm.class);
			player.setAlgorithm(algorithm);
			state.addPlayer(player);
		}

		return state;
	}
}
