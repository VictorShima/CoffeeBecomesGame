package com.md.mechevo.game;

import java.util.ArrayList;

import com.md.mechevo.game.ai.AIAlgorithm;
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
	public static final double MOVE_SPEED = 500;

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

	public static final double MAX_HEAT = 100;

	private int teamId;
	private int health;
	private double heat;
	private ArrayList<Weapon> weapons;
	private ArrayList<Sentry> sentries;
	private boolean paralysed = false;
	private boolean confused = false;
	private AIAlgorithm algorithm;
	private double lastHitAngle; // /< The absolute angle of the last hit

	/**
	 * Current order that the Player is executing.
	 */
	private AISuggestion currentOrder;

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

	public static double getMaxHeat() {
		return MAX_HEAT;
	}

	public AISuggestion getCurrentOrder() {
		return currentOrder;
	}

	public double getLastHitAngle() {
		return lastHitAngle;
	}

	public void setLastHitAngle(double lastHitAngle) {
		this.lastHitAngle = lastHitAngle;
	}

	public AIAlgorithm getAlgorithm() {
		return algorithm;
	}

	public double getAngleToTarget(Solid target) {
		// VecRight is a vector pointing right
		double vecRightX = -20;
		double vecRightY = 0;
		double vecX = this.getPosition().getX() - target.getPosition().getX();
		double vecY = this.getPosition().getY() - target.getPosition().getY();
		double cosValue =
				((vecX * vecRightX) + (vecY * vecRightY))
						/ (Math.sqrt(Math.pow(vecRightX, 2) + Math.pow(vecRightY, 2)) * Math
								.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2)));
		double angle = Math.toDegrees(Math.acos(cosValue));

        // Rotate clockwise/counter-clockwise is determined by sign of cross-product
        double crossProd = (vecX * vecRightY) - (vecX * vecRightX);
		return (crossProd < 0) ? angle : -angle;
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

	public void increaseHeat(double amount) {
		this.heat += amount;
		if (this.heat > MAX_HEAT) {
			this.heat = MAX_HEAT;
		}
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

		EventData event =
				new EventData("modifyHp").addAttribute("id", this.getId()).addAttribute("value",
						this.getHealth());
		this.notifyEventObserver(event);
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
		EventData event =
				new EventData("modifyHp").addAttribute("id", this.getId()).addAttribute("value",
						this.getHealth());
		this.notifyEventObserver(event);
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
	public ArrayList<Player> fieldOfView(State state, FieldOfViewAngle angle) {
		// vectorPlayer is the vector where the player is looking
		ArrayList<Player> playersInView = new ArrayList<>();
		double vectorPlayerX = Math.cos(Math.toRadians(this.getAngle()));
		double vectorPlayerY = Math.sin(Math.toRadians(this.getAngle()));
		ArrayList<Player> players = state.getPlayers();
		for (Player p : players) {
			if (this.getId() != p.getId()) {
				double vecX = this.getPosition().getX() - p.getPosition().getX();
				double vecY = this.getPosition().getY() - p.getPosition().getY();
				double cosValue =
						((vectorPlayerX * vecX) + (vectorPlayerY * vecY))
								/ ((Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2))) * (Math
										.sqrt(Math.pow(vectorPlayerX, 2)
												+ Math.pow(vectorPlayerY, 2))));
				double angleToPlayer = Math.toDegrees(Math.acos(cosValue));
				if (angleToPlayer < angle.getAngle()) {
					playersInView.add(p);
				}
			}
		}
		return playersInView;
	}

	public ArrayList<Obstacle> fieldOfViewObstacles(State state, FieldOfViewAngle angle) {
		// vectorPlayer is the vector where the player is looking
		ArrayList<Obstacle> obstaclesInView = new ArrayList<>();
		double vectorPlayerX = Math.cos(Math.toRadians(this.getAngle()));
		double vectorPlayerY = Math.sin(Math.toRadians(this.getAngle()));

		for (Obstacle o : state.getObstacles()) {
			double vecX = this.getPosition().getX() - o.getPosition().getX();
			double vecY = this.getPosition().getY() - o.getPosition().getY();
			double cosValue =
					((vectorPlayerX * vecX) + (vectorPlayerY * vecY))
							/ ((Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2))) * (Math.sqrt(Math
									.pow(vectorPlayerX, 2) + Math.pow(vectorPlayerY, 2))));
			double angleToObstacle = Math.acos(cosValue);
			if (angleToObstacle < angle.getAngle()) {
				obstaclesInView.add(o);
			}
		}

		return obstaclesInView;
	}

	public void begin(State state) {
		EventData event =
				new EventData("createPlayer").addAttribute("id", this.getId())
						.addAttribute("teamId", this.getTeamId())
						.addAttribute("x", this.getPosition().getX())
						.addAttribute("y", this.getPosition().getY())
						.addAttribute("angle", this.getAngle())
						//TODO add color
						.addAttribute("color", "shimMuchGaySuchWow")
						.addAttribute("WeaponLeft", this.getWeapons().get(0).getClass().getSimpleName())
						.addAttribute("WeaponRight", this.getWeapons().get(1).getClass().getSimpleName())
						.addAttribute("WeaponCenter", this.getWeapons().get(2).getClass().getSimpleName())
						.addAttribute("Hp", this.getHealth());
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

			// try to cancel the action and find a new one (if no current action or cancelable)
            AISuggestion suggestion = this.algorithm.calculateBestAction(state);
            if (this.currentOrder != null && this.currentOrder.getAction() != null &&
                    this.currentOrder.getAction().isCancelable() && (!suggestion.getAiEntry().equals(
                    this.currentOrder.getAiEntry()))) {
                this.currentOrder.getAction().end(state);
                this.currentOrder = suggestion;
            }

			if (this.currentOrder == null || this.currentOrder.getAction() == null) {
                this.currentOrder = suggestion;
			}

			if (this.currentOrder.getAction() != null) {
				// perform the current action
				if (this.currentOrder.isActionStart()) {
					this.currentOrder.getAction().begin(state);
				}
				this.currentOrder.getAction().update(state, dtime);

				// post-update
				this.currentOrder.addActionTime(dtime);
			}
		}
	}

	public void end(State state) {
		EventData event =
				new EventData("erasePlayer").addAttribute("id", this.getId());
		this.notifyEventObserver(event);
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
}
