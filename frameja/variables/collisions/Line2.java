package frameja.variables.collisions;

import frameja.variables.Vector2;

public class Line2 extends Geom2 {

    Vector2 start;
    Vector2 end;

    public Line2(){}

    public Line2(double x1, double y1, double x2, double y2){
        
    }

    public Line2(Vector2 start, Vector2 end){
        
    }

    @Override
    public boolean collideWith(Vector2 vct) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collideWith'");
    }

    // NOT TESTED
    @Override
    public boolean collideWith(Line2 line) {
        float uA = (float) (((line.end.x - line.start.x) * (start.y - line.start.y) - (line.end.y - line.start.y) * 
            (start.x - line.start.x)) / ((line.end.y - line.start.y) * (end.x - start.x) - 
            (line.end.x - line.start.x) * (end.y - start.y)));
        
        float uB = (float) (((end.x - start.x) * (start.y - line.start.y) - (end.y - start.y) * 
            (start.x - line.start.x)) / ((line.end.y - line.start.y) * (end.x - start.x) - 
            (line.end.x - line.start.x) * (end.y - start.y)));

        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean collideWith(Rect2 rect) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colideWith'");
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
