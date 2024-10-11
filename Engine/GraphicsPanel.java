package Engine;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements Runnable {

    int run = 0;

    private static int FPS = 60;

    private static double delta_time;

    private static ArrayList<GraphicsItem> graphics_item_list = new ArrayList<GraphicsItem>();

    public static HashMap<String, Integer> __actions_just_pressed = new HashMap<String, Integer>();
    public static HashMap<String, Integer> __actions_just_released = new HashMap<String, Integer>();

    public GraphicsPanel(int width, int height){
        setBounds(0, 0, width, height);
        setFocusable(true);
        setDoubleBuffered(true);
        setEnabled(true);
        setLayout(null);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(true){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                delta_time = delta / FPS;
                update(delta_time);
                repaint();
                delta--;
            }
        }
    }

    private void update(double delta_time){
        // run += 300 * delta_time;
        // if(Input.is_action_pressed("Accept")){
        //     System.out.println("Aceito");
        // }

        // If it's zero, then the action has been just pressed, so I increment one for the next if.
        // If isn't zero, then the action was just pressed, then it shouldn't be said as just pressed.
        for(String str : __actions_just_pressed.keySet()){
            if(__actions_just_pressed.get(str) == 0){
                __actions_just_pressed.replace(str, 1);
            } else {
                Input.__action_is_no_more_just_pressed(str);
            }
        }


        // If it's zero, then the action has been just released, so I increment one for the next if.
        // If isn't zero, then the action was just released, then it shouldn't be said as just released.
        for(String str : __actions_just_released.keySet()){
            if(__actions_just_released.get(str) == 0){
                __actions_just_released.replace(str, 1);
            } else {
                Input.__action_is_no_more_just_released(str);
            }
        }

        // Update every class that inherits GraphicsItem
        for (GraphicsItem graphics : graphics_item_list) {
            graphics.update(delta_time);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Draw every class that inherits GraphicsItem
        for (GraphicsItem graphics : graphics_item_list) {
            graphics.draw(g2);
        }
        g2.dispose();
    }

    public static double get_delta_time() {
        return delta_time;
    }

    public static int get_FPS() {
        return FPS;
    }

    public static void set_FPS(int FPS) {
        GraphicsPanel.FPS = FPS;
    }

    public static void __add_graphic_item(GraphicsItem g_item){
        graphics_item_list.add(g_item);
    }

}