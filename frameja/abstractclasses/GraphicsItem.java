package frameja.abstractclasses;

import java.awt.Graphics2D;
import java.util.ArrayList;

import frameja.util.GraphicsPanel;
import frameja.util.Log;
import frameja.variables.Vector2;
import frameja.variables.collisions.Circle2;
import frameja.variables.collisions.Line2;
import frameja.variables.collisions.Rect2;

/**
 * Class to process and draw (based in the {@code z}) in the {@link GraphicsPanel}.
 */
public abstract class GraphicsItem {

    // The position of this.
    public Vector2 position = new Vector2();

    /*
     * The position of the draw.
     * If z = 0, it'll be drawn in front of all the other GraphicsItem that has a lower z;
     */
    private int z = 0;

    // Flag for the GraphicsPanel to know if the update() should be called.
    private boolean updateEnabled = true;

    // Flag for the GraphicsPanel to know if the draw() should be called.
    private boolean drawEnabled = true;

    // An list of all childs.
    private ArrayList<GraphicsItem> children = new ArrayList<>();

    // The parent of this.
    protected GraphicsItem parent;

    /**
     * It's called every frame, if the update is enabled, and process all the code inside.
     * @param deltaTime The variance between the current frame and the last frame.
     */
    public abstract void update(double deltaTime);

    /**
     * It's called every frame, if the draw is enabled, and redraw the {@link GraphicsPanel}.
     * @param g2 Draw in the {@link GraphicsPanel}.
     */
    public abstract void draw(Graphics2D g2);

    /**
     * Return an {@link ArrayList} containing all the childs of this {@link GraphicsItem}.
     * @return {@link ArrayList} that contains all the childs of this {@link GraphicsItem}.
     */
    public ArrayList<GraphicsItem> getChildren(){
        return children;
    }

    /**
     * Add the child inside this {@code GraphicsItem}.
     * @param child The child of {@code this}.
     * @throws Exception the child already has a parent.
     */
    public void addChild(GraphicsItem child){
        if(child.parent != null){
            try {
                throw new Exception("The child already has a parent!");
            } catch (Exception e) {
                Log.printExceptionWarning(e);
                return;
            }
        }
        children.add(child);
        child.parent = this;
        if(GraphicsPanel.containsGraphicItem(this) && !GraphicsPanel.containsGraphicItem(child)){
            GraphicsPanel.addGraphicItem(child);
        }
    }

    /**
     * Remove the child of this {@code GraphicsItem}.
     * @param child The child of {@code this}.
     * @throws Exception {@code this} doesn't have that {@code child}.
     */
    public void removeChild(GraphicsItem child){
        if(!children.contains(child)){
            try {
                throw new Exception("This GraphicsItem doesn't contain that child!");
            } catch (Exception e) {
                Log.printExceptionWarning(e);
                return;
            }
        }
        children.remove(child);
        child.parent = null;
        child.free();
    }

    /**
     * Remove the child of this {@code GraphicsItem} at the {@code index}.
     * @param index The position of the child inside the {@code children} array.
     */
    public void removeChild(int index){
        children.remove(index);
    }

    /**
     * Returns the parent of this {@code GraphicsItem}
     * @return {@code parent}
     */
    public GraphicsItem getParent(){
        return parent;
    }

    /**
     * Remove {@code this} from the {@link GraphicsPanel} and all of it's children.
     * If there's another variable referencing {@code this}, it'll only be removed from {@link GraphicsPanel} but not freed
     * in the memory.
     */
    public void free(){
        if(parent != null){
            parent.removeChild(this);
        } else{
            for (int i = 0; i < children.size();) {
                removeChild(children.get(i));
            }
            GraphicsPanel.removeGraphicItem(this);
        }
    }

    /**
     * Get the {@code z} value.
     * @return The position in the axis-z of the draw.
     */
    public int getZ() {
        return z;
    }

    /**
     * Set the {@code z} value.
     * <h3> Note: </h3>
     * If {@code this} is inside {@link GraphicsPanel}, the {@link GraphicsPanel} will sort the 
     * {@code List} that store all the objects that inherits {@link GraphicsItem} based in the {@code z} value.
     * @param z The position in the axis-z of the draw.
     */
    public void setZ(int z) {
        this.z = z;
        if(GraphicsPanel.containsGraphicItem(this)){
            GraphicsItem temp = this;
            GraphicsPanel.removeGraphicItem(this);
            GraphicsPanel.addGraphicItem(temp);
        }
    }

    /**
     * Check if the update is enabled
     * @return {@code true} if update is enabled, else {@code false}.
     */
    public boolean isUpdateEnabled() {
        return updateEnabled;
    }

    /**
     * Set {@code boolean} if the update should still be processed.
     * @param updateEnabled Update enabled.
     */
    public void setUpdateEnabled(boolean updateEnabled) {
        this.updateEnabled = updateEnabled;
    }

    /**
     * Check if the draw is enabled
     * @return {@code true} if draw is enabled, else {@code false}.
     */
    public boolean isDrawEnabled() {
        return drawEnabled;
    }

    /**
     * Set {@code boolean} if the draw should still be redrawn.
     * @param drawEnabled Draw enabled.
     */
    public void setDrawEnabled(boolean drawEnabled) {
        this.drawEnabled = drawEnabled;
    }

    /**
     * Draw a pixel.
     * @param vct The position.
     * @param g2 The render.
     */
    public void drawPixel(Vector2 vct, Graphics2D g2){
        g2.fillRect((int) vct.x, (int) vct.y, 1, 1);
    }

    /**
     * Draw a line.
     * @param line The {@link Line2}
     * @param g2 The render.
     */
    public void drawLine(Line2 line, Graphics2D g2){
        g2.drawLine((int) line.start.x, (int) line.start.y, (int) line.end.x, (int) line.end.y);
    }

    /**
     * Draw a rectangle.
     * @param rect The {@link Rect2}
     * @param g2 The render.
     */
    public void drawRect(Rect2 rect, Graphics2D g2){
        g2.drawRect((int) rect.position.x, (int) rect.position.y, (int) rect.size.x, (int) rect.size.y);
    }

    /**
     * Draw a circle.
     * @param circle The {@link Circle2}.
     * @param g2 The render.
     */
    public void drawCircle(Circle2 circle, Graphics2D g2){
        g2.drawOval((int) (circle.position.x - circle.radius), 
                    (int) (circle.position.y - circle.radius), 
                    (int) circle.radius * 2, (int) circle.radius * 2);
    }

    /**
     * Fill a rectangle.
     * @param rect The {@link Rect2}
     * @param g2 The render.
     */
    public void fillRect(Rect2 rect, Graphics2D g2){
        g2.fillRect((int) rect.position.x, (int) rect.position.y, (int) rect.size.x, (int) rect.size.y);
    }

    /**
     * Fill a circle.
     * @param circle The {@link Circle2}.
     * @param g2 The render.
     */
    public void fillCircle(Circle2 circle, Graphics2D g2){
        g2.fillOval((int) (circle.position.x - circle.radius), 
                    (int) (circle.position.y - circle.radius), 
                    (int) circle.radius * 2, (int) circle.radius * 2);
    }

}
