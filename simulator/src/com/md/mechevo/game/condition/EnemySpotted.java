package com.md.mechevo.game.condition;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Selects the closest/furthest visible enemy
 */
public class EnemySpotted extends Condition {
    public EnemySpotted(Player owner, String param) {
        super(owner, param);
    }

    /**
     * Check if the condition applies.
     *
     * @param state Current State of the game
     * @return True if the condition applies
     */
    @Override
    public boolean check(State state) {
        return false;
    }

    /**
     * Get the preferred target for this condition.
     *
     * @param state Current State of the game
     * @return Reference to target Player or null if none
     */
    @Override
    public Player getPreferredPlayer(State state) {
        return null;
    }
}
