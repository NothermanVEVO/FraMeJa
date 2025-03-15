package frameja.variables.collisions;

import frameja.variables.Vector2;

/**
 * The base class for the other geometric forms.
 */
public abstract class Geom2 {

    /**
     * Check if the geometric form contains or intersect with a point.
     * @param vct The {@link Vector2}.
     * @return {@code true} if the geometric form contains or intersect with a point, {@code false} otherwise.
     */
    public abstract boolean collideWith(Vector2 vct);

    /**
     * Check if the geometric form contains or intersect with a line.
     * @param line The {@link Line2}.
     * @return {@code true} if the geometric form contains or intersect with a line, {@code false} otherwise.
     */
    public abstract boolean collideWith(Line2 line);

    /**
     * Check if the geometric form contains or intersect with a rectangle.
     * @param rect The {@link Rect2}.
     * @return {@code true} if the geometric form contains or intersect with a rectangle, {@code false} otherwise.
     */
    public abstract boolean collideWith(Rect2 rect);

    /**
     * Check if the geometric form contains or intersect with a circle.
     * @param circle The {@link Circle2}.
     * @return {@code true} if the geometric form contains or intersect with a circle, {@code false} otherwise.
     */
    public abstract boolean collideWith(Circle2 circle);

    /**
     * Check if the geometric form contains or intersect with a polygon.
     * @param poly The {@link Poly2}
     * @return {@code true} if the geometric form contains or intersect with a polygon, {@code false} otherwise.
     */
    public abstract boolean collideWith(Poly2 poly);

}
