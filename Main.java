import java.util.Set;

import org.reflections.Reflections;

import Engine.GraphicsItem;
import Engine.Input;
import Engine.Window;

public class Main{

    public static void main(String[] args) {

        read_graphics_items();

        int[] keyboard_accept = {10, 32};
        int[] mouse_accept = {1};

        Input.create_new_action("Accept", keyboard_accept, mouse_accept, new int[0]);

        new Window("Framework do Paraguai", 800, 600);

    }

    private static void read_graphics_items(){
        Reflections reflections = new Reflections("src");
        Set<Class<? extends GraphicsItem>> subClasses = reflections.getSubTypesOf(GraphicsItem.class);
        for (Class<? extends GraphicsItem> subClass : subClasses) {
            try {
                subClass.getDeclaredConstructor().newInstance();
                System.out.println("Instancia de: " + subClass.getSimpleName() + " criada.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}