import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import Engine.GlobalVariables.Input;
import Engine.UI.GraphicsPanel;
import Engine.UI.Window;

public class Main{

    public static void main(String[] args) {

        Input.create_new_action("Move Up", new int[]{KeyEvent.VK_W, KeyEvent.VK_UP}, new int[0], new int[]{Input.MOUSE_WHEEL_UP});
        Input.create_new_action("Move Down", new int[]{KeyEvent.VK_S, KeyEvent.VK_DOWN}, new int[0], new int[]{Input.MOUSE_WHEEL_DOWN});
        Input.create_new_action("Move Left", new int[]{KeyEvent.VK_A, KeyEvent.VK_LEFT}, new int[]{MouseEvent.BUTTON1}, new int[0]);
        Input.create_new_action("Move Right", new int[]{KeyEvent.VK_D, KeyEvent.VK_RIGHT}, new int[]{MouseEvent.BUTTON3}, new int[0]);
        
        new Window("Framework do Paraguai", 800, 600).add(new GraphicsPanel(800, 600));

    }

}