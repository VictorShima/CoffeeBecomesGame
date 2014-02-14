package com.md.mechevo.game.condition;

import java.util.ArrayList;

import com.md.mechevo.game.Player;
import com.md.mechevo.game.State;

/**
 * Selects the closest/furthest visible enemy
 */
public class EnemySpotted extends Condition {
    private Player preferredEnemy;

    public EnemySpotted(Player owner, ArrayList<String> param) {
        super(owner, param);
    }

    public Player getPreferredEnemy() {
        return preferredEnemy;
    }

    public void setPreferredEnemy(Player preferredEnemy) {
        this.preferredEnemy = preferredEnemy;
    }

    /**
     * Check if the condition applies.
     *
     * @param state Current State of the game
     * @return True if the condition applies
     */
    @Override
    public boolean check(State state) {
        // TODO the preferredEnemy can be the closest or the farthest
        ArrayList<Player> players = getOwner().fieldOfView(state, Player.FieldOfViewAngle.VIEW);
        if(!players.isEmpty()){
            double nearestDist = 99999;
            double dist;
            for(Player p : players){
                double distX = this.getOwner().getPosition().getX() - p.getPosition().getX();
                double distY = this.getOwner().getPosition().getY() - p.getPosition().getY();
                dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
                if(dist < nearestDist){
                    nearestDist = dist;
                    this.setPreferredEnemy(p);
                }
            }
            return true;
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
        return this.getPreferredEnemy();
    }
}
