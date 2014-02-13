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
	public static final int HEALTH = 100;

	/**
	 * Radius is measured in number of pixels.
	 */
	public static final double RADIUS = 30;

	/**
	 * Speed is measured by number of pixels per second.
	 */
	public static final double SPEED = 5;

	private static final int WEAPON_TRANSLATION = 15;

	private int teamId;
	private int health;
	private ArrayList<Weapon> weapons;
	private ArrayList<Sentry> sentries;
	private boolean paralysed = false;
	private boolean confused = false;
	private AIAlgorithm algorithm;
	private AIEntry currentAiEntry;
	private Action currentAction;

	/**
	 * Time since the begin of the action's execution.
	 */
	private double currentActionTime;

	/**
	 * The class constructor. All these parameters are required.
	 *
	 * @param id the unique identifier
	 * @param teamId the unique team identifier
	 * @param position the initial position
	 * @param angle the initial angle
	 */
	public Player(int id, int teamId, Position position, double angle) {
		super(id, position, RADIUS, SPEED, angle);
		this.teamId = teamId;
		this.health = HEALTH;
		this.weapons = new ArrayList<>();
		this.sentries = new ArrayList<>();
		this.algorithm = new AIAlgorithm(this);
	}

	public Position getLeftWeaponPosition() {
		double angle = 90 - this.getAngle();
		double vecX = WEAPON_TRANSLATION * Math.cos(angle);
		double vecY = -(WEAPON_TRANSLATION * Math.sin(angle));
		int posX = (int) (this.getPosition().getX() - vecX);
		int posY = (int) (this.getPosition().getY() + vecY);

		return new Position(posX, posY);
	}

	public Position getRightWeaponPosition() {
		double angle = 90 - this.getAngle();
		double vecX = WEAPON_TRANSLATION * Math.cos(angle);
		double vecY = -(WEAPON_TRANSLATION * Math.sin(angle));
		int posX = (int) (this.getPosition().getX() + vecX);
		int posY = (int) (this.getPosition().getY() - vecY);

		return new Position(posX, posY);
	}

	public int getTeamId() {
		return teamId;
	}

	public int getHealth() {
		return health;
	}

	public void takeDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			setDestroyed(true);
		}
	}

	public void setAlgorithm(AIAlgorithm algorithm) {
		this.algorithm = algorithm;
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

	@Override
	public void accept(CollisionVisitor s, State state) {
		s.collidesWith(state, this);
	}

	@Override
	public void collidesWith(State state, Player p) {
		// vec is the distance vector
		double vecX = this.getPosition().getX() - p.getPosition().getX();
		double vecY = this.getPosition().getY() - p.getPosition().getY();
		double tangentAlfa = vecY / vecX;
		double angle = Math.atan(tangentAlfa);
		double dist = Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2));
		// distance shouldn't count the radius of the solid
		dist = -(dist - (this.getRadius() * 2));
		this.moveForward(dist / 2, angle);
		p.moveForward(dist / 2, angle + 180f);

	}

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


	// TODO: Needs to generate weapon name
	public void begin(State state) {
		EventData event =
				new EventData("createPlayer").addAttribute("id", this.getId())
						.addAttribute("teamId", this.getTeamId())
						.addAttribute("x", this.getPosition().getX())
						.addAttribute("y", this.getPosition().getY())
						.addAttribute("angle", this.getAngle());
		this.notifyEventObserver(event);
	}


	@Override
	public void update(State state, double dtime) {
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

	public void end(State state) {
        // TODO
    }

	// interface EventObservable

	/**
	 * Will register the observer on himself and also on its belongings. Belongings with the
	 * observer include: Weapon, Actions
	 */
	public void registerEventObserver(EventObserver eventObserver) {
		// set himself
		super.registerEventObserver(eventObserver);
		// set all weapons
		for (Weapon w : this.weapons) {
			w.registerEventObserver(eventObserver);
		}
		// set all actions
		for (AIEntry aiEntry : this.algorithm.getEntries()) {
			for (Action action : aiEntry.getActions()) {
				action.registerEventObserver(eventObserver);
			}
		}
	}

}
