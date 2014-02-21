package com.md.mechevo.game.weapon;

import com.md.mechevo.game.Player;

public class WeaponFactory {
	public static Weapon createWeapon(String name, Player player) {
		switch (name) {
			case "BatteryRam":
				return new BatteringRam(player);
			case "Confusor":
				return new Flamethrower(player);
			case "HomingBombLauncher":
				return new HomingBombLauncher(player);
			case "HomingMissileLauncher":
				return new HomingMissileLauncher(player);
			case "MineDetector":
				return new MineDetector(player);
			case "MineLauncher":
				return new MineLauncher(player);
			case "Minigun":
				return new Minigun(player);
			case "ParalyseShotLauncher":
				return new ParalyseShotLauncher(player);
			case "TurretLauncher":
				return new TurretLauncher(player);
			case "RocketLauncher":
				return new RocketLauncher(player);
			case "":
				return new NullWeapon(player);
			default:
				throw new UnknownWeapon(name);
		}
	}
}
