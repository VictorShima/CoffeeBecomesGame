package com.md.mechevo.game;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.md.mechevo.game.projectile.Projectile;
import com.rits.cloning.Cloner;

/**
 * A report contains a log where the first entry is the
 */
public class Report {
	private final Gson gson;
	private final Cloner cloner;
	private List<String> log;
	private State lastState;

	public Report() {
		this.log = new LinkedList<String>();
		this.gson = new Gson();
		this.cloner = new Cloner();
		this.lastState = null;
	}

	public void update(State state) {
		if (lastState == null) {
			// prints every player
			log.add(print(state.getPlayers()));
		} else {
			List<String> diff = new LinkedList<String>();

			// only prints the differences between the lastState and state
			for (Player playerInLastState : lastState.getPlayers()) {
				Player playerInCurrentState = state.findPlayerById(playerInLastState.getId());

				if (!playerInCurrentState.equals(playerInLastState)) {
					diff.add(printPlayerDifferences(playerInLastState, playerInCurrentState));
				}
			}
			log.add(gson.toJson(diff));

			// print the projectiles
			Type type = new TypeToken<Collection<Projectile>>() {}.getType();
			log.add(gson.toJson(state.getProjectiles(), type));
		}
		// clones the state to be sure it isn't modified
		this.lastState = cloner.deepClone(state);
	}

	private String printPlayerDifferences(Player playerInLastState, Player playerInCurrentState) {
		List<String> diff = new LinkedList<String>();

		// compare positions
		if (!playerInCurrentState.getPosition().equals(playerInLastState.getPosition())) {
			Type type = new TypeToken<Position>() {}.getType();
			diff.add(gson.toJson(playerInCurrentState.getPosition(), type));
		}

		// compare health
		if (playerInCurrentState.getHealth() != playerInLastState.getHealth()) {
			diff.add(String.format("\"health\":\"%d\",", playerInCurrentState.getHealth()));
		}

		Type type = new TypeToken<Collection<String>>() {}.getType();
		return gson.toJson(diff, type);
	}

	public String print() {
		return gson.toJson(log);
	}

	private String print(Collection<Player> players) {
		Type type = new TypeToken<Collection<Player>>() {}.getType();
		return gson.toJson(players, type);
	}
}
