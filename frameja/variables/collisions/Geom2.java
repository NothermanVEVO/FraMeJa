package frameja.variables.collisions;

public abstract class Geom2 {

    public abstract boolean collideWith(Line2 line);

    public abstract boolean collideWith(Rect2 rect);

    public abstract boolean collideWith(Circle2 circle);

    public abstract boolean collideWith(Poly2 poly);

}
