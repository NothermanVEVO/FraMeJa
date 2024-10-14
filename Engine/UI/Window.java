package Engine.UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Window extends JFrame {

    private static boolean first_time_init = true;
    private static GraphicsPanel g_panel;

    public Window(String name, int width, int height){
        if(first_time_init){
            setTitle(name);
            setLayout(new BorderLayout());

            g_panel = new GraphicsPanel(width, height);

            add(g_panel, BorderLayout.CENTER);
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
