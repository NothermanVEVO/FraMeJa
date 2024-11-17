package frameja.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import frameja.variables.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to receive inputs from keyboard, mouse and mouse wheel.
 * Implemented to also detect inputs based in actions.
 * It was made for the {@link GraphicsPanel}, but still can be used in other classes that suport listener.
 * Implements the listeners {@link KeyListener}, {@link MouseInputListener} and {@link MouseWheelListener}.
 * @see Action
 */
public class Input implements KeyListener, MouseInputListener, MouseWheelListener{

    /*
     * Flags for mouse wheels input.
     */
    public static final int MOUSE_WHEEL_UP = -20;
    public static final int MOUSE_WHEEL_DOWN = -10;

    // Position of mouse.
    private static Vector2 mousePosition = new Vector2();

    // Position of mouse when mouse has been pressed.
    private static Vector2 mousePositionWhenPressed = new Vector2();

    // Position of mouse when mouse has been released.
    private static Vector2 mousePositionWhenReleased = new Vector2();

    // Flag to check if any key is being pressed.
    private static boolean anyKeyPressed = false;

    // Counter for keys pressed.
    private static int anyKeyCount = 0;

    // Flag to check if mouse is being pressed or not.
    private static boolean mousePressed = false;

    // Counter for mouse buttons pressed.
    private static int mouseCount = 0;

    // Flag to check if a key, mouse button or mouse wheel(moving up or down) is being pressed.
    private static boolean anyThingPressed = false;

    // Counter for any key, mouse button or mouse wheel(moving up or down) pressed.
    private static int anyThingCount = 0;

    // HashMap that keep all the created actions and use them to check if the action is occurring.
    private static HashMap<String, Action> inputActions = new HashMap<String, Action>();

    // Instance of Input.
    private static Input instance = new Input();

    /**
     *  Create an {@code instance} of Input.
    */
    private Input(){
    }

