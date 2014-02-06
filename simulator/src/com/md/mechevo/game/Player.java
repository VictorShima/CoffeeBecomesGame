package com.md.mechevo.game;

import java.util.ArrayList;

import com.md.mechevo.ai.AIAlgorithm;
import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class Player extends Solid implements CollisionVisitor {
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

	private int id;
	private int health;
	private ArrayList<Weapon> weapons;
	private ArrayList<Sentry> sentries;
	private AIAlgorithm algorithm;

	public Player(int id) {
		super(INITIAL_WIDTH, INITIAL_HEIGHT, INITIAL_SPEED, INITIAL_ANGLE);
		this.id = id;
		this.health = INITIAL_HEALTH;
		this.weapons = new ArrayList<>();
		this.sentries = new ArrayList<>();
		this.algorithm = new AIAlgorithm();
	}

	public int getID() {
		return id;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void addWeapon(Weapon w) {
		weapons.add(w);
	}

	public void takeDamage(int damage) {
		health -= damage;
		if (health < 0) {
			health = 0;
		}
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

	public void play(State state) {
		// update sentries
		for (Sentry s : sentries) {
			s.play(state);
		}

		// TODO
	}
}
