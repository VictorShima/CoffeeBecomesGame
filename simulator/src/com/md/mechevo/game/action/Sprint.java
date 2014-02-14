package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Faster movement (forward or backward)
 */
public class Sprint extends Action {
    //TODO I think we can delete this and use MoveInLine instead
    private static final double DURATION = 1;
    private static final boolean CANCELABLE = true;

    public Sprint(Player owner, ArrayList<String> param) {
        super(owner, param, CANCELABLE);
    }

    /**
     * Check if the the action has already finished.
     */
    @Override
    public boolean hasFinished() {
        return DURATION <= this.getOwner().getCurrentActionTime();
    }

    /**
     * Check if the required condition for an action applies.
     *
     * @param state Current State of the game
     * @return True if the condition applies
     */
    @Override
    public boolean check(State state) {
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
