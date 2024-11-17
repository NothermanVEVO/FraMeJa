package frameja.util;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Class to create Window that already support the {@link GraphicsPanel} and 
 * can support any other class from the package {@code javax.swing.*}.
 */
public class Window extends JFrame {

    private static Window instance;

    private static Input input;

    /**
     * Create an {@code instance} of Window.
     */
    private Window(String name){
        setTitle(name);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        input = Input.getInstance();
        addKeyListener(input);
    }

    /**
     * Create an {@code instance} of Window if it hasn't been instantiated before.
     * @param name {@code Title} of the window.
     * @return Instance of Window.
     */
    public static Window getInstance(String name){
        if(instance == null){
            instance = new Window(name);
        }
        return instance;
    }

    /**
     * Call that method every time you add a Component inside the window.
     * This method will resize the window to fit the Component and 
     * the window location will be set to the center of the monitor screen.
     */
    public void adjust(){
        pack();
        setLocationRelativeTo(null);
    }

}
