package frameja.variables.collisions;

import frameja.variables.Vector2;

public class Rect2 extends Geom2 {

    Vector2 position = new Vector2();
    Vector2 size = new Vector2();

    public Rect2(){}

    public Rect2(double x, double y, double width, double height){
        position.x = x;
        position.y = y;
        size.x = width;
        size.y = height;
    }

    public Rect2(Vector2 position, Vector2 size){
        this.position = position;
        this.size = size;
    }

    @Override
    public boolean collideWith(Line2 line) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colideWith'");
    }

    @Override
    public boolean collideWith(Rect2 rect) {
        return position.x <= rect.position.x && position.y <= rect.position.y && 
            (position.x + size.x) >= (rect.position.x + rect.size.x) && 
            (position.y + size.y) >= (rect.position.y + rect.size.y);
    }

    @Override
    public boolean collideWith(Circle2 circle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colideWith'");
    }

    @Override
    public boolean collideWith(Poly2 poly) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colideWith'");
    }

}
