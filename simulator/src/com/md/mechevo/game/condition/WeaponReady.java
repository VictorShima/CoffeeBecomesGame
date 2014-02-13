package com.md.mechevo.game.condition;

import static com.md.mechevo.game.weapon.Weapon.WeaponSlot;

import java.util.List;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;
import com.md.mechevo.game.weapon.Weapon;

/**
 * WeaponReady (left, right, central) : true if a weapon is off cooldown
 */
public class WeaponReady extends Condition {
    private final WeaponSlot weaponSlot;

    public WeaponReady(Player owner, String param) throws InvalidConditionParameter {
        super(owner, param);
        weaponSlot = this.convertParam();
    }

    private WeaponSlot convertParam() throws InvalidConditionParameter {
        try {
            return WeaponSlot.valueOf(this.getParam());
        } catch (IllegalArgumentException e) {
            throw new InvalidConditionParameter(WeaponReady.class.getName(), this.getParam());
        }
    }

    /**
     * Check if the condition applies.
     *
     * @param state Current State of the game
     * @return True if the condition applies
     */
    @Override
    public boolean check(State state) {
        List<Weapon> weapons = this.getOwner().getWeapons();
        for (Weapon weapon : weapons) {
            if ((weapon.getCurrentSlot() == weaponSlot) && weapon.getCurrentCooldown() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the preferred target for this condition.
     *
     * @param state Current State of the game
     * @return Reference to target Player or null if none
     */
    @Override
    public Player getPreferredPlayer(State state) {
        // Empty on purpose
        return null;
    }
}
