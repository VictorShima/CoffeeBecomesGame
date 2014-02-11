package com.md.mechevo.game;


import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public abstract class Solid implements CollisionVisitor, EventObservable {
	/**
	 * This is the center position.
	 */
	private Position position;

	private float radius;

	/**
	 * Speed comes in MapUnits per Second
	 */
	private float speed;

	/**
	 * Angle comes in radians, with 0 radians being left xx axis (like in math)
	 */
	private float angle;

	private boolean destroyed;
	private int id;

	private EventObserver report;

	protected Solid(float width, float height, float speed, float angle, int id) {
		this.speed = speed;
		this.angle = angle;
		this.destroyed = false;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle%360f;
	}

	public void rotateBy(float angle) {
		this.angle += angle;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroy) {
		this.destroyed = destroy;
	}

	/**
	 * Implements the visitor design pattern to enable new operations on solids.
	 * 
	 * @param s the visitor
	 * @param state the state after moving
	 */
	public abstract void accept(CollisionVisitor s, State state);


	/**
	 * Method that is called when a new Solid is created.
	 * 
	 * @param state Current State of the game
	 */
	public abstract void begin(State state);

	/**
	 * Update method for a solid.
	 * 
	 * @param state Current State of the game
	 * @param dtime Delta time of update
	 */
	public abstract void update(State state, float dtime);
	
	
	/**
	 * Method that is called when a new Solid is destroyed.
	 * 
	 * @param state Current State of the game
	 */
	public abstract void end(State state);
	

	/**
	 * Moves the Solid in direction given by angle with given speed in a straight line.
	 * 
	 * @param angle Angle of movement (independent of current angle)
	 * @param speed Velocity in MapUnits per Second (independent of current angle)
	 * @param dtime Time in seconds of duration of movement
	 */
	public void moveForward(float angle, float speed, float dtime) {
		float velX = (float) Math.cos(angle);
		float velY = (float) Math.sin(angle);
		// Normalization of the velocity vector
		float vel = velX + velY;
		velX = (velX / vel) * dtime * speed;
		velY = -(velY / vel) * dtime * speed;

		this.setPosition(new Position(this.getPosition().getX() + velX, this.getPosition().getY()
						+ velY));
	}

	/**
	 * Moves the Solid in direction given by angle for a given distance in a straight line.
	 * 
	 * @param angle Angle of movement (independent of current angle)
	 * @param dist Distance to moveForward
	 */
	public void moveForward(float angle, float dist) {
		float vecX = dist * (float) Math.cos(angle);
		float vecY = -(dist * (float) Math.sin(angle));

		this.setPosition(new Position(this.getPosition().getX() + vecX, this.getPosition().getY()
						+ vecY));
	}



	// interface CollisionVisitor

	@Override
	public abstract void collidesWith(State state, Player p);

	@Override
	public abstract void collidesWith(State state, Projectile p);

	@Override
	public abstract void collidesWith(State State, Obstacle o);

	@Override
	public abstract void collidesWith(State state, Sentry s);


	// interface EventObservable
	
	public void registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
	}

	public void notifyEventObserver(EventData eventData) {
		if (this.report != null) {
			this.report.notify(eventData);
		}
	}

}
