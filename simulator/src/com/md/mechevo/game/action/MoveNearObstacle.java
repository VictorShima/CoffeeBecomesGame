package com.md.mechevo.game.action;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Obstacle;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Move or sprint to the nearest obstacle in a direct line
 */
public class MoveNearObstacle extends Action {
    private static final boolean CANCELABLE = true;

    private Obstacle target;

    public MoveNearObstacle(Player owner) {
        super(owner, CANCELABLE);
    }

    /**
     * Check if the the action has already finished.
     */
    @Override
    public boolean hasFinished() {
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
        return false;
    }

    /**
     * Begin the execution of the action. Will be called once at the start of the action.
     *
     * @param state Current state of the game
     */
    @Override
    public void begin(State state) {
        // TODO find target

        EventData eventData = new EventData("startMovingObstacle").addAttribute("id",
                this.getOwner().getId());
        this.notifyEventObserver(eventData);
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
        EventData eventData = new EventData("stopMovingObstacle").addAttribute("id",
                this.getOwner().getId());
        this.notifyEventObserver(eventData);
    }
}