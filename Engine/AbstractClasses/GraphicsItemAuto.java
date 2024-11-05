package engine.abstractclasses;

import java.awt.Graphics2D;

import engine.util.GraphicsPanel;

/**
 * Inherits {@link GraphicsItem}, it's automatically instantiated and has a create method to 
 * replace the {@code Constructor}.
 */
public abstract class GraphicsItemAuto extends GraphicsItem{

    /**
     * Create a instance of {@code GraphicsItemAuto}.
     * <h3> Note: </h3>
     * In this class you shouldn't manually instantiate, it's automatically instantiated when 
     * the {@code Thread} of {@link GraphicsPanel} starts.
     * @see org.reflections.Reflections
     */
    protected GraphicsItemAuto(){
        GraphicsPanel.addGraphicItem(this);
        create();
    }

    /**
     * Called just once when it's instantiated.
     */
    public abstract void create();
    public abstract void update(double deltaTime);
    public abstract void draw(Graphics2D g2);
}
