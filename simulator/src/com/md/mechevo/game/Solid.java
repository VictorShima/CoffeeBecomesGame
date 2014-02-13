package com.md.mechevo.game;


import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public abstract class Solid implements CollisionVisitor, EventObservable {
	/**
	 * This is the center position.
	 */
	private Position position;

	private double radius;

	/**
	 * Speed comes in MapUnits per Second
	 */
	private double speed;

	/**
	 * Angle comes in degrees, with 0 degrees being left xx axis (like in math)
	 */
	private double angle;

	private boolean destroyed;
	private int id;

	private EventObserver report;

	protected Solid(int id, Position position, double radius, double speed, double angle) {
		this.id = id;
		this.position = position;
		this.radius = radius;
		this.speed = speed;
		this.angle = angle;
		this.destroyed = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle % 360f;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
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

	public EventObserver getReport() {
		return report;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Solid) {
			Solid s = (Solid) o;
			return this.getId() == s.getId();
		}
		return false;
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
	public abstract void update(State state, double dtime);


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
	public void moveForward(double angle, double speed, double dtime) {
		double velX = Math.cos(angle);
		double velY = Math.sin(angle);
		// Normalization of the velocity vector
		double vel = velX + velY;
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
	public void moveForward(double angle, double dist) {
		double vecX = dist * Math.cos(angle);
		double vecY = -(dist * (double) Math.sin(angle));

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
