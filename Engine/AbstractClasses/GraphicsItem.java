package Engine.AbstractClasses;

import java.awt.Graphics2D;

import Engine.UI.GraphicsPanel;

public abstract class GraphicsItem {

    protected GraphicsItem(){
        GraphicsPanel.__add_graphic_item(this);
    }

    public abstract void update(double delta_time);
    public abstract void draw(Graphics2D g2);
}
