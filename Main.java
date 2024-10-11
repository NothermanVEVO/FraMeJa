import Engine.Input;
import Engine.Window;

public class Main{

    public static void main(String[] args) {

        int[] keyboard_accept = {10, 32};
        int[] mouse_accept = {1};

        Input.create_new_action("Accept", keyboard_accept, mouse_accept, new int[0]);

        new Window("Framework do Paraguai", 800, 600);
    }

}