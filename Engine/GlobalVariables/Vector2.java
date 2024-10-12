package Engine.GlobalVariables;

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

    public boolean equal(Vector2 vct){
        if(x == vct.x && y == vct.y){
            return true;
        } else{
            return false;
        }
    }

    public boolean is_different(Vector2 vct){
        if(x != vct.x || y != vct.y){
            return true;
        } else{
            return false;
        }
    }

    public boolean greater(Vector2 vct){
        if(magnitude_squared() > vct.magnitude_squared()){
            return true;
        } else{
            return false;
        }
    }

    public boolean greater_or_equal(Vector2 vct){
        if(magnitude_squared() >= vct.magnitude_squared()){
            return true;
        } else{
            return false;
        }
    }

    public boolean less(Vector2 vct){
        if(magnitude_squared() < vct.magnitude_squared()){
            return true;
        } else{
            return false;
        }
    }

    public boolean less_or_equal(Vector2 vct){
        if(magnitude_squared() <= vct.magnitude_squared()){
            return true;
        } else{
            return false;
        }
    }

    public Vector2 abs(){
        return new Vector2(Math.abs(x), Math.abs(y));
    }

    public double magnitude_squared() {
        return x * x + y * y;
    }

    public double magnitude() {
        return Math.sqrt(magnitude_squared());
    }

    public Vector2 normalized(){
        double lenght = magnitude();
        if(lenght > 0){
            return new Vector2(x / lenght, y / lenght);   
        } else{
            return Vector2.ZERO;
        }
    }

    public double dot(Vector2 vct) {
        return x * vct.x + y * vct.y;
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double angle_to(Vector2 vct) {
        double dot_product = dot(vct);
        double magnitudes = magnitude() * vct.magnitude();
        if (magnitudes > 0) {
            double cos_theta = Math.max(-1, Math.min(1, dot_product / magnitudes));
            return Math.acos(cos_theta);
        } else{
            return 0;
        }
    }

    public double angle_to_point(Vector2 vct) {
        return Math.atan2(vct.y - y, vct.x - x);
    }

    public Vector2 bounce(Vector2 vct) {
        return sub(vct.mult(2 * dot(vct)));
    }

    public Vector2 clamp(Vector2 min, Vector2 max) {
        return new Vector2(
            Math.max(min.x, Math.min(x, max.x)),
            Math.max(min.y, Math.min(y, max.y))
        );
    }

    public Vector2 clampf(double min, double max) {
        return new Vector2(
            Math.max(min, Math.min(x, max)),
            Math.max(min, Math.min(y, max))
        );
    }

    public Vector2 direction_to(Vector2 vct) {
        Vector2 direction = vct.sub(this);
        return direction.normalized();
    }

    public double distance_squared_to(Vector2 vct) {
        return Math.pow(vct.x - x, 2) + Math.pow(vct.y - y, 2);
    }

    public double distance_to(Vector2 vct) {
        return Math.sqrt(distance_squared_to(vct));
    }

    public Vector2 lerp(Vector2 vct, double weight) {
        return new Vector2(
            x + (vct.x - x) * weight,
            y + (vct.y - y) * weight
        );
    }

    public Vector2 move_toward(Vector2 vct, double delta) {
        Vector2 direction = direction_to(vct);
        return sum(direction.mult(delta));
    }

    public Vector2 ceil() {
        return new Vector2(Math.ceil(x), Math.ceil(y));
    }

    public Vector2 floor() {
        return new Vector2(Math.floor(x), Math.floor(y));
    }

    public Vector2 max(Vector2 vct) {
        return new Vector2(
            Math.max(x, vct.x),
            Math.max(y, vct.y)
        );
    }

    public Vector2 min(Vector2 vct) {
        return new Vector2(
            Math.min(x, vct.x),
            Math.min(y, vct.y)
        );
    }

    public Vector2 minf(double vct) {
        return new Vector2(
            Math.min(x, vct),
            Math.min(y, vct)
        );
    }

    public Vector2 rotated(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2(
            x * cos - y * sin,
            x * sin + y * cos
        );
    }

    public Vector2 round() {
        return new Vector2(
            Math.round(x),
            Math.round(y)
        );
    }

    public static Vector2 from_angle(double angle) {
        return new Vector2(Math.cos(angle), Math.sin(angle));
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}
