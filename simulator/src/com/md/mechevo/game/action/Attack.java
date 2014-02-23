package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;
import com.md.mechevo.game.weapon.Weapon;


/**
 * Attack will shoot with the selected weapon[s], first by checking if their cooldown is 0.
 */
public class Attack extends Action {
	private static final boolean CANCELABLE = false;
	private ArrayList<Weapon> weapons;
	private Player target;

	/**
	 * @param param the selected weapon (LEFT, CENTER, RIGHT)
	 */
	public Attack(Player owner, ArrayList<String> param) {
		super(owner, param, Attack.CANCELABLE);
		this.weapons = new ArrayList<>();
		convertParams();
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public Player getTarget() {
		return target;
	}

	public void convertParams() {
		if (this.getParam().size() == 0) {
			throw new InvalidActionParameter(Attack.class.getSimpleName());
		}
		try {
			for (String param : getParam()) {
				Weapon.WeaponSlot slot = Weapon.WeaponSlot.valueOf(param.toUpperCase());
				for (Weapon w : this.getOwner().getWeapons()) {
					if (w.getCurrentSlot() == slot) {
						this.weapons.add(w);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidActionParameter(Attack.class.getSimpleName());
		}
	}

	/**
	 * After update, already finished
	 */
	@Override
	public boolean hasFinished() {
		return true;
	}

	/**
	 * @param state Current State of the game
	 * @return True if the at least one weapon has the current cooldown equal to 0, false otherwise.
	 */
	@Override
	public boolean check(State state) {
		for (Weapon w : this.weapons) {
			if (w.getCurrentCooldown() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Begin the execution of the action. Will be called once at the start of the action.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void begin(State state) {
		target = this.getOwner().getCurrentOrder().getPreferredTarget();
		EventData eventData =
				new EventData("startAttacking").addAttribute("id", getOwner().getId());

		for (Weapon w : this.weapons) {
			if (w.getCurrentCooldown() == 0) {
				eventData.addAttribute("slot" + w.getCurrentSlot().toString(), "true");
			}
		}
		this.notifyEventObserver(eventData);
	}

	/**
	 * Execute the action.
	 *
	 * @param state Current State of the game
	 * @param dtime Duration of the round
	 */
	@Override
	public void update(State state, double dtime) {
		for (Weapon w : weapons) {
			if (w.getCurrentCooldown() == 0) {
				w.fire(state, this.getTarget());
			}
		}
	}

	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void end(State state) {
		target = this.getOwner().getCurrentOrder().getPreferredTarget();
		EventData eventData =
 new EventData("stopAttacking").addAttribute("id", getOwner().getId());

		for (Weapon w : this.weapons) {
			if (w.getCurrentCooldown() == w.getCooldown()) {
				eventData.addAttribute("slot" + w.getCurrentSlot().toString(), "true");
			}
		}
		this.notifyEventObserver(eventData);
	}
}
