package com.md.mechevo.game.action;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.Solid;
import com.md.mechevo.game.State;

/**
 * (move forward, move back, sprint forward, sprint back) in a direct line
 */
public class MoveInLine extends Action {
    private static final boolean CANCELABLE = true;

    private final Mode mode;

    /**
     * @param param indicates if the player moves forward or backward.
     */
    public MoveInLine(Player owner, String param) throws InvalidActionParameter {
        super(owner, param, CANCELABLE);
        this.mode = convertParam();
    }

    private Mode convertParam() throws InvalidActionParameter {
        try {
            return Mode.valueOf(this.getParam());
        } catch (IllegalArgumentException e) {
            throw new InvalidActionParameter(Mode.class.getName(), this.getParam());
        }
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
     * @return if it can move in the wished direction
     */
    @Override
    public boolean check(State state) {
        switch (mode) {
            case MOVE_FORWARD: // fall-through
            case SPRINT_FORWARD:
                return state.getMap().canSolidMove(this.getOwner(), this.getOwner().getAngle());
            case MOVE_BACKWARD: // fall-through
            case SPRINT_BACKWARD:
                return state.getMap().canSolidMove(this.getOwner(), this.getOwner().getAngle() +
                        Solid.HALF_CIRCLE_DEGREES);
        }

        System.err.println("MoveInLine#check: invalid mode.");
        return false;
    }

    /**
     * Begin the execution of the action. Will be called once at the start of the action.
     *
     * @param state Current state of the game
     */
    @Override
    public void begin(State state) {
        EventData eventData = new EventData("startMoving").addAttribute("id", getOwner().getId())
                .addAttribute("mode", this.mode.toString());
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
        Player owner = this.getOwner();
        switch (mode) {
            case MOVE_FORWARD:
                owner.move(owner.getAngle(), Player.MOVE_SPEED, dtime, true);
                break;
            case SPRINT_FORWARD:
                owner.move(owner.getAngle(), Player.SPRINT_SPEED, dtime, true);
                break;
            case MOVE_BACKWARD:
                owner.move(owner.getAngle(), Player.MOVE_SPEED, dtime, false);
                break;
            case SPRINT_BACKWARD:
                owner.move(owner.getAngle(), Player.SPRINT_SPEED, dtime, false);
                break;
        }
    }

    /**
     * End the execution of the action, whether it is cancelled or successfully ended.
     *
     * @param state Current state of the game
     */
    @Override
    public void end(State state) {
        EventData eventData = new EventData("stopMoving").addAttribute("id", getOwner().getId());
        this.notifyEventObserver(eventData);
    }

    private enum Mode {
        MOVE_FORWARD, MOVE_BACKWARD, SPRINT_FORWARD, SPRINT_BACKWARD
    }
}
