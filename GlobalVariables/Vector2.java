package GlobalVariables;

public class Vector2 {

    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 RIGHT = new Vector2(1, 0);
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 DOWN = new Vector2(0, 1);

    public double x = 0;
    public double y = 0;

    public Vector2(){}

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2 sum(Vector2 vct){
        return new Vector2(x + vct.x, y + vct.y);
    }

    public Vector2 sub(Vector2 vct){
        return new Vector2(x - vct.x, y - vct.y);
    }

    public Vector2 mult(Vector2 vct){
        return new Vector2(x * vct.x, y * vct.y);
    }

    public Vector2 mult(double num){
        return new Vector2(x * num, y * num);
    }

    public Vector2 div(Vector2 vct){
        return new Vector2(x / vct.x, y / vct.y);
    }

    public Vector2 div(double num){
        return new Vector2(x / num, y / num);
    }

    public Vector2 abs(){
        return new Vector2(Math.abs(x), Math.abs(y));
    }

    public Vector2 normalized(){
        double norma = Math.sqrt(x * x + y * y);
        return new Vector2(x / norma, y / norma);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}
