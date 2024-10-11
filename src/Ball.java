package src;

import java.awt.Graphics2D;

import Engine.GraphicsItem;
import Engine.Input;

public class Ball extends GraphicsItem{

    int x = 200;
    int speed = 300;

    @Override
    public void create() {
        System.out.println("sou uma bola");
    }

    @Override
    public void update(double delta_time) {
        if(Input.is_action_pressed("Accept")){
            x += speed * delta_time;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillOval(x, 200, 100, 100);
    }
    
}
