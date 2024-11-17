package frameja.variables;

/**
 * Represent a 2D coordinates.
 */
public class Vector2 {

    // Coordinate x.
    public double x = 0;
    
    // Coordinate y.
    public double y = 0;

    /**
     * Create a {@code Vector2} of position (0, 0).
     */
    public Vector2(){}

    /**
     * Create a {@code Vector2} of the given position.
     * @param x Coordinate x.
     * @param y Coordinate y.
     */
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Sum a {@code Vector2} with another {@code Vector2}
     * @param vct The {@code Vector2} to be sum.
     * @return The sum of {@code this} and {@code vct}.
     */
    public Vector2 sum(Vector2 vct){
        return new Vector2(x + vct.x, y + vct.y);
    }

    /**
     * Subtract a {@code Vector2} with another {@code Vector2}
     * @param vct The {@code Vector2} to subtract {@code this}.
     * @return The subtraction of {@code this} and {@code vct}.
     */
    public Vector2 sub(Vector2 vct){
        return new Vector2(x - vct.x, y - vct.y);
    }

    /**
     * Multiply a {@code Vector2} with another {@code Vector2}
     * @param vct The {@code Vector2} to multiply.
     * @return The multiplication of {@code this} and {@code vct}.
     */
    public Vector2 mult(Vector2 vct){
        return new Vector2(x * vct.x, y * vct.y);
    }

    /**
     * Multiply a {@code Vector2} with a value {@code double}
     * @param num A {@code double} to multiply {@code this}.
     * @return The multiplication of {@code this} and {@code num}.
     */
    public Vector2 mult(double num){
        return new Vector2(x * num, y * num);
    }

    /**
     * Divide a {@code Vector2} with another {@code Vector2}
     * @param vct The {@code Vector2} to divide.
     * @return The division of {@code this} and {@code vct}.
     */
    public Vector2 div(Vector2 vct){
        return new Vector2(x / vct.x, y / vct.y);
    }

    /**
     * Divide a {@code Vector2} with a value {@code double}
     * @param num A {@code double} to divide {@code this}.
     * @return The division of {@code this} and {@code num}.
     */
    public Vector2 div(double num){
        return new Vector2(x / num, y / num);
    }

    /**
     * Negative the given {@code Vector2}.
     * @param vct The {@code Vector2} to be negative.
     * @return The negative of {@code vct}.
     */
    public static Vector2 negative(Vector2 vct){
        return vct.mult(-1);
    }

    /**
     * Check if {@code this} is equal to {@code vct}.
     * @param vct Another {@code Vector2}.
     * @return {@code true} if coordinates {@code x} and {@code y} are equal, else {@code false}.
     */
    public boolean equal(Vector2 vct){
        return x == vct.x && y == vct.y;
    }

    /**
     * Check if {@code this} is different from {@code vct}.
     * @param vct Another {@code Vector2}.
     * @return {@code true} if coordinates {@code x} or {@code y} are different, else {@code false}.
     */
    public boolean different(Vector2 vct){
        return x != vct.x || y != vct.y;
    }

    /**
     * Check if {@code this} is greater than {@code vct}.
     * @param vct Another {@code Vector2}.
     * @return {@code true} if coordinates {@code this.x} and {@code this.y} are greater, else {@code false}.
     */
    public boolean greater(Vector2 vct){
        return x > vct.x && y > vct.y;
    }

    /**
     * Check if {@code this} is greater or equal than {@code vct}.
     * @param vct Another {@code Vector2}.
     * @return {@code true} if coordinates {@code this.x} and {@code this.y} are greater or equal, 
     * else {@code false}.
     */
    public boolean greaterOrEqual(Vector2 vct){
        return x >= vct.x && y >= vct.y;
    }

    /**
     * Check if {@code this} is less than {@code vct}.
     * @param vct Another {@code Vector2}.
     * @return {@code true} if coordinates {@code this.x} and {@code this.y} are less, else {@code false}.
     */
    public boolean less(Vector2 vct){
        return x < vct.x && y < vct.y;
    }

    /**
     * Check if {@code this} is less or equal than {@code vct}.
     * @param vct Another {@code Vector2}.
     * @return {@code true} if coordinates {@code this.x} and {@code this.y} are less or equal, else {@code false}.
     */
    public boolean lessOrEqual(Vector2 vct){
        return x <= vct.x && y <= vct.y;
    }

    
    public Vector2 abs(){
        return new Vector2(Math.abs(x), Math.abs(y));
    }

    public double magnitudeSquared() {
        return x * x + y * y;
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public Vector2 normalized(){
        double lenght = magnitude();
        if(lenght > 0){
            return new Vector2(x / lenght, y / lenght);   
        } else{
            return new Vector2();
        }
    }

    public double dot(Vector2 vct) {
        return x * vct.x + y * vct.y;
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double angleTo(Vector2 vct) {
        double dotProduct = dot(vct);
        double magnitudes = magnitude() * vct.magnitude();
        if (magnitudes > 0) {
            double cosTheta = Math.max(-1, Math.min(1, dotProduct / magnitudes));
            return Math.acos(cosTheta);
        } else{
            return 0;
        }
    }

    public double angleToPoint(Vector2 vct) {
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

    public Vector2 directionTo(Vector2 vct) {
        Vector2 direction = vct.sub(this);
        return direction.normalized();
    }

    public double distanceSquaredTo(Vector2 vct) {
        return Math.pow(vct.x - x, 2) + Math.pow(vct.y - y, 2);
    }

    public double distanceTo(Vector2 vct) {
        return Math.sqrt(distanceSquaredTo(vct));
    }

    public Vector2 lerp(Vector2 vct, double weight) {
        return new Vector2(
            x + (vct.x - x) * weight,
            y + (vct.y - y) * weight
        );
    }

    public Vector2 moveToward(Vector2 vct, double delta) {
        Vector2 direction = directionTo(vct);
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

    public static Vector2 fromAngle(double angle) {
        return new Vector2(Math.cos(angle), Math.sin(angle));
    }

    public static Vector2 ZERO(){
        return new Vector2(0, 0);
    }

    public static Vector2 RIGHT(){
        return new Vector2(1, 0);
    }

    public static Vector2 LEFT(){
        return new Vector2(-1, 0);
    }

    public static Vector2 UP(){
        return new Vector2(0, -1);
    }

    public static Vector2 DOWN(){
        return new Vector2(0, 1);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}
