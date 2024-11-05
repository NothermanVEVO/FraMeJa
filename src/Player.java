package src;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

import engine.abstractclasses.GraphicsItem;
import engine.abstractclasses.GraphicsItemAuto;
import engine.util.GraphicsPanel;
import engine.util.Input;
import engine.util.Timer;
import engine.variables.Vector2;

/**
 * CLASS CREATED FOR A TEST DEMONSTRATION
 * REMOVE THIS LATER
 */

public class Player extends GraphicsItemAuto {

    Vector2 size = new Vector2(100, 100);
    double speed = 300;

    double lowest_fps = Double.MAX_VALUE;
    double highest_fps = Double.MIN_VALUE;

    int segundos = 0;

    // Ball ball = new Ball();

    @Override
    public void create() {
        // ball.setZ(4);
        // addChild(ball);
        // GraphicsPanel.addGraphicItem(ball);
        setZ(3);
        Timer timer = new Timer();
        timer.addListeners(() -> timeout());
        timer.start(1);
    }

    @Override
    public void update(double deltaTime) {
        Vector2 direction = Input.getVector("Move Left", "Move Right", "Move Up", "Move Down").normalized();
        position = position.sum(direction.mult(speed * deltaTime));

        if(position.x + size.x > GraphicsPanel.getPanelWidth()){
            position.x = GraphicsPanel.getPanelWidth() - size.x;
        } else if (position.x < 0){
            position.x = 0;
        }
        if(position.y + size.y > GraphicsPanel.getPanelHeight()){
            position.y = GraphicsPanel.getPanelHeight() - size.y;
        } else if (position.y < 0){
            position.y = 0;
        }
        
        if(Input.isActionJustPressed("Mouse 1")){
            lowest_fps = Double.MAX_VALUE;
            highest_fps = Double.MIN_VALUE;
        }

        if(Input.isActionJustPressed("Create")){
            addChild(new Ball());
        }

        if(Input.isActionJustPressed("Destroy")){
            ArrayList<GraphicsItem> children = getChildren();
            if(!children.isEmpty()){
                // removeChild(children.get(children.size() - 1));
                children.get(children.size() - 1).free();
            }
        }

        if(Input.isActionJustPressed("Destroy All")){
            free();
        }

        double fps = GraphicsPanel.getCurrentFps();
        if(fps > highest_fps){
            highest_fps = fps;
        }
        if(fps < lowest_fps){
            lowest_fps = fps;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect((int) position.x, (int) position.y, (int) size.x, (int) size.y);
        g2.drawString("FPS: " + Integer.toString(Math.round((float) GraphicsPanel.getCurrentFps())), 0, 20);
        g2.drawString("Highest FPS: " + Integer.toString(Math.round((float) highest_fps)), 0, 40);
        g2.drawString("Lowest FPS: " + Integer.toString(Math.round((float) lowest_fps)), 0, 60);
    }

    public void timeout(){
        segundos++;
        System.out.println("Passou " + segundos + " segundos.");
    }

}

class Ball extends GraphicsItem{

    Ball(){
        Random rng = new Random();
        position.x = rng.nextInt(GraphicsPanel.getPanelWidth());
        position.y = rng.nextInt(GraphicsPanel.getPanelHeight());
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        // g2.fillOval(200, 200, 100, 100);
        g2.fillOval((int) position.x, (int) position.y, 100, 100);
    }

}
