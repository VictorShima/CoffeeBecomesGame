package com.md.mechevo.game;

import java.util.ArrayList;

import com.md.mechevo.game.action.Action;
import com.md.mechevo.game.ai.AIAlgorithm;
import com.md.mechevo.game.ai.AIEntry;
import com.md.mechevo.game.ai.AISuggestion;
import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class Player extends Solid {


	/**
	 * Health the Player starts with
	 */
	public static final int INITIAL_HEALTH = 100;

	/**
	 * Width is measured in pixels
	 */
	public static final float INITIAL_WIDTH = 30;

	/**
	 * Height is measured in pixels
	 */
	public static final float INITIAL_HEIGHT = 30;

	/**
	 * Speed is measured by number of pixels per second
	 */
	public static final float INITIAL_SPEED = 5;

	/**
	 * Angle is measured in degrees.
	 */
	public static final float INITIAL_ANGLE = 0;
	private int teamId;
	private int health;
	private ArrayList<Weapon> weapons; // TODO: make each weapon assigned to a slot (1,2,3)
	private ArrayList<Sentry> sentries;
	private boolean paralysed = false;
	private boolean confused = false;
	private MovementState movementState;
	
	private AIAlgorithm algorithm;
	private AIEntry currentAiEntry;
	private Action currentAction;
	private float currentActionTime; // /< time since begin of action execution

	public Player(int id, int teamId) {
		super(INITIAL_WIDTH, INITIAL_HEIGHT, INITIAL_SPEED, INITIAL_ANGLE, id);
		this.teamId = teamId;
		this.health = INITIAL_HEALTH;
		this.weapons = new ArrayList<Weapon>();
		this.sentries = new ArrayList<Sentry>();
		this.algorithm = new AIAlgorithm();
	}

	public int getTeamId() {
		return teamId;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}


	/**
	 * Equips a weapon and boxes it into a WeaponSlot
	 */
	public void equipWeapon(Weapon w, Weapon.WeaponSlot slot) {
		w.setCurrentSlot(slot);
		weapons.add(w);
	}


	public boolean isParalysed() {
		return paralysed;
	}

	public void setParalysed(boolean paralysed) {
		this.paralysed = paralysed;
	}

	public boolean isConfused() {
		return confused;
	}

	public void setConfused(boolean confused) {
		this.confused = confused;
	}

	public void addSentry(Sentry s) {
		sentries.add(s);
	}

	public void removeSentry(Sentry s) {
		sentries.remove(s);
	}

	public void takeDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			setDestroyed(true);
		}
	}

	/**
	 * Get Movement State
	 */
	public MovementState getMovementState() {
		return this.movementState;
	}

	/**
	 * Set Movement State
	 */
	public void setMovementState(MovementState state) {
		this.movementState = state;
	}

	@Override
	public void accept(CollisionVisitor s, State state) {
		s.collidesWith(state, this);
	}

	@Override
	public void collidesWith(State state, Player p) {}

	@Override
	public void collidesWith(State state, Projectile p) {}

	@Override
	public void collidesWith(State State, Obstacle o) {}

	@Override
	public void collidesWith(State state, Sentry s) {}

	public void confuse() {
		this.setConfused(true);
	}

	public void paralyse() {
		this.setParalysed(true);
	}
	
	
	
	public void begin(State state) { }
	

	@Override
	public void update(State state, float dtime) {

		if (true) {
			return;
		}

		if (!isParalysed()) {

			// switch actions if they already finished
			if (this.currentAction != null
							&& this.currentActionTime >= this.currentAction.getDuration()) {
				this.currentAction = this.currentAction.getNext();
			}

			// try to cancel the action and find a new one
			if (this.currentAction != null) {
				if (this.currentAction.isCancelable()) {
					AISuggestion suggestion = this.algorithm.calculateBestAction(state);
					if (suggestion.getAiEntry() != this.currentAiEntry) {
						this.currentAiEntry = suggestion.getAiEntry();
						this.currentAction = suggestion.getFirstAction();
						this.currentActionTime = 0;
					}
				}
			}

			if (this.currentAction != null) {
				// perform the current action
				if (this.currentActionTime == 0) {
					this.currentAction.begin(state);
				}
				this.currentAction.update(state, dtime);

				// post-update
				this.currentActionTime += dtime;
			}
		}
	}
	
	
	
	public void end(State state) { }


	/**
	 * Current state of movement
	 */
	public static enum MovementState {
		STOPPED, MOVING, SPRINTING, DASHING
	}
	
	
	// interface EventObservable
	
	/**
	 * Will register the observer on himself and also on its belongings.
	 * Belongings with the observer include: Weapon, Actions
	 */
	public void registerEventObserver(EventObserver eventObserver) {
		// set himself
		super.registerEventObserver(eventObserver);
		// set all weapons
		for ( Weapon w : this.weapons ) {
			w.registerEventObserver(eventObserver);
		}
		// set all actions
		for ( AIEntry aiEntry : this.algorithm.getEntries() ) {
			for ( Action action : aiEntry.getActions() ) {
				action.registerEventObserver(eventObserver);
			}
		}
	}
	
}
