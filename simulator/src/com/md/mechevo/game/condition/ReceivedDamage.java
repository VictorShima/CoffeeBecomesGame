package com.md.mechevo.game.condition;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * ReceivedDamage (frontal, back, side) : true if just received damage
 */
public class ReceivedDamage extends Condition {
    public ReceivedDamage(Player owner, String param) {
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
