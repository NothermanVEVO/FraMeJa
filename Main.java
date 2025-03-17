import java.awt.BorderLayout;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import frameja.util.GraphicsPanel;
import frameja.util.Input;
import frameja.util.Window;

public class Main{

    static Window window;
    static GraphicsPanel gPanel;

    public static void main(String[] args) {

        Input.createNewAction("Move Up", new int[]{KeyEvent.VK_W, KeyEvent.VK_UP}, new int[0], new int[]{Input.MOUSE_WHEEL_UP});
        Input.createNewAction("Move Down", new int[]{KeyEvent.VK_S, KeyEvent.VK_DOWN}, new int[0], new int[]{Input.MOUSE_WHEEL_DOWN});
        Input.createNewAction("Move Left", new int[]{KeyEvent.VK_A, KeyEvent.VK_LEFT}, new int[]{MouseEvent.BUTTON1}, new int[0]);
        Input.createNewAction("Move Right", new int[]{KeyEvent.VK_D, KeyEvent.VK_RIGHT}, new int[]{MouseEvent.BUTTON3}, new int[0]);

        Input.createNewAction("Mouse 1", null, new int[]{MouseEvent.BUTTON1}, null);

        Input.createNewAction("Create", new int[]{KeyEvent.VK_ENTER}, null, null);
        Input.createNewAction("Destroy", new int[]{KeyEvent.VK_BACK_SPACE}, null, null);
        Input.createNewAction("Destroy All", new int[]{KeyEvent.VK_DELETE}, null, null);

        Input.createNewAction("null", new int[]{KeyEvent.VK_SPACE}, null, null);

        window = Window.getInstance("Framework do Paraguai");

        gPanel = GraphicsPanel.getInstance(800, 600, true, "src");

        window.add(gPanel, BorderLayout.CENTER);
        window.adjust();
        window.setVisible(true);

        gPanel.start();

        //TODO COMMENT LINE CLASS, AND ABOUT ALL THE GEOM CLASSES
        //TODO FINISH COMMENTING VECTOR2

        //TODO PAUSE INPUT

        //TODO RECOMMENT CLASS "INPUT"

    }

}