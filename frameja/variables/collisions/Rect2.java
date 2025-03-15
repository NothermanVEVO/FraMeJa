package frameja.variables.collisions;

import frameja.variables.Vector2;

public class Rect2 extends Geom2 {

    public Vector2 position;
    public Vector2 size;

    public Rect2(){
        position = new Vector2();
        size = new Vector2();
    }

    public Rect2(double x, double y, double width, double height){
        position = new Vector2(x, y);
        size = new Vector2(width, height);
    }

    public Rect2(Vector2 position, Vector2 size){
        this.position = position;
        this.size = size;
    }

    /**
     * Check if the rectangle contains or intersect with a point.
     * @param vct The {@link Vector2}.
     * @return {@code true} if the rectangle contains or intersect with a point, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Vector2 vct) {
        return vct.x >= position.x && vct.x <= position.x + size.x &&
                vct.y >= position.y && vct.y <= position.y + size.y;
    }

    /**
     * Check if the rectangle contains or intersect with a line.
     * @param line The {@link Line2}.
     * @return {@code true} if the rectangle contains or intersect with a line, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Line2 line) {
        Line2 left = new Line2(position.x, position.y, position.x, position.y + size.y);
        Line2 right = new Line2(position.x + size.x, position.y, position.x + size.x, position.y + size.y);
        Line2 up = new Line2(position.x, position.y, position.x + size.x, position.y);
        Line2 down = new Line2(position.x, position.y + size.y, position.x + size.x, position.y + size.y);
        
        if(line.collideWith(left) || line.collideWith(right) || line.collideWith(up) || 
            line.collideWith(down) || (this.collideWith(line.start) && this.collideWith(line.end))){
                return true;
        }
        return false;
    }

    /**
     * Check if the rectangle contains or intersect with a rectangle.
     * @param rect The {@link Rect2}.
     * @return {@code true} if the rectangle contains or intersect with a rectangle, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Rect2 rect) {
        return position.x < rect.position.x + rect.size.x &&
                position.x + size.x > rect.position.x &&
                position.y < rect.position.y + rect.size.y &&
                position.y + size.y > rect.position.y;
    }

    /**
     * Check if the rectangle contains or intersect with a circle.
     * @param circle The {@link Circle2}.
     * @return {@code true} if the rectangle contains or intersect with a circle, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Circle2 circle) {
        Vector2 direction = circle.position.directionTo(this.position.sum(this.size.div(2)));
        Vector2 endPos = circle.position.sum(direction.mult(circle.radius));

        Vector2 topLeft = position;
        Vector2 bottomLeft = new Vector2(position.x, position.y + size.y);
        Vector2 topRight = new Vector2(position.x + size.x, position.y);
        Vector2 bottomRight = new Vector2(position.x + size.x, position.y + size.y);

        if(circle.collideWith(topLeft) || circle.collideWith(topRight) || 
            circle.collideWith(bottomLeft) || circle.collideWith(bottomRight) || 
            collideWith(circle.position) || collideWith(endPos)){
                return true;
        }
        return false;
    }

    @Override
    public boolean collideWith(Poly2 poly) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colideWith'");
    }

}
