package Engine;

import java.awt.Graphics2D;

public abstract class GraphicsItem {

    public GraphicsItem(){
        GraphicsPanel.__add_graphic_item(this);
        create();
    }

    public abstract void create();
    public abstract void update(double delta_time);
    public abstract void draw(Graphics2D g2);
}
