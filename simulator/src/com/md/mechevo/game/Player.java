package com.md.mechevo.game;

import java.util.ArrayList;

import com.md.mechevo.ai.AIAlgorithm;

public class Player extends Solid implements CollisionVisitor {
	private int id;
	private int health;
	private ArrayList<Weapon> weapons;
	private ArrayList<Sentry> sentries;
	private AIAlgorithm algorithm;

	public Player(Coordinate centerCoordinate, float width, float height, float speed, int health,
					float angle, int id, ArrayList<Weapon> weapons, AIAlgorithm algorithm) {
		super(centerCoordinate, width, height, speed, angle);
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
	void accept(CollisionVisitor s) {
		s.collidesWith(this);
	}

	@Override
	public void collidesWith(Player p) {}

	@Override
	public void collidesWith(Projectile p) {}

	@Override
	public void collidesWith(Obstacle o) {

	}

	@Override
	public void collidesWith(Sentry s) {

	}

	public void play(float time) {
		for (Sentry s : sentries) {
			s.play(time);
		}

		// TODO
	}
}
