package Engine;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class GraphicPanel extends JPanel implements Runnable {

    int run = 0;

    private static int FPS = 60;

    private static double delta_time;

    private static ArrayList<GraphicsItem> graphics_item_list = new ArrayList<GraphicsItem>();

    public static HashMap<String, Integer> _actions_just_pressed = new HashMap<String, Integer>();
    public static HashMap<String, Integer> _actions_just_released = new HashMap<String, Integer>();

    public GraphicPanel(int width, int height){
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

        // If it's zero, then the action has been just done, so I increment one for the next if.
        // If isn't zero, then the action was just done, then it shouldn't be said as just pressed.
        for(String str : _actions_just_pressed.keySet()){
            if(_actions_just_pressed.get(str) == 0){
                _actions_just_pressed.replace(str, 1);
            } else {
                Input._action_is_no_more_just_pressed(str);
            }
        }

        for(String str : _actions_just_released.keySet()){
            if(_actions_just_released.get(str) == 0){
                _actions_just_released.replace(str, 1);
            } else {
                Input._action_is_no_more_just_released(str);
            }
        }

        int speed = 800;

        if (Input.is_action_just_pressed("Accept")) {
            run += speed * delta_time;
        }

        for (GraphicsItem graphics : graphics_item_list) {
            graphics.update(delta_time);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRect(run, 0, 100, 100);
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
        GraphicPanel.FPS = FPS;
    }

    public static void add_graphic_item(GraphicsItem g_item){
        graphics_item_list.add(g_item);
    }

}