package src;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import frameja.abstractclasses.GraphicsItem;
import frameja.abstractclasses.GraphicsItemAuto;
import frameja.util.GraphicsPanel;
import frameja.util.Input;
import frameja.util.Timer;
import frameja.variables.Vector2;
import frameja.variables.collisions.Circle2;
import frameja.variables.collisions.Line2;
import frameja.variables.collisions.Rect2;

import java.awt.Color;

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

    Line2 playerLine = new Line2();
    Rect2 playerRect = new Rect2(0, 0, 100, 100);
    Circle2 playerCircle = new Circle2(0, 0, 50);

    Line2 enemyLine = new Line2(300, 100, 400, 200);
    Rect2 enemyRect = new Rect2(300, 300, 50, 50);
    Circle2 enemyCircle = new Circle2(300, 300, 100);

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

        playerLine.start = position;
        playerRect.position = position;
        playerCircle.position = position.sum(new Vector2(playerCircle.radius, playerCircle.radius));

        playerLine.end = Input.getMousePosition();

        g2.setColor(Color.RED);
        drawLine(playerLine, g2);
        // drawLine(enemyLine, g2);
        // drawRect(playerRect, g2);
        // drawRect(enemyRect, g2);
        // drawCircle(playerCircle, g2);
        drawCircle(enemyCircle, g2);
        g2.setColor(Color.BLACK);
        
        g2.setColor(Color.BLUE);
        System.out.println(playerLine.collideWith(enemyCircle));
        // Vector2 collisionPoint = playerLine.collisionPoint(enemyCircle);
        // if (collisionPoint != null) {
        //     fillCircle(new Circle2(collisionPoint, 3), g2);
        // }
    }

    public void timeout(){
        segundos++;
        // System.out.println("Passou " + segundos + " segundos.");
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
