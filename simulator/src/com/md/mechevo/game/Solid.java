package com.md.mechevo.game;


import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;

public abstract class Solid implements CollisionVisitor, EventObservable {
	/**
	 * This is the left-most and top-most position.
	 */
	private Position position;
	private float width;
	private float height;
	
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
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.angle = angle;
		this.destroyed = false;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void rotateBy(float angle) {
		this.angle += angle;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
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
	 * @return a position with the center point
	 */
	Position getCenterPoint() {
		return new Position(position.getX() + width / 2, position.getY() + height / 2);
	}

	/**
	 * @return two coordinates where the top-most and left-most position and the bottom-most and
	 *         right-most position.
	 */
	Position[] getBoundingBox() {
		Position[] positions = new Position[2];
		positions[0] = this.getPosition();
		positions[1] = new Position(position.getX() + width, position.getY() + height);

		return positions;
	}

	/**
	 * Implements the visitor design pattern to enable new operations on solids.
	 * 
	 * @param s the visitor
	 * @param state the state after moving
	 */
	public abstract void accept(CollisionVisitor s, State state);


	/**
	 * Default case for updating position. Used in everything that isn't homing.
	 * 
	 * @param state
	 * TODO: switch the movement part to the action
	 */
	public void update(State state, float dtime) {
        this.move(this.getAngle(), this.getSpeed(), dtime);
    }
	
	
	/**
	 * Moves the Solid in direction given by angle with given speed in a straight line.
	 *
	 * @param angle Angle of movement (independent of current angle)
	 * @param speed Velocity in MapUnits per Second (independent of current angle)
	 * @param dtime Time in seconds of duration of movement
	 */
	public void move(float angle, float speed, float dtime) {
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
     * @param dist Distance to move
     */
    public void move(float angle, float dist) {
        float vecX =  dist * (float) Math.cos(angle);
        float vecY =  -(dist * (float) Math.sin(angle));

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
	
	public boolean registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
		return true;
	}
	
	public void notifyEventObserver(EventData eventData) {
		if ( this.report != null ) {
			this.report.notify(eventData);
		}
	}
	
}
