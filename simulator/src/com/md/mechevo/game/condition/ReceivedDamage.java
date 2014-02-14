package com.md.mechevo.game.condition;

import java.util.ArrayList;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * ReceivedDamage (frontal, back, side) : true if just received damage
 */
public class ReceivedDamage extends Condition {
    public ReceivedDamage(Player owner, ArrayList<String> param) {
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
        //If this condition applies we should update the players lastHitAngle to null
        //so that the player doesnt keep repeating this action
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

    public static enum Face {
        FRONT(45f, 135f), LEFT(135f, 225f), RIGHT(315f, 45f), BACK(225f, 315f);

        private final double minAngle;
        private final double maxAngle;

        Face(double minAngle, double maxAngle) {
            this.minAngle = minAngle;
            this.maxAngle = maxAngle;
        }

        private double getMinAngle() {
            return minAngle;
        }
        private double getMaxAngle() { return maxAngle; }
    }
}
