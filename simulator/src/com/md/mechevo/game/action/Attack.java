package com.md.mechevo.game.action;

import java.util.ArrayList;

import com.md.mechevo.game.EventData;
import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;
import com.md.mechevo.game.weapon.Weapon;


/**
 * Attack will shoot with the selected weapon, first by checking if it's cooldown is up.
 */
public class Attack extends Action {
	private static final double DURATION = 0.5;
	private static final boolean CANCELABLE = false;
	private Weapon.WeaponSlot slot;
	private Weapon weapon;
	private Player target;

	/**
	 * @param param the selected weapon (LEFT, CENTER, RIGHT)
	 */
	public Attack(Player owner, ArrayList<String> param) {
		super(owner, param, Attack.CANCELABLE);
		convertParam();
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public Player getTarget() {
		return target;
	}

	public void convertParam() {
		if (this.getParam().size() != 1) {
			throw new InvalidActionParameter(Attack.class.getName());
		}
		try {
			ArrayList<Weapon> weapons = this.getOwner().getWeapons();
			slot = Weapon.WeaponSlot.valueOf(this.getParam().get(0).toUpperCase());
			for (Weapon w : weapons) {
				if (w.getCurrentSlot() == slot) {
					this.weapon = w;
				}
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidActionParameter(Attack.class.getName());
		}
	}

	/**
	 * Check if the the action has already finished.
	 */
	@Override
	public boolean hasFinished() {
		return DURATION <= this.getOwner().getCurrentOrder().getCurrentActionTime();
	}

	/**
	 * Check if the required condition for an action applies.
	 *
	 * @param state Current State of the game
	 * @return True if the condition applies
	 */
	@Override
	public boolean check(State state) {
		return (this.getOwner().getCurrentOrder().getCurrentActionTime() < DURATION)
				&& (this.getWeapon().getCooldown() < 0);
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
				new EventData("startAttacking").addAttribute("id", getOwner().getId())
						.addAttribute("slot", this.slot.toString());
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
		this.getWeapon().fire(state, getTarget());
	}

	/**
	 * End the execution of the action, whether it is cancelled or successfully ended.
	 *
	 * @param state Current state of the game
	 */
	@Override
	public void end(State state) {
		EventData eventData =
				new EventData("stopAttacking").addAttribute("id", getOwner().getId()).addAttribute(
						"slot", this.slot.toString());
		this.notifyEventObserver(eventData);
	}
}
