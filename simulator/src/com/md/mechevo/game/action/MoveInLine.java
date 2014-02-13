package com.md.mechevo.game.action;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * (move forward, move back, sprint forward, sprint back) in a direct line
 */
public class MoveInLine extends Action {
    private static final boolean CANCELABLE = true;

    /**
     * @param param indicates wether the player moves forward or backward.
     */
    public MoveInLine(Player owner, String param) {
        super(owner, param, CANCELABLE);
    }

    /**
     * Check if the the action has already finished.
     */
    @Override
    public boolean hasFinished() {
        // TODO
        return false;
    }

    /**
     * Check if the required condition for an action applies.
     *
     * @param state Current State of the game
     * @return True if the condition applies
     */
    @Override
    public boolean check(State state) {
        return true;
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
