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
	 * Move speed is measured by MapUnits per second.
	 */
	public static final double MOVE_SPEED = 5;

    public static final double ROT_SPEED = 10;

    /**
     * Sprint speed is measured by MapUnits per second.
     */
    public static final double SPRINT_SPEED = 10;

    /**
     * The speed at which the heat decreases per second.
     */
    public static final double HEAT_RATE = 10;

	private static final int WEAPON_TRANSLATION = 15;

	private int teamId;
	private int health;
    private double heat;
	private ArrayList<Weapon> weapons;
	private ArrayList<Sentry> sentries;
	private boolean paralysed = false;
	private boolean confused = false;
	private AIAlgorithm algorithm;
	private AIEntry currentAiEntry;
	private Action currentAction;
    private double lastHitAngle; // /< The absolute angle of the last hit

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
		super(id, position, RADIUS, MOVE_SPEED, angle);
		this.teamId = teamId;
		this.health = HEALTH;
        this.heat = 0;
		this.weapons = new ArrayList<>();
		this.sentries = new ArrayList<>();
	}

    public double getLastHitAngle() {
        return lastHitAngle;
    }

    public void setLastHitAngle(double lastHitAngle) {
        this.lastHitAngle = lastHitAngle;
    }

    public Position getLeftWeaponPosition() {
		double angle = 90 - this.getAngle();
		double vecX = WEAPON_TRANSLATION * Math.cos(Math.toRadians(angle));
		double vecY = -(WEAPON_TRANSLATION * Math.sin(Math.toRadians(angle)));
		int posX = (int) (this.getPosition().getX() - vecX);
		int posY = (int) (this.getPosition().getY() + vecY);

		return new Position(posX, posY);
	}

	public Position getRightWeaponPosition() {
		double angle = 90 - this.getAngle();
		double vecX = WEAPON_TRANSLATION * Math.cos(Math.toRadians(angle));
		double vecY = -(WEAPON_TRANSLATION * Math.sin(Math.toRadians(angle)));
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

    public double getHeat() {
        return heat;
    }

    public void updateHeat(double dtime) {
        this.heat -= Player.HEAT_RATE * dtime;
        if (this.heat < 0) {
            this.heat = 0;
        }
    }

    public double getCurrentActionTime() {
        return currentActionTime;
    }

    /**
     * takeDamage drops the health of the player (this is only used for mines
     * because it makes no sense updating the lastHitAngle)
     * @param damage
     */
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            setDestroyed(true);
        }
    }

    /**
     * take Damage drops the health of player
     * @param damage
     * @param angle the angle of the projectile
     */
    public void takeDamage(int damage, double angle) {
		health -= damage;
        setLastHitAngle(angle);
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

    public ArrayList<Weapon> getWeapons() {
        return weapons;
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
		this.move(dist / 2, angle, true);
		p.move(dist / 2, angle, false);

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

    /**
     * fieldOfView returns all players that are in the field of view or in the field of fire
     * @param state
     * @param angle FieldOfViewAngle.FIRE or FieldOfViewAngle.VIEW
     * @return
     */
    public ArrayList<Player> fieldOfView(State state, FieldOfViewAngle angle){
        //vectorPlayer is the vector where the player is looking
        ArrayList<Player> playersInView= new ArrayList<>();
        double vectorPlayerX = Math.cos(Math.toRadians(this.getAngle()));
        double vectorPlayerY = Math.sin(Math.toRadians(this.getAngle()));
        ArrayList<Player> players = state.getPlayers();
        for (Player p : players){
            if(!(this.getId() == p.getId())){
                double vecX = this.getPosition().getX() - p.getPosition().getX();
                double vecY = this.getPosition().getY() - p.getPosition().getY();
                double cosValue = ((vectorPlayerX * vecX) + (vectorPlayerY * vecY))/((Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY,2))) *
                        (Math.sqrt(Math.pow(vectorPlayerX, 2) + Math.pow(vectorPlayerY,2))));
                double angleToPlayer = Math.acos(cosValue);
                if(angleToPlayer < angle.getAngle()) {
                    playersInView.add(p);
                }
            }
        }
        return playersInView;
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
        // Update player's heat
        this.updateHeat(dtime);

        // Update all weapon's cooldown
        for (Weapon weapon : weapons) {
            weapon.updateCurrentCooldown(dtime);
        }

        // A player can't move when it's paralysed
		if (!isParalysed()) {
			if (this.currentAction != null && this.currentAction.hasFinished()) {
				this.currentAction = this.currentAction.getNext();
			}

			// Find a new AIEntry
			if ((this.currentAction != null && this.currentAction.isCancelable()) || this.currentAction == null) {
                AISuggestion suggestion = this.algorithm.calculateBestAction(state);
                if (suggestion.getAiEntry() != this.currentAiEntry) {
                    if (this.currentAction != null) {
                        this.currentAction.end(state);
                    }

                    this.currentAiEntry = suggestion.getAiEntry();
                    this.currentAction = suggestion.getFirstAction();
                    this.currentAction.begin(state);
                    this.currentActionTime = 0;
                }
			}

            this.currentAction.update(state, dtime);
            this.currentActionTime += dtime;
		}
	}

	public void end(State state) {
    }

    public static enum FieldOfViewAngle {
        VIEW(60), FIRE(30);
        
        private final double angle;
        
        FieldOfViewAngle(double angle) {
            this.angle = angle;
        }
        
        private double getAngle() {
            return angle;
        }
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
