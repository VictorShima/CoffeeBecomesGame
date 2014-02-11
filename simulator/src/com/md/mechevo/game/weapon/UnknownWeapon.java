package com.md.mechevo.game.weapon;

import java.util.UnknownFormatConversionException;

public class UnknownWeapon extends UnknownFormatConversionException {
	public UnknownWeapon(String weaponName) {
		super("Unknown weapon: " + weaponName);
	}
}
