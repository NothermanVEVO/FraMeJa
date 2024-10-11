package Engine;

import javax.swing.JFrame;

public class Window extends JFrame {

    GraphicPanel g_panel;
    Thread engine_thread;

    Input input = new Input();

    public Window(String name, int width, int height){
        super(name);
        setFocusable(true);
        setEnabled(true);
        setSize(width, height);
        setLayout(null);

        g_panel = new GraphicPanel(width, height);
        add(g_panel);

        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addMouseWheelListener(input);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        engine_thread = new Thread(g_panel);
        engine_thread.start();
    }

}