    /**
     * Method to return the {@code instance} of Input
     * @return Instance of Input
     */
    public static Input getInstance(){
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        anyKeyPressed = true;
        anyKeyCount++;

        anyThingPressed = true;
        anyThingCount++;

        for(Action action : inputActions.values()){
            for (int i = 0; i < action.keyboardCodes.length; i++) {
                if(action.keyboardCodes[i] == keyCode && !action.pressedKeys.contains(keyCode) && 
                    action.releasedKeys.contains(keyCode)){
                        action.pressedKeys.add(keyCode);
                        action.releasedKeys.remove((Object) keyCode);
                }
            }
            if(action.pressedKeys.size() > 0 && action.releasedKeys.size() < action.numberOfKeys){
                if(!action.wasJustPressed){
                    action.isJustPressed = true;
                    action.wasJustPressed = true;
                    GraphicsPanel.actionsJustPressed.put(action.name, 0);
                }
                action.isPressed = true;
                action.isReleased = false;
                action.wasJustReleased = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        anyKeyCount--;
        if(anyKeyCount == 0){
            anyKeyPressed = false;
        }

        anyThingCount--;
        if(anyThingCount == 0){
            anyThingPressed = false;
        }

        for(Action action : inputActions.values()){
            for (int i = 0; i < action.keyboardCodes.length; i++) {
                if(action.keyboardCodes[i] == keyCode && action.pressedKeys.contains(keyCode) && 
                    !action.releasedKeys.contains(keyCode)){
                        action.pressedKeys.remove((Object) keyCode);
                        action.releasedKeys.add(keyCode);
                }
            }
            if(action.releasedKeys.size() > 0 && action.pressedKeys.size() == 0){
                if(!action.wasJustReleased){
                    action.isJustReleased = true;
                    action.wasJustReleased = true;
                    GraphicsPanel.actionsJustReleased.put(action.name, 0);
                }
                action.isPressed = false;
                action.wasJustPressed = false;
                action.isReleased = true;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePositionWhenPressed.x = e.getX();
        mousePositionWhenPressed.y = e.getY();

        mousePressed = true;
        mouseCount++;

        anyThingPressed = true;
        anyThingCount++;

        int mouseCode = e.getButton();
        
        for(Action action : inputActions.values()){
            for (int i = 0; i < action.mouseCodes.length; i++) {
                if(action.mouseCodes[i] == mouseCode && !action.pressedKeys.contains(mouseCode) && 
                    action.releasedKeys.contains(mouseCode)){
                        action.pressedKeys.add(mouseCode);
                        action.releasedKeys.remove((Object) mouseCode);
                }
            }
            if(action.pressedKeys.size() > 0 && action.releasedKeys.size() < action.numberOfKeys){
                if(!action.wasJustPressed){
                    action.isJustPressed = true;
                    action.wasJustPressed = true;
                    GraphicsPanel.actionsJustPressed.put(action.name, 0);
                }
                action.isPressed = true;
                action.isReleased = false;
                action.wasJustReleased = false;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePositionWhenReleased.x = e.getX();
        mousePositionWhenReleased.y = e.getY();

        mouseCount--;
        if(mouseCount == 0){
            mousePressed = false;
        }

        anyThingCount--;
        if(anyThingCount == 0){
            anyThingPressed = false;
        }

        int mouseCode = e.getButton();

        for(Action action : inputActions.values()){
            for (int i = 0; i < action.mouseCodes.length; i++) {
                if(action.mouseCodes[i] == mouseCode && action.pressedKeys.contains(mouseCode) && 
                    !action.releasedKeys.contains(mouseCode)){
                        action.pressedKeys.remove((Object) mouseCode);
                        action.releasedKeys.add(mouseCode);
                }
            }
            if(action.releasedKeys.size() > 0 && action.pressedKeys.size() == 0){
                if(!action.wasJustReleased){
                    action.isJustReleased = true;
                    action.wasJustReleased = true;
                    GraphicsPanel.actionsJustReleased.put(action.name, 0);
                }
                action.isPressed = false;
                action.wasJustPressed = false;
                action.isReleased = true;
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
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int wheelCode = e.getWheelRotation();
        wheelCode = wheelCode == -1 ? MOUSE_WHEEL_UP : wheelCode == 1 ? MOUSE_WHEEL_DOWN : wheelCode;

        for(Action action : inputActions.values()){
            for (int i = 0; i < action.mouseWheelCodes.length; i++) {
                if(action.mouseWheelCodes[i] == wheelCode && !action.pressedKeys.contains(wheelCode) && 
                    action.releasedKeys.contains(wheelCode)){
                        action.pressedKeys.add(wheelCode);
                        action.releasedKeys.remove((Object) wheelCode);
                }
            }
            if(action.pressedKeys.size() > 0 && action.releasedKeys.size() < action.numberOfKeys){
                if(!action.wasJustPressed){
                    action.isJustPressed = true;
                    action.wasJustPressed = true;
                    GraphicsPanel.actionsJustPressed.put(action.name, 0);
                }
                if(wheelCode == MOUSE_WHEEL_UP){
                    GraphicsPanel.wheelMovedUp = 0;
                } else if(wheelCode == MOUSE_WHEEL_DOWN){
                    GraphicsPanel.wheelMovedDown = 0;
                }
                action.isPressed = true;
                action.isReleased = false;
                action.wasJustReleased = false;
            }
        }
    }

    /**
     * Method for when the mouse wheel isn't going up anymore.
     */
    protected static void mouseWheelUpReleased(){
        for(Action action : inputActions.values()){
            for (int i = 0; i < action.mouseWheelCodes.length; i++) {
                if(action.mouseWheelCodes[i] == MOUSE_WHEEL_UP && action.pressedKeys.contains(MOUSE_WHEEL_UP) && 
                    !action.releasedKeys.contains(MOUSE_WHEEL_UP)){
                        action.pressedKeys.remove((Object) MOUSE_WHEEL_UP);
                        action.releasedKeys.add(MOUSE_WHEEL_UP);
                }
            }
            if(action.releasedKeys.size() > 0 && action.pressedKeys.size() == 0){
                if(!action.wasJustReleased){
                    action.isJustReleased = true;
                    action.wasJustReleased = true;
                    GraphicsPanel.actionsJustReleased.put(action.name, 0);
                }
                action.isPressed = false;
                action.wasJustPressed = false;
                action.isReleased = true;
            }
        }
    }

    /**
     * Method for when the mouse wheel isn't going down anymore.
     */
    protected static void mouseWheelDownReleased(){
        for(Action action : inputActions.values()){
            for (int i = 0; i < action.mouseWheelCodes.length; i++) {
                if(action.mouseWheelCodes[i] == MOUSE_WHEEL_DOWN && action.pressedKeys.contains(MOUSE_WHEEL_DOWN) && 
                    !action.releasedKeys.contains(MOUSE_WHEEL_DOWN)){
                        action.pressedKeys.remove((Object) MOUSE_WHEEL_DOWN);
                        action.releasedKeys.add(MOUSE_WHEEL_DOWN);
                }
            }
            if(action.releasedKeys.size() > 0 && action.pressedKeys.size() == 0){
                if(!action.wasJustReleased){
                    action.isJustReleased = true;
                    action.wasJustReleased = true;
                    GraphicsPanel.actionsJustReleased.put(action.name, 0);
                }
                action.isPressed = false;
                action.wasJustPressed = false;
                action.isReleased = true;
            }
        }
    }

    /**
     * Create an {@link Action} for all the codes received.
     * @param name Name of the {@link Action}.
     * @param keyboardCodes Codes from keyboard. Ex: {@code new int[]}{{@link KeyEvent#VK_W}}
     * @param mouseCodes Codes from mouse. Ex: {@code new int[]}{{@link MouseEvent#BUTTON1}}
     * @param mouseWheelCodes Codes from mouse wheel. Ex: {@code new int[]}{{@code Input.MOUSE_WHEEL_UP}}
     */
    public static void createNewAction(String name, int[] keyboardCodes, int[] mouseCodes, int[] mouseWheelCodes){
        
        for(String str : inputActions.keySet()){
            if(name.equals(str)){
                try {
                    throw new Exception("Actions with name repeated! -> \"" + name + "\".");
                } catch (Exception e) {
                    Log.printExceptionError(e);
                    System.exit(-1);
                }
            }
        }

        String aName;
        int[] aKeyboardCodes;
        int[] aMouseCodes;
        int[] aMouseWheelCodes;
        if(name.isBlank()){
            try {
                throw new Exception("Action name cannot be blank!");
            } catch (Exception e) {
                Log.printExceptionError(e);
                System.exit(-1);
            }
            return;
        } else{
            aName = name;
        }
        
        if((keyboardCodes == null || keyboardCodes.length == 0) && (mouseCodes == null || mouseCodes.length == 0) && (mouseWheelCodes == null || mouseWheelCodes.length == 0)){
            try {
                throw new Exception("Action should have at least ONE key code!");
            } catch (Exception e) {
                Log.printExceptionError(e);
                System.exit(-1);
            }
            return;
        } else{
            aKeyboardCodes = keyboardCodes;
            aMouseCodes = mouseCodes;
            aMouseWheelCodes = mouseWheelCodes;
        }

        inputActions.put(aName, new Action(aName, aKeyboardCodes, aMouseCodes, aMouseWheelCodes));
    }

    /**
     * Checks if an {@link Action} exist.
     * @param name Name of the action to verify.
     * @return {@code true} if the action exists, 
     *         {@code false} if the actions don't exist.
     * @throws Exception The {@link Action} doesn't exist.
     */
    private static boolean actionExist(String name){
        if(!inputActions.containsKey(name)){
            try {
                throw new Exception("Action \"" + name + "\" not found!");
            } catch (Exception e) {
                Log.printExceptionWarning(e);
                return false;
            }
        }
        return true;
    }

    /**
     * Check if an {@link Action} is being pressed.
     * @param name Name of the action that should be verified.
     * @return {@code true} if the action is pressed, 
     *         {@code false} if the action isn't pressed or doesn't exist.
     * @throws Exception The {@link Action} doesn't exist.
     */
    public static boolean isActionPressed(String name){
        if(actionExist(name)){
            return inputActions.get(name).isPressed;
        }
        return false;
    }

    /**
     * Check if an {@link Action} just got pressed.
     * @param name Name of the action that should be verified.
     * @return {@code true} if the action just got pressed, 
     *         {@code false} if the action isn't just pressed or doesn't exist.
     * @throws Exception The {@link Action} doesn't exist.
     */
    public static boolean isActionJustPressed(String name){
        if(actionExist(name)){
            return inputActions.get(name).isJustPressed;
        }
        return false;
    }

    /**
     * Check if an {@link Action} is released.
     * @param name Name of the action that should be verified.
     * @return {@code true} if the action is released, 
     *         {@code false} if the action isn't released or doesn't exist.
     * @throws Exception The {@link Action} doesn't exist.
     */
    public static boolean isActionReleased(String name){
        if(actionExist(name)){
            return inputActions.get(name).isReleased;
        }
        return false;
    }

    /**
     * Check if an {@link Action} just got released.
     * @param name Name of the action that should be verified.
     * @return {@code true} if the action just got released, 
     *         {@code false} if the action isn't just released or doesn't exist.
     * @throws Exception The {@link Action} doesn't exist.
     */
    public static boolean isActionJustReleased(String name){
        if(actionExist(name)){
            return inputActions.get(name).isJustReleased;
        }
        return false;
    }

    /**
     * Check if any key from the keyboard is being pressed.
     * @return {@code true} if any key is pressed, 
     *         {@code false} if no key is pressed.
     */
    public static boolean isAnyKeyPressed() {
        return anyKeyPressed;
    }

    /**
     * Check if any mouse button is being pressed.
     * @return {@code true} if any button is pressed, 
     *         {@code false} if no button is pressed.
     */
    public static boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Check if all mouse buttons are released.
     * @return {@code true} if all buttons are released, 
     *         {@code false} if some button is being pressed.
     */
    public static boolean isMouseReleased() {
        return !mousePressed;
    }

    /**
     * Check if any key from keyboard or any mouse button is being pressed.
     * @return {@code true} if any key or mouse button is pressed, 
     *         {@code false} if all key and mouse buttons are released.
     */
    public static boolean isAnythingPressed() {
        return anyThingPressed;
    }

    /**
     * Method to get the position of the mouse inside {@link GraphicsPanel}.
     * @return Position of mouse inside {@link GraphicsPanel}
     */
    public static Vector2 getMousePosition() {
        return mousePosition;
    }

    /**
     * Method to get the position of the mouse when it was pressed inside {@link GraphicsPanel}.
     * @return Position of mouse when it was pressed inside {@link GraphicsPanel}
     */
    public static Vector2 getMousePositionWhenPressed() {
        return mousePositionWhenPressed;
    }

    /**
     * Method to get the position of the mouse when it was released inside {@link GraphicsPanel}.
     * @return Position of mouse when it was released inside {@link GraphicsPanel}
     */
    public static Vector2 getMousePositionWhenReleased() {
        return mousePositionWhenReleased;
    }

    /**
     * The {@link GraphicsPanel} check in the {@link Action} isn't just pressed anymore.
     * @param name Name of the {@link Action}
     */
    protected static void actionIsNoMoreJustPressed(String name){
        inputActions.get(name).isJustPressed = false;
    }

    /**
     * The {@link GraphicsPanel} check in the {@link Action} isn't just released anymore.
     * @param name Name of the {@link Action}
     */
    protected static void actionIsNoMoreJustReleased(String name){
        inputActions.get(name).isJustReleased = false;
    }

    /**
     * Get the value of the negative and positive action of an axis.
     * Ex: If the only negative action is pressed, it'll return {@code -1},
     * else if only the positive action is pressed, it shall return {@code 1},
     * else if neither or both negative and positive action are pressed, it would return {@code 0}.
     * @param negativeAction The name of the negative {@link Action}.
     * @param positiveAction The name of the positive {@link Action}.
     * @return Direction value of the negative action plus the positive action.
     */
    public static int getAxis(String negativeAction, String positiveAction){
        int value = 0;
        if(Input.isActionPressed(negativeAction)) value--;
        if(Input.isActionPressed(positiveAction)) value++;
        return value;
    }

    /**
     * 
     * @param negativeX Name of the negative {@link Action} from axis-x.
     * @param positiveX Name of the positive {@link Action} from axis-x.
     * @param negativeY Name of the negative {@link Action} from axis-y.
     * @param positiveY Name of the positive {@link Action} from axis-y.
     * @return An {@link Vector2}, {@code x} is the {@code getAxis(negativeX, positiveX)}
     * and {@code y} is the {@code getAxis(negativeY, positiveY)}.
     * @see Input#getAxis(String, String)
     */
    public static Vector2 getVector(String negativeX, String positiveX, String negativeY, String positiveY){
        return new Vector2(getAxis(negativeX, positiveX), getAxis(negativeY, positiveY));
    }

}

/**
 * Private class to store the input codes based in it's name.
 */
class Action{

    String name;
    int[] keyboardCodes;
    int[] mouseCodes;
    int[] mouseWheelCodes;

    int numberOfKeys;

    boolean isPressed = false;
    boolean isReleased = true;

    boolean isJustPressed = false;
    boolean isJustReleased = false;

    boolean wasJustPressed = false;
    boolean wasJustReleased = false;

    int numberOfPressedKeys = 0;
    int numberOfReleasedKeys = 0;

    ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
    ArrayList<Integer> releasedKeys = new ArrayList<Integer>();

    Action(String name, int[] keyboardCodes, int[] mouseCodes, int[] mouseWheelCodes){
        this.name = name;
        this.keyboardCodes = keyboardCodes == null ? new int[0] : keyboardCodes;
        this.mouseCodes = mouseCodes == null ? new int[0] : mouseCodes;
        this.mouseWheelCodes = mouseWheelCodes == null ? new int[0] : mouseWheelCodes;
        for(int i : this.keyboardCodes){
            releasedKeys.add(i);
        }
        for(int i : this.mouseCodes){
            releasedKeys.add(i);
        }
        for(int i : this.mouseWheelCodes){
            releasedKeys.add(i);
        }
        numberOfKeys = this.keyboardCodes.length + this.mouseCodes.length + this.mouseWheelCodes.length;
    }

}