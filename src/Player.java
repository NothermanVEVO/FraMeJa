package src;

import java.awt.Graphics2D;

import Engine.GraphicsItem;

public class Player extends GraphicsItem{

    @Override
    public void create() {
        System.out.println("sou o player");
    }

    @Override
    public void update(double delta_time) {
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillRect(0, 0, 100, 100);
    }
    
}
