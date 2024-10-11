package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import java.util.ArrayList;
import java.util.HashMap;

import GlobalVariables.Vector2;

public class Input implements KeyListener, MouseInputListener, MouseWheelListener{

    public final static int KEYBOARD_INPUT = 1;
    public final static int MOUSE_INPUT = 2;

    private static boolean mouse_pressed = false;
    private static boolean mouse_released = true;
    private static boolean mouse_just_pressed = false;
    private static boolean mouse_just_released = false;

    private static Vector2 mouse_position = new Vector2();
    private static Vector2 mouse_position_when_pressed = new Vector2();

    private static HashMap<String, Action> input_actions = new HashMap<String, Action>();

    private static boolean first_time_init = true;

    Input(){
        if(first_time_init){
            first_time_init = false;
        } else{
            try {
                throw new Exception("You can't create the Object Input");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key_code = e.getKeyCode();
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
                    GraphicPanel._actions_just_pressed.put(action.name, 0);
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
                    GraphicPanel._actions_just_released.put(action.name, 0);
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
        mouse_pressed = true;
        mouse_position_when_pressed.x = e.getX();
        mouse_position_when_pressed.y = e.getY();

        int mouse_code = e.getButton();
        // System.out.println(mouse_code);
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
                    GraphicPanel._actions_just_pressed.put(action.name, 0);
                }
                action.is_pressed = true;
                action.is_released = false;
                action.was_just_released = false;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse_released = true;

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
                    GraphicPanel._actions_just_released.put(action.name, 0);
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
    }

    public static boolean is_mouse_pressed() {
        return mouse_pressed;
    }

    public static boolean is_mouse_released() {
        return mouse_released;
    }

    public static boolean is_mouse_just_pressed() {
        return mouse_just_pressed;
    }

    public static boolean is_mouse_just_released() {
        return mouse_just_released;
    }

    public static void create_new_action(String name, int[] keyboard_codes, int[] mouse_codes, int[] mouse_wheel_codes){
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
                throw new Exception("Action not found!");
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

    public static void _action_is_no_more_just_pressed(String name){
        input_actions.get(name).is_just_pressed = false;
    }

    public static void _action_is_no_more_just_released(String name){
        input_actions.get(name).is_just_released = false;
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