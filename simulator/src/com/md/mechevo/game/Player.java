package com.md.mechevo.game;

import java.util.ArrayList;

import com.md.mechevo.ai.AIAlgorithm;
import com.md.mechevo.game.projectile.Projectile;
import com.md.mechevo.game.sentry.Sentry;
import com.md.mechevo.game.weapon.Weapon;

public class Player extends Solid implements CollisionVisitor {
	private int id;
	private int health;
	private ArrayList<Weapon> weapons;
	private ArrayList<Sentry> sentries;
	private AIAlgorithm algorithm;

	public Player(Position centerPosition, float width, float height, float speed, int health,
					float angle, int id, ArrayList<Weapon> weapons, AIAlgorithm algorithm) {
		super(centerPosition, width, height, speed, angle);
		this.id = id;
		this.health = health;
		this.weapons = weapons;
		this.algorithm = algorithm;
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

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
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
