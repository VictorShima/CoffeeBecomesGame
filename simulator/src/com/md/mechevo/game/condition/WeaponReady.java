package com.md.mechevo.game.condition;

import static com.md.mechevo.game.weapon.Weapon.WeaponSlot;

import java.util.ArrayList;
import java.util.List;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;
import com.md.mechevo.game.weapon.Weapon;

/**
 * WeaponReady (LEFT, RIGHT, CENTER) : true if a weapon is off cooldown
 */
public class WeaponReady extends Condition {
    private WeaponSlot weaponSlot;

    public WeaponReady(Player owner, ArrayList<String> param) throws InvalidConditionParameter {
        super(owner, param);
        this.convertParam();
    }

    private void convertParam() throws InvalidConditionParameter {
        if (this.getParam().size() != 1) {
            throw new InvalidConditionParameter(WeaponReady.class.getName());
        }

        try {
            this.weaponSlot = WeaponSlot.valueOf(this.getParam().get(0));
        } catch (IllegalArgumentException e) {
            throw new InvalidConditionParameter(WeaponReady.class.getName());
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
