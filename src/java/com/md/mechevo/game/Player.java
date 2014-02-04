package com.md.mechevo.game;

public class Player extends Solid implements CollisionVisitor {
    public Player(float width, float height, Coordinate initialCoordinate) {
        super(width, height, initialCoordinate);
    }

    @Override
    void accept(CollisionVisitor s) {

    }

    @Override
    public void collidesWith(Player p) {

    }

    @Override
    public void collidesWith(Projectile p) {

    }

    @Override
    public void collidesWith(Obstacle o) {

    }

    @Override
    public void collidesWith(Sentry s) {

    }
}
