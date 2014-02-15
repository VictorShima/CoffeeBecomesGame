package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.Position;
import com.md.mechevo.game.State;

/**
 * Required params: (Move or Sprint), (Forward or Backward), distance
 */
public class MoveInLine extends Action {
    private static final double MARGIN_ERROR = 0.01;
    private static final boolean CANCELABLE = true;

    private Mode mode;
    private Direction direction;

    /**
     * Distance is in MapUnits per second.
     */
    private double distance;
    private double distanceAlreadyMoved;

    /**
     * @param param indicates if the player moves forward or backward.
     */
    public MoveInLine(Player owner, ArrayList<String> param) throws InvalidActionParameter {
        super(owner, param, CANCELABLE);
        this.distanceAlreadyMoved = 0;

        convertParams();
    }

    private void convertParams() throws InvalidActionParameter {
        ArrayList<String> params = this.getParam();
        if (params.size() != 3) {
            throw new InvalidActionParameter(MoveInLine.class.getName());
        }

        try {
            this.mode = Mode.valueOf(params.get(0));
            this.direction = Direction.valueOf(params.get(1));
            this.distance = Double.valueOf(params.get(2));
        } catch (IllegalArgumentException e) {
            throw new InvalidActionParameter(MoveInLine.class.getName());
        }
    }

    /**
     * @return true if the distance already moved is close to the ideal distance.
     */
    @Override
    public boolean hasFinished() {
        return Math.abs(distance - distanceAlreadyMoved) < MARGIN_ERROR;
    }

    /**
     * TODO Should be compared with all the other solids.
     *
     * @return if it can move in the wished direction
     */
    @Override
    public boolean check(State state) {
		//TODO if he wants to sprint but the heat is at max he cant
        return (direction.equals(Direction.FORWARD)) ?
                state.getMap().canSolidMove(this.getOwner(), this.getOwner().getAngle(), true) :
                state.getMap().canSolidMove(this.getOwner(), this.getOwner().getAngle(), false);
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
     * Moves in the desired direction and updates the distance already moved.
     *
     * @param state Current State of the game
     * @param dtime Duration of the round
     */
    @Override
    public void update(State state, double dtime) {
		//TODO this has to increase heat while it's sprinting
        Player owner = this.getOwner();
        Position oldPos = owner.getPosition();
        owner.move(owner.getAngle(), (mode == Mode.MOVE) ? Player.MOVE_SPEED : Player.SPRINT_SPEED,
                dtime, direction == Direction.FORWARD);

        double distX = owner.getPosition().getX() - oldPos.getX();
        double distY = owner.getPosition().getY() - oldPos.getY();
        this.distanceAlreadyMoved += Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
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
        MOVE, SPRINT
    }

    private enum Direction {
        FORWARD, BACKWARD
    }
}