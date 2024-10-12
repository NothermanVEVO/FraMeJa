package src;

import java.awt.Graphics2D;

import Engine.AbstractClasses.GraphicsItemAuto;
import Engine.GlobalVariables.Input;
import Engine.GlobalVariables.Vector2;


/**
 * CLASS CREATED FOR A TEST DEMONSTRATION
 * REMOVE THIS LATER
 */

public class Player extends GraphicsItemAuto {

    Vector2 position = Vector2.ZERO;
    double speed = 300;

    @Override
    public void create() {
    }

    @Override
    public void update(double delta_time) {
        Vector2 direction = Input.get_vector("Move Left", "Move Right", "Move Up", "Move Down").normalized();
        position = position.sum(direction.mult(speed * delta_time));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillRect((int) position.x, (int) position.y, 100, 100);
    }
    
}
