package Engine.UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Window extends JFrame {

    private static boolean first_time_init = true;

    public Window(String name, int width, int height){
        if(first_time_init){
            setTitle(name);
            setLayout(new BorderLayout());

            add(new GraphicsPanel(800, 600), BorderLayout.CENTER);
            pack();
            
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
