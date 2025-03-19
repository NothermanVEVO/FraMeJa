package frameja.variables.collisions;

import frameja.variables.Vector2;

/**
 * A class that represents the circle.
 */
public class Circle2 extends Geom2 {

    // The position of the center.
    public Vector2 position;

    // The radius.
    public double radius;

    /**
     * Create a circle in the position (0, 0) and without size.
     */
    public Circle2(){
        position = new Vector2();
        radius = 0.0f;
    }

    /**
     * Create a circle.
     * @param position The center position of the circle.
     * @param radius The radius of the circle.
     */
    public Circle2(Vector2 position, double radius){
        this.position = position;
        this.radius = radius;
    }

    /**
     * Create a circle.
     * @param x The coordinate x of the circle.
     * @param y The coordinate y of the circle.
     * @param radius The radius of the circle.
     */
    public Circle2(double x, double y, double radius){
        position = new Vector2(x, y);
        this.radius = radius;
    }

    /**
     * Check if the circle contains or intersect with a point.
     * @param vct The {@link Vector2}.
     * @return {@code true} if the circle contains or intersect with a point, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Vector2 vct) {
        return position.distanceSquaredTo(vct) <= Math.pow(radius, 2);
    }

    /**
     * Check if the circle contains or intersect with a line.
     * @param line The {@link Line2}.
     * @return {@code true} if the circle contains or intersect with a line, {@code false} otherwise.
     */
    // https://mathworld.wolfram.com/Circle-LineIntersection.html
    @Override
    public boolean collideWith(Line2 line) {
        return line.collideWith(this);
    }

    /**
     * Check if the circle contains or intersect with a rectangle.
     * @param rect The {@link Rect2}.
     * @return {@code true} if the circle contains or intersect with a rectangle, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Rect2 rect) {
        return rect.collideWith(this);
    }

    /**
     * Check if the circle contains or intersect with a circle.
     * @param circle The {@link Circle2}.
     * @return {@code true} if the circle contains or intersect with a circle, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Circle2 circle) {
        return position.distanceSquaredTo(circle.position) <= Math.pow(radius + circle.radius, 2);
    }

    @Override
    public boolean collideWith(Poly2 poly) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collideWith'");
    }

}
