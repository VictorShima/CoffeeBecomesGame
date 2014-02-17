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
			throw new InvalidActionParameter(MoveInLine.class.getSimpleName());
		}

		try {
			this.mode = Mode.valueOf(params.get(0).toUpperCase());
			this.direction = Direction.valueOf(params.get(1).toUpperCase());
			this.distance = Double.valueOf(params.get(2));
		} catch (IllegalArgumentException e) {
			throw new InvalidActionParameter(MoveInLine.class.getSimpleName());
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
	 * @return if it can move in the wished direction
	 */
	@Override
	public boolean check(State state) {
		boolean canSprint =
				(mode.equals(Mode.SPRINT)) && (this.getOwner().getHeat() < Player.MAX_HEAT);
		return (mode.equals(Mode.MOVE)) || (mode.equals(Mode.SPRINT) && canSprint);
	}

	/**
	 * Begin the execution of the action. Will be called once at the start of the action.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void begin(State state) {
		EventData eventData =
				new EventData("startMoving").addAttribute("id", getOwner().getId()).addAttribute(
						"mode", this.mode.toString()).addAttribute(
						"speed", this.getOwner().getSpeed());
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
		Player owner = this.getOwner();
		Position oldPos = owner.getPosition();
		owner.move(owner.getAngle(), (mode == Mode.MOVE) ? Player.MOVE_SPEED : Player.SPRINT_SPEED,
				dtime, direction == Direction.FORWARD);

		double distX = owner.getPosition().getX() - oldPos.getX();
		double distY = owner.getPosition().getY() - oldPos.getY();
		double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		this.distanceAlreadyMoved += distance;

		if (mode == Mode.SPRINT) {
			this.getOwner().increaseHeat(dtime * Player.HEAT_RATE);
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
		MOVE, SPRINT
	}

	private enum Direction {
		FORWARD, BACKWARD
	}
}
