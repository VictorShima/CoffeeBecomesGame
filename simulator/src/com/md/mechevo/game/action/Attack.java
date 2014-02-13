package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;


/**
 * Attack will shot with the selected weapon, first by checking if it's cooldown is up.
 */
public class Attack extends Action {
    private static final double DURATION = 0.5;
    private static final boolean CANCELABLE = true;

    /**
     * @param param the selected weapon (LEFT, CENTER, RIGHT)
     */
    public Attack(Player owner, String param) {
        super(owner, param, Attack.CANCELABLE);
    }

    /**
     * Check if the required condition for an action applies.
     *
     * @param state Current State of the game
     * @return True if the condition applies
     */
    @Override
    public boolean check(State state) {
        // TODO check weapon cooldown and action duration
        return false;
    }

    /**
     * Begin the execution of the action. Will be called once at the start of the action.
     *
     * @param state Current state of the game
     */
    @Override
    public void begin(State state) {

    }

    /**
     * Execute the action.
     *
     * @param state Current State of the game
     * @param dtime Duration of the round
     */
    @Override
    public void update(State state, double dtime) {

    }

    /**
     * End the execution of the action, whether it is cancelled or successfully ended.
     *
     * @param state Current state of the game
     */
    @Override
    public void end(State state) {

    }
}
