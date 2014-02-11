package com.md.mechevo.game.projectile;

import com.md.mechevo.game.*;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public abstract class Projectile extends Solid implements EventObservable {

	private Weapon weapon;
	
	private EventObserver report;

	protected Projectile(int id, Position position, float width, float height, float speed,
					float angle, Weapon weapon) {
		super(width, height, speed, angle, id);
		super.setPosition(position);
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	
	/**
	 * Notify the report that it has been created.
	 */
	public void begin(State state) {
		if (this.report != null) {
			//this.report.notify( Event Data that I have been created );
		}
	}
	
	//public abstract void update(State state, float dtime);
	
	
	/**
	 * Notify the report that it has been destroyed.
	 */
	public void end(State state) {
		if (this.report != null) {
			//this.report.notify( Event Data that I have been destroyed );
		}
	}
	
	
	

	@Override
	public abstract void collidesWith(State state, Player p);

	@Override
	public abstract void collidesWith(State state, Projectile p);

	@Override
	public abstract void collidesWith(State State, Obstacle o);

	@Override
	public abstract void collidesWith(State state, Sentry s);

	@Override
	public void accept(CollisionVisitor s, State state) {
		s.collidesWith(state, this);
	}
	
	
	// interface EventObservable
	
	public void registerEventObserver(EventObserver eventObserver) {
		this.report = eventObserver;
	}
	
	public void notifyEventObserver(EventData eventData) {
		if ( this.report != null ) {
			this.report.notify(eventData);
		}
	}


}
