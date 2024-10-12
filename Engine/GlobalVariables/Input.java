package Engine.GlobalVariables;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import Engine.UI.GraphicsPanel;

import java.util.ArrayList;
import java.util.HashMap;

public class Input implements KeyListener, MouseInputListener, MouseWheelListener{

    public static final int MOUSE_WHEEL_UP = -20;
    public static final int MOUSE_WHEEL_DOWN = -10;

    private static Vector2 mouse_position = new Vector2();
    private static Vector2 mouse_position_when_pressed = new Vector2();
    private static Vector2 mouse_position_when_released = new Vector2();

    private static boolean any_key_pressed = false;
    private static int any_key_count = 0;

    private static boolean mouse_pressed = false;
    private static int mouse_count = 0;

    private static boolean anything_pressed = false;
    private static int anything_count = 0;

    private static HashMap<String, Action> input_actions = new HashMap<String, Action>();

    private static boolean first_time_init = true;

    public Input(){
        if(first_time_init){
            first_time_init = false;
        } else{
            try {
                throw new Exception("Object \"Input\" can't be created more than 1 time!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key_code = e.getKeyCode();
        
        any_key_pressed = true;
        any_key_count++;

        anything_pressed = true;
        anything_count++;

        for(Action action : input_actions.values()){
            for (int i = 0; i < action.keyboard_codes.length; i++) {
                if(action.keyboard_codes[i] == key_code && !action.pressed_keys.contains(key_code) && 
                    action.released_keys.contains(key_code)){
                        action.pressed_keys.add(key_code);
                        action.released_keys.remove((Object) key_code);
                }
            }
            if(action.pressed_keys.size() > 0 && action.released_keys.size() < action.number_of_keys){
                if(!action.was_just_pressed){
                    action.is_just_pressed = true;
                    action.was_just_pressed = true;
                    GraphicsPanel.__actions_just_pressed.put(action.name, 0);
                }
                action.is_pressed = true;
                action.is_released = false;
                action.was_just_released = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key_code = e.getKeyCode();
        
        any_key_count--;
        if(any_key_count == 0){
            any_key_pressed = false;
        }

        anything_count--;
        if(anything_count == 0){
            anything_pressed = false;
        }

        for(Action action : input_actions.values()){
            for (int i = 0; i < action.keyboard_codes.length; i++) {
                if(action.keyboard_codes[i] == key_code && action.pressed_keys.contains(key_code) && 
                    !action.released_keys.contains(key_code)){
                        action.pressed_keys.remove((Object) key_code);
                        action.released_keys.add(key_code);
                }
            }
            if(action.released_keys.size() > 0 && action.pressed_keys.size() == 0){
                if(!action.was_just_released){
                    action.is_just_released = true;
                    action.was_just_released = true;
                    GraphicsPanel.__actions_just_released.put(action.name, 0);
                }
                action.is_pressed = false;
                action.was_just_pressed = false;
                action.is_released = true;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse_position_when_pressed.x = e.getX();
        mouse_position_when_pressed.y = e.getY();

        mouse_pressed = true;
        mouse_count++;

        anything_pressed = true;
        anything_count++;

        int mouse_code = e.getButton();
        
        for(Action action : input_actions.values()){
            for (int i = 0; i < action.mouse_codes.length; i++) {
                if(action.mouse_codes[i] == mouse_code && !action.pressed_keys.contains(mouse_code) && 
                    action.released_keys.contains(mouse_code)){
                        action.pressed_keys.add(mouse_code);
                        action.released_keys.remove((Object) mouse_code);
                }
            }
            if(action.pressed_keys.size() > 0 && action.released_keys.size() < action.number_of_keys){
                if(!action.was_just_pressed){
                    action.is_just_pressed = true;
                    action.was_just_pressed = true;
                    GraphicsPanel.__actions_just_pressed.put(action.name, 0);
                }
                action.is_pressed = true;
                action.is_released = false;
                action.was_just_released = false;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse_position_when_released.x = e.getX();
        mouse_position_when_released.y = e.getY();

        mouse_count--;
        if(mouse_count == 0){
            mouse_pressed = false;
        }

        anything_count--;
        if(anything_count == 0){
            anything_pressed = false;
        }

        int mouse_code = e.getButton();

        for(Action action : input_actions.values()){
            for (int i = 0; i < action.mouse_codes.length; i++) {
                if(action.mouse_codes[i] == mouse_code && action.pressed_keys.contains(mouse_code) && 
                    !action.released_keys.contains(mouse_code)){
                        action.pressed_keys.remove((Object) mouse_code);
                        action.released_keys.add(mouse_code);
                }
            }
            if(action.released_keys.size() > 0 && action.pressed_keys.size() == 0){
                if(!action.was_just_released){
                    action.is_just_released = true;
                    action.was_just_released = true;
                    GraphicsPanel.__actions_just_released.put(action.name, 0);
                }
                action.is_pressed = false;
                action.was_just_pressed = false;
                action.is_released = true;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouse_position.x = e.getX();
        mouse_position.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_position.x = e.getX();
        mouse_position.y = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int wheel_code = e.getWheelRotation();
        wheel_code = wheel_code == -1 ? MOUSE_WHEEL_UP : wheel_code == 1 ? MOUSE_WHEEL_DOWN : wheel_code;

        any_key_pressed = true;
        any_key_count++;

        anything_pressed = true;
        anything_count++;

        for(Action action : input_actions.values()){
            for (int i = 0; i < action.mouse_wheel_codes.length; i++) {
                if(action.mouse_wheel_codes[i] == wheel_code && !action.pressed_keys.contains(wheel_code) && 
                    action.released_keys.contains(wheel_code)){
                        action.pressed_keys.add(wheel_code);
                        action.released_keys.remove((Object) wheel_code);
                }
            }
            if(action.pressed_keys.size() > 0 && action.released_keys.size() < action.number_of_keys){
                if(!action.was_just_pressed){
                    action.is_just_pressed = true;
                    action.was_just_pressed = true;
                    GraphicsPanel.__actions_just_pressed.put(action.name, 0);
                }
                if(wheel_code == MOUSE_WHEEL_UP){
                    GraphicsPanel.__wheel_moved_up = 0;
                } else if(wheel_code == MOUSE_WHEEL_DOWN){
                    GraphicsPanel.__wheel_moved_down = 0;
                }
                action.is_pressed = true;
                action.is_released = false;
                action.was_just_released = false;
            }
        }
    }

    public static void __mouse_wheel_up_released(){

        any_key_count--;
        if(any_key_count == 0){
            any_key_pressed = false;
        }

        anything_count--;
        if(anything_count == 0){
            anything_pressed = false;
        }

        for(Action action : input_actions.values()){
            for (int i = 0; i < action.mouse_wheel_codes.length; i++) {
                if(action.mouse_wheel_codes[i] == MOUSE_WHEEL_UP && action.pressed_keys.contains(MOUSE_WHEEL_UP) && 
                    !action.released_keys.contains(MOUSE_WHEEL_UP)){
                        action.pressed_keys.remove((Object) MOUSE_WHEEL_UP);
                        action.released_keys.add(MOUSE_WHEEL_UP);
                }
            }
            if(action.released_keys.size() > 0 && action.pressed_keys.size() == 0){
                if(!action.was_just_released){
                    action.is_just_released = true;
                    action.was_just_released = true;
                    GraphicsPanel.__actions_just_released.put(action.name, 0);
                }
                action.is_pressed = false;
                action.was_just_pressed = false;
                action.is_released = true;
            }
        }
    }

    public static void __mouse_wheel_down_released(){
        any_key_count--;
        if(any_key_count == 0){
            any_key_pressed = false;
        }

        anything_count--;
        if(anything_count == 0){
            anything_pressed = false;
        }

        for(Action action : input_actions.values()){
            for (int i = 0; i < action.mouse_wheel_codes.length; i++) {
                if(action.mouse_wheel_codes[i] == MOUSE_WHEEL_DOWN && action.pressed_keys.contains(MOUSE_WHEEL_DOWN) && 
                    !action.released_keys.contains(MOUSE_WHEEL_DOWN)){
                        action.pressed_keys.remove((Object) MOUSE_WHEEL_DOWN);
                        action.released_keys.add(MOUSE_WHEEL_DOWN);
                }
            }
            if(action.released_keys.size() > 0 && action.pressed_keys.size() == 0){
                if(!action.was_just_released){
                    action.is_just_released = true;
                    action.was_just_released = true;
                    GraphicsPanel.__actions_just_released.put(action.name, 0);
                }
                action.is_pressed = false;
                action.was_just_pressed = false;
                action.is_released = true;
            }
        }
    }

    public static void create_new_action(String name, int[] keyboard_codes, int[] mouse_codes, int[] mouse_wheel_codes){
        
        for(String str : input_actions.keySet()){
            if(name.equals(str)){
                try {
                    throw new Exception("Actions with name repeated! -> \"" + name + "\"");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }

        String a_name;
        int[] a_keyboard_codes;
        int[] a_mouse_codes;
        int[] a_mouse_wheel_codes;
        if(name.isBlank()){
            try {
                throw new Exception("Action name cannot be blank!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
            return;
        } else{
            a_name = name;
        }
        if(keyboard_codes.length == 0 && mouse_codes.length == 0 && mouse_wheel_codes.length == 0){
            try {
                throw new Exception("Action should have at least ONE key code!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
            return;
        } else{
            a_keyboard_codes = keyboard_codes;
            a_mouse_codes = mouse_codes;
            a_mouse_wheel_codes = mouse_wheel_codes;
        }

        input_actions.put(a_name, new Action(a_name, a_keyboard_codes, a_mouse_codes, a_mouse_wheel_codes));
    }

    private static boolean action_exist(String name){
        if(!input_actions.containsKey(name)){
            try {
                throw new Exception("Action \"" + name + "\" not found!");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean is_action_pressed(String name){
        if(action_exist(name)){
            return input_actions.get(name).is_pressed;
        }
        return false;
    }

    public static boolean is_action_just_pressed(String name){
        if(action_exist(name)){
            return input_actions.get(name).is_just_pressed;
        }
        return false;
    }

    public static boolean is_action_released(String name){
        if(action_exist(name)){
            return input_actions.get(name).is_released;
        }
        return false;
    }

    public static boolean is_action_just_released(String name){
        if(action_exist(name)){
            return input_actions.get(name).is_just_released;
        }
        return false;
    }

    public static boolean is_any_key_pressed() {
        return any_key_pressed;
    }

    public static boolean is_mouse_pressed() {
        return mouse_pressed;
    }

    public static boolean is_mouse_released() {
        return !mouse_pressed;
    }

    public static boolean is_anything_pressed() {
        return anything_pressed;
    }

    public static void __action_is_no_more_just_pressed(String name){
        input_actions.get(name).is_just_pressed = false;
    }

    public static void __action_is_no_more_just_released(String name){
        input_actions.get(name).is_just_released = false;
    }

    public static int get_axis(String negative_action, String positive_action){
        int value = 0;
        if(Input.is_action_pressed(negative_action)) value--;
        if(Input.is_action_pressed(positive_action)) value++;
        return value;
    }

    public static Vector2 get_vector(String negative_x, String positive_x, String negative_y, String positive_y){
        return new Vector2(get_axis(negative_x, positive_x), get_axis(negative_y, positive_y));
    }

}

class Action{

    String name;
    int[] keyboard_codes;
    int[] mouse_codes;
    int[] mouse_wheel_codes;

    int number_of_keys;

    boolean is_pressed = false;
    boolean is_released = true;

    boolean is_just_pressed = false;
    boolean is_just_released = false;

    boolean was_just_pressed = false;
    boolean was_just_released = false;

    int number_of_pressed_keys = 0;
    int number_of_released_keys = 0;

    ArrayList<Integer> pressed_keys = new ArrayList<Integer>();
    ArrayList<Integer> released_keys = new ArrayList<Integer>();

    Action(String name, int[] keyboard_codes, int[] mouse_codes, int[] mouse_wheel_codes){
        this.name = name;
        this.keyboard_codes = keyboard_codes;
        this.mouse_codes = mouse_codes;
        this.mouse_wheel_codes = mouse_wheel_codes;
        for(int i : keyboard_codes){
            released_keys.add(i);
        }
        for(int i : mouse_codes){
            released_keys.add(i);
        }
        for(int i : mouse_wheel_codes){
            released_keys.add(i);
        }
        number_of_keys = keyboard_codes.length + mouse_codes.length + mouse_wheel_codes.length;
    }

}