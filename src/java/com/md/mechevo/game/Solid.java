package com.md.mechevo.game;


public abstract class Solid {
    private Coordinate centerCoordinate;
    private float width;
    private float height;

    public Solid(float width, float height, Coordinate initialCoordinate) {
        this.width = width;
        this.height = height;
        this.centerCoordinate = initialCoordinate;
    }

    /**
     * @return a coordinate with the center point
     */
    Coordinate getCenterPoint() {
        return centerCoordinate;
    }

    /**
     * @return two coordinates where the top-most and left-most coordinate and the bottom-most and
     *         right-most coordinate.
     */
    Coordinate[] getBoundingBox() {
        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] =
                        new Coordinate(centerCoordinate.getX() - width / 2, centerCoordinate.getY()
                                        - height / 2);
        coordinates[1] =
                        new Coordinate(centerCoordinate.getX() + width / 2, centerCoordinate.getY()
                                        + height / 2);

        return coordinates;
    }

    /**
     * Implements the visitor design pattern to enable new operations on solids.
     * 
     * @param s the visitor
     */
    abstract void accept(CollisionVisitor s);
}
