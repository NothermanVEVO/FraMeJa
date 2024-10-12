package Engine.UI;

import javax.swing.JFrame;

import Engine.GlobalVariables.Input;

public class Window extends JFrame {

    private static Input input;

    private static boolean first_time_init = true;

    public Window(String name, int width, int height){
        if(first_time_init){
            setTitle(name);
            setFocusable(true);
            setEnabled(true);
            setSize(width, height);
            setLayout(null);

            input = new Input();

            addKeyListener(input);
            addMouseListener(input);
            addMouseMotionListener(input);
            addMouseWheelListener(input);
            
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
            first_time_init = false;
        } else{
            try {
                throw new Exception("Object \"Window\" can't be created more than 1 time!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

}
