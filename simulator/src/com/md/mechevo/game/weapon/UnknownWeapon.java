package com.md.mechevo.game.weapon;

public class UnknownWeapon extends Error {
	public UnknownWeapon(String weaponName) {
		super("Unknown weapon: " + weaponName);
	}
}
