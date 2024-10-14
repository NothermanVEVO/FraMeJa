package Engine.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JPanel;

import org.reflections.Reflections;

import Engine.AbstractClasses.GraphicsItem;
import Engine.AbstractClasses.GraphicsItemAuto;
import Engine.GlobalVariables.Input;

public class GraphicsPanel extends JPanel implements Runnable {

    private static Thread engine_thread;

    private static int FPS = 60;

    private static double delta_time;

    private static int width;
    private static int height;

    private static Input input;

    private static ArrayList<GraphicsItem> graphics_item_list = new ArrayList<GraphicsItem>();

    public static HashMap<String, Integer> __actions_just_pressed = new HashMap<String, Integer>();
    public static HashMap<String, Integer> __actions_just_released = new HashMap<String, Integer>();

    public static int __wheel_moved_up = -1;
    public static int __wheel_moved_down = -1;

    private static boolean first_time_init = true;

    public GraphicsPanel(int width, int height){
        if(first_time_init){
            first_time_init = false;

            GraphicsPanel.width = width;
            GraphicsPanel.height = height;

            // Adjust the JPanel
            setOpaque(false);
            setBounds(0, 0, width, height);
            setPreferredSize(new Dimension(width, height));
            setFocusable(true);
            setDoubleBuffered(true);
            setEnabled(true);
            setLayout(null);

            input = new Input();

            addKeyListener(input);
            addMouseListener(input);
            addMouseMotionListener(input);
            addMouseWheelListener(input);

            // Create and start the Thread
            engine_thread = new Thread(this);
            engine_thread.start();
        } else{
            try {
                throw new Exception("Object \"GraphicsPanel\" can't be created more than 1 time!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Auto instantiate all classes that inherits the class "GraphicsItemAuto"
        auto_load_graphics_items();

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

        if(__wheel_moved_up == 0){
            __wheel_moved_up = 1;
        } else if(__wheel_moved_up == 1){
            Input.__mouse_wheel_up_released();
            __wheel_moved_up = -1;
        }

        if(__wheel_moved_down == 0){
            __wheel_moved_down = 1;
        } else if(__wheel_moved_down == 1){
            Input.__mouse_wheel_down_released();
            __wheel_moved_down = -1;
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

    //? It change the var fps, but don't actually change the Thread fps
    public static void set_FPS(int FPS) {
        GraphicsPanel.FPS = FPS;
    }

    public static int get_width(){
        return GraphicsPanel.width;
    }

    public static int get_height(){
        return GraphicsPanel.height;
    }

    public static void __add_graphic_item(GraphicsItem g_item){
        graphics_item_list.add(g_item);
    }

    public static void remove_graphic_item(GraphicsItem g_item){
        graphics_item_list.remove(g_item);
    }

    private void auto_load_graphics_items(){
        Reflections reflections = new Reflections("src");
        Set<Class<? extends GraphicsItemAuto>> subClasses = reflections.getSubTypesOf(GraphicsItemAuto.class);
        for (Class<? extends GraphicsItemAuto> subClass : subClasses) {
            try {
                subClass.getDeclaredConstructor().newInstance();
                System.out.println("Instancia de: " + subClass.getSimpleName() + " criada.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}