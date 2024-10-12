package Engine.AbstractClasses;

import java.awt.Graphics2D;

public abstract class GraphicsItemAuto extends GraphicsItem{

    protected GraphicsItemAuto(){
        create();
    }

    public abstract void create();
    public abstract void update(double delta_time);
    public abstract void draw(Graphics2D g2);
    
}
