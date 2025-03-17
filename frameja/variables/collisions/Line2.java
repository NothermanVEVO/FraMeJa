package frameja.variables.collisions;

import frameja.variables.Vector2;

public class Line2 extends Geom2 {

    public Vector2 start;
    public Vector2 end;

    private static final double EPSILON = 0.001f;

    public Line2(){
        start = new Vector2();
        end = new Vector2();
    }

    public Line2(double x1, double y1, double x2, double y2){
        start = new Vector2(x1, y1);
        end = new Vector2(x2, y2);
    }

    public Line2(Vector2 start, Vector2 end){
        this.start = start;
        this.end = end;
    }

    /**
     * Check if the line contains or intersect with a point.
     * @param vct The {@link Vector2}.
     * @return {@code true} if the line contains or intersect with a point, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Vector2 vct) {
        double m = (end.y - start.y) / (end.x - start.x);
        double b = start.y - m * start.x;
        return Math.abs(vct.y - (m * vct.x +b)) < EPSILON;
    }

    /**
     * Check if the line contains or intersect with a line.
     * @param line The {@link Line2}.
     * @return {@code true} if the line contains or intersect with a line, {@code false} otherwise.
     */
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

    /**
     * Check if the line contains or intersect with a rectangle.
     * @param rect The {@link Rect2}.
     * @return {@code true} if the line contains or intersect with a rectangle, {@code false} otherwise.
     */
    @Override
    public boolean collideWith(Rect2 rect) {
        return rect.collideWith(this);
    }

    /**
     * Check if the line contains or intersect with a circle.
     * @param circle The {@link Circle2}.
     * @return {@code true} if the line contains or intersect with a circle, {@code false} otherwise.
     */
    // https://mathworld.wolfram.com/Circle-LineIntersection.html
    @Override
    public boolean collideWith(Circle2 circle) {
        if(circle.collideWith(start) || circle.collideWith(end)){
            return true;
        }
        return collisionPoint(circle) != null;
    }

    @Override
    public boolean collideWith(Poly2 poly) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colideWith'");
    }

    public Vector2 collisionPoint(Line2 line){
        float uA = (float) (((line.end.x - line.start.x) * (start.y - line.start.y) - (line.end.y - line.start.y) * 
            (start.x - line.start.x)) / ((line.end.y - line.start.y) * (end.x - start.x) - 
            (line.end.x - line.start.x) * (end.y - start.y)));
        
        float uB = (float) (((end.x - start.x) * (start.y - line.start.y) - (end.y - start.y) * 
            (start.x - line.start.x)) / ((line.end.y - line.start.y) * (end.x - start.x) - 
            (line.end.x - line.start.x) * (end.y - start.y)));

        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
            return new Vector2(start.x + (uA * (end.x - start.x)), 
                                start.y + (uA * (end.y - start.y)));
        }
        return null;
    }

    public Vector2 collisionPoint(Rect2 rect){
        Line2 left = new Line2(rect.position.x, rect.position.y, rect.position.x, 
                                rect.position.y + rect.size.y);
        Line2 right = new Line2(rect.position.x + rect.size.x, rect.position.y, 
                                rect.position.x + rect.size.x, rect.position.y + rect.size.y);
        Line2 up = new Line2(rect.position.x, rect.position.y, rect.position.x + rect.size.x, 
                                rect.position.y);
        Line2 down = new Line2(rect.position.x, rect.position.y + rect.size.y, 
                                rect.position.x + rect.size.x, rect.position.y + rect.size.y);

        Vector2 leftCol = collisionPoint(left);
        Vector2 rightCol = collisionPoint(right);
        Vector2 upCol = collisionPoint(up);
        Vector2 downCol = collisionPoint(down);

        if(leftCol != null) {
            if(rightCol != null) {
                return leftCol.distanceSquaredTo(start) < 
                        rightCol.distanceSquaredTo(start) ? leftCol : rightCol;
            } else if(upCol != null){
                return leftCol.distanceSquaredTo(start) < 
                        upCol.distanceSquaredTo(start) ? leftCol : upCol;
            } else if(downCol != null){
                return leftCol.distanceSquaredTo(start) < 
                        downCol.distanceSquaredTo(start) ? leftCol : downCol;
            }
            return leftCol;
        } else if(rightCol != null) {
            if(upCol != null){
                return rightCol.distanceSquaredTo(start) < 
                        upCol.distanceSquaredTo(start) ? rightCol : upCol;
            } else if(downCol != null){
                return rightCol.distanceSquaredTo(start) < 
                        downCol.distanceSquaredTo(start) ? rightCol : downCol;
            }
            return rightCol;
        } else if(upCol != null) {
             if(downCol != null){
                return upCol.distanceSquaredTo(start) < 
                        downCol.distanceSquaredTo(start) ? upCol : downCol;
            }
            return upCol;
        } else {
            return downCol;
        }
    }

    // ADDAPTED: https://mathworld.wolfram.com/Circle-LineIntersection.html
    public Vector2 collisionPoint(Circle2 circle){
        // To prevent a case where occur a division by zero.
        if (start.equal(end)) {
            return null;
        }

        Line2 nLine = new Line2(start.sub(circle.position), end.sub(circle.position));

        double dx = nLine.end.x - nLine.start.x;
        double dy = nLine.end.y - nLine.start.y;
        double dr = Math.pow(dx, 2) + Math.pow(dy, 2);
        double d = (nLine.start.x * nLine.end.y) - (nLine.start.y * nLine.end.x);

        double delta = (Math.pow(circle.radius, 2) * dr) - Math.pow(d, 2);

        if (delta < 0){
            return null;
        }

        Vector2 p1 = new Vector2();
        Vector2 p2 = new Vector2();

        int sgn = dy < 0 ? -1 : 1;
        
        double sqrtDelta = Math.sqrt(delta);

        p1.x = ((d * dy) - (sgn * dx) * sqrtDelta) / dr;
        p1.y = ((-d * dx) - (Math.abs(dy) * sqrtDelta)) / dr;

        p2.x = ((d * dy) + (sgn * dx) * sqrtDelta) / dr;
        p2.y = ((-d * dx) + (Math.abs(dy) * sqrtDelta)) / dr;

        p1 = p1.sum(circle.position);
        p2 = p2.sum(circle.position);

        // Those previous lines are a formula for a infinite line
        // Now, we check if they are within the segment.

        if (!withinSegment(p1) && !withinSegment(p2)){
            return null;
        }

        // If the start point is inside the circle, then will calculate the distance based on the end point, 
        // case the start point is outside, it will calculate based in the start point.
        if (circle.collideWith(start)) {
            return p1.distanceSquaredTo(end) < p2.distanceSquaredTo(end) ? p1 : p2;
        } else{
            return p1.distanceSquaredTo(start) < p2.distanceSquaredTo(start) ? p1 : p2;
        }

    }

    public Vector2 collisionPoint(Poly2 poly){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collisionPoint'");
    }

    private boolean withinSegment(Vector2 vct) {
        return Math.min(start.x, end.x) <= vct.x && vct.x <= Math.max(start.x, end.x) &&
               Math.min(start.y, end.y) <= vct.y && vct.y <= Math.max(start.y, end.y);
    }
    
}
