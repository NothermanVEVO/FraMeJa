package engine.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JPanel;

import org.reflections.Reflections;

import engine.abstractclasses.GraphicsItem;
import engine.abstractclasses.GraphicsItemAuto;

/**
 * Class that updates and redraw every frame per second.
 * Extends {@link JPanel} and implements {@link Runnable}.
 * @see GraphicsItem
 * @see GraphicsItemAuto
 */
public class GraphicsPanel extends JPanel implements Runnable {

    // Thread that will process the updates and draws.
    private static Thread engineThread;

    // Frames per second.
    private static int fps = 60;

    // The time between the current frame and the last frame.
    private static double deltaTime;

    // Width of the Panel.
    private static int width;

    // Height of the Panel.
    private static int height;

    // Receive the input from the user.
    private static Input input;

    // Store all the GraphicsItem instantiated and that hasn't used the free().
    private static ArrayList<GraphicsItem> graphicsItemList = new ArrayList<GraphicsItem>();

    // Store all the actions, based on it's name, that has been just pressed.
    protected static HashMap<String, Integer> actionsJustPressed = new HashMap<String, Integer>();

    // Store all the actions, based on it's name, that has been just released.
    protected static HashMap<String, Integer> actionsJustReleased = new HashMap<String, Integer>();

    // Store all the timers.
    protected static ArrayList<Timer> timers = new ArrayList<Timer>();

    // Created to check if the wheel still moving up.
    protected static int wheelMovedUp = -1;

    // Created to check if the wheel still moving down.
    protected static int wheelMovedDown = -1;

    // Counts the frames per seconds.
    private static int frameCount = 0;

    // The sum of all the deltaTimes in one second.
    private static double pseudoFps = 0;

    // The value of the pseudoFps after one second.
    private static double currentFps = 0;

    // Flag to check if the Thread should be processed.
    private static boolean paused = false;

    // Flag to check if the Thread should stop.
    private static boolean stop = false;

    // Flag to check if the anti-aliasing should be used in the draw method.
    private static boolean antiAliasingEnabled = false;

    // Relative path of the package that has all the classes that inherits GraphicsItemAuto.
    private static String packageOfAutoInstantiatedGraphics;

    // Instance of GraphicsPanel.
    private static GraphicsPanel instance;

    /**
     * Create an {@code instance} of GraphicsPanel.
     * @param width The {@code width} of the {@link JPanel}.
     * @param height The {@code height} of the {@link JPanel}.
     * @param antiAliasingEnabled if the anti-aliasing should be used in the draw method.
     * @param packageOfAutoInstantiatedGraphics Relative path of the package that has all the classes 
     * that inherits GraphicsItemAuto.
     */
    private GraphicsPanel(int width, int height, boolean antiAliasingEnabled, String packageOfAutoInstantiatedGraphics){
        GraphicsPanel.width = width;
        GraphicsPanel.height = height;
        GraphicsPanel.antiAliasingEnabled = antiAliasingEnabled;
        GraphicsPanel.packageOfAutoInstantiatedGraphics = packageOfAutoInstantiatedGraphics;

        // Adjust the JPanel
        setOpaque(false);
        setBounds(0, 0, width, height);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setEnabled(true);
        setLayout(null);

        input = Input.getInstance();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addMouseWheelListener(input);
    }

    /**
     * Create an {@code instace} of the GraphicsPanel if it hasn't been instantiated before.
     * @param width The width of the {@link JPanel}.
     * @param height The height of the {@link JPanel}.
     * @param antiAliasingEnabled if the anti-aliasing should be used in the draw method.
     * @param packageOfAutoInstantiatedGraphics Relative path of the package that has all the classes 
     * that inherits GraphicsItemAuto.
     * @return Instace of GraphicsPanel.
     */
    public static GraphicsPanel getInstance(int width, int height, boolean antiAliasingEnabled, 
        String packageOfAutoInstantiatedGraphics){
        if(instance == null){
            instance = new GraphicsPanel(width, height, antiAliasingEnabled, packageOfAutoInstantiatedGraphics);
        }
        return instance;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        Log.println(Log.SUCCESS, "FraMeJa initialized without errors!");

        // Auto instantiate all classes that inherits the class "GraphicsItemAuto".
        autoLoadGraphicsItems(packageOfAutoInstantiatedGraphics);

        while(!stop){
            if(paused){
                return;
            }
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                deltaTime = delta / fps;
                update(deltaTime);
                repaint();
                delta--;
                countFps();
            }
        }
    }

    /**
     * Counter and set the current frames per second.
     */
    private void countFps(){
        frameCount++;
        pseudoFps += deltaTime;
        if(frameCount >= fps){
            currentFps = fps / pseudoFps;
            frameCount = 0;
            pseudoFps = 0;
        }
    }

    /**
     * Method that is called every frame and process all that {@link GraphicsItem} and {@link GraphicsItemAuto}.
     * @param deltaTime The variance between the current frame and the last frame.
     */
    private void update(double deltaTime){
        for (Timer timer : timers) {
            if(!timer.stop){
                timer.update(deltaTime);
            }
        }

        // If it's zero, then the action has been just pressed, so I increment one for the next if.
        // If isn't zero, then the action was just pressed, then it shouldn't be said as just pressed.
        for(String str : actionsJustPressed.keySet()){
            if(actionsJustPressed.get(str) == 0){
                actionsJustPressed.replace(str, 1);
            } else {
                Input.actionIsNoMoreJustPressed(str);
            }
        }

        // If it's zero, then the action has been just released, so I increment one for the next if.
        // If isn't zero, then the action was just released, then it shouldn't be said as just released.
        for(String str : actionsJustReleased.keySet()){
            if(actionsJustReleased.get(str) == 0){
                actionsJustReleased.replace(str, 1);
            } else {
                Input.actionIsNoMoreJustReleased(str);
            }
        }

        if(wheelMovedUp == 0){
            wheelMovedUp = 1;
        } else if(wheelMovedUp == 1){
            Input.mouseWheelUpReleased();
            wheelMovedUp = -1;
        }

        if(wheelMovedDown == 0){
            wheelMovedDown = 1;
        } else if(wheelMovedDown == 1){
            Input.mouseWheelDownReleased();
            wheelMovedDown = -1;
        }

        // Update every class that inherits GraphicsItem
        for (GraphicsItem graphics : graphicsItemList) {
            if(graphics.isUpdateEnabled()){
                graphics.update(deltaTime);
            }
        }
    }

    /**
     * It's called every frame and redraw every {@link GraphicsItem} and {@link GraphicsItemAuto}.
     * @param g Draw in the JPanel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(antiAliasingEnabled){
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //// Draw every class that inherits GraphicsItem
        for (GraphicsItem graphics : graphicsItemList) {
            if (graphics.isDrawEnabled()) {
                graphics.draw(g2);
            }
        }

        g2.dispose();
    }

    /**
     * Get the {@code delta time}.
     * @return The variance between the current frame and the last frame.
     */
    public static double getDeltaTime() {
        return deltaTime;
    }

    /**
     * Get the quantity of frames that should occur every second.
     * @return Frames per second.
     */
    public static int getFps() {
        return fps;
    }

    /**
     * Set the quantity of frames that should occur every second.
     * @param fps Frames per second.
     */
    public static void setFps(int fps) {
        GraphicsPanel.fps = fps;
    }

    /**
     * Get the quantity of frames that occured in one second.
     * @return Current quantity of frames per second.
     */
    public static double getCurrentFps(){
        return currentFps;
    }

    /**
     * Get the width.
     * @return Width.
     * @see JPanel
     */
    public static int getPanelWidth(){
        return GraphicsPanel.width;
    }

    /**
     * Get the height.
     * @return Height.
     * @see JPanel
     */
    public static int getPanelHeight(){
        return GraphicsPanel.height;
    }

    /**
     * Add a {@link GraphicsItem} at the correct position in the {@link GraphicsPanel}.
     * <p>The correct position is based in the {@code z} of the {@code gItem}.<p>
     * @param gItem An object that inherits {@link GraphicsItem}.
     * @throws Exception the {@code gItem} already exists inside the {@link GraphicsPanel}.
     */
    public static void addGraphicItem(GraphicsItem gItem){
        if(!graphicsItemList.contains(gItem)){
            addAtCorrectPosition(gItem);
        } else{
            try {
                throw new Exception("The GraphicsItem: \"" + gItem + "\" is already " + 
                    "inside the GraphicsPanel.");
            } catch (Exception e) {
                Log.printExceptionWarning(e);
            }
        }
    }

    /**
     * Remove the {@link GraphicsItem} from the {@link GraphicsPanel}.
     * @param gItem An object that inherits {@link GraphicsItem}.
     * @throws Exception the {@code gItem} doesn't exists inside the {@link GraphicsPanel}.
     */
    public static void removeGraphicItem(GraphicsItem gItem){
        if(graphicsItemList.contains(gItem)){
            graphicsItemList.remove(gItem);
        } else{
            try {
                throw new Exception("The GraphicsItem: \"" + gItem + "\" isn't inside the GraphicsPanel.");
            } catch (Exception e) {
                Log.printExceptionWarning(e);
            }
        }
    }

    /**
     * Add a {@link Timer} inside the {@code Thread} of {@link GraphicsPanel}.
     * @param timer An object that inherits {@link Timer}
     * @throws Exception the {@code timer} already exists inside the {@link GraphicsPanel}.
     */
    protected static void addTimer(Timer timer){
        if(!timers.contains(timer)){
            timers.add(timer);
        } else{
            try {
                throw new Exception("The Timer: \"" + timer + "\" is already inside the timers.");
            } catch (Exception e) {
                Log.printExceptionWarning(e);
            }
        }
    }

    /**
     * Remove a {@link Timer} from the {@code Thread} of {@link GraphicsPanel}.
     * @param timer An object that inherits {@link Timer}
     * @throws Exception the {@code timer} doens't exists inside the {@link GraphicsPanel}.
     */
    protected static void removerTimer(Timer timer){
        if(timers.contains(timer)){
            timers.remove(timer);
        } else{
            try {
                throw new Exception("The Timer: \"" + timer + "\" isn't inside the timers.");
            } catch (Exception e) {
                Log.printExceptionWarning(e);
            }
        }
    }

    /**
     * Check if a {@link GraphicsItem} exists inside the {@link GraphicsPanel}.
     * @param gItem An object that inherits {@link GraphicsItem}.
     * @return {@code true} if the {@code gItem} exists inside the {@link GraphicsPanel}, else {@code false}.
     */
    public static boolean containsGraphicItem(GraphicsItem gItem){
        return graphicsItemList.contains(gItem);
    }

    /**
     * Add a {@link GraphicsItem} in the {@link ArrayList} based in the {@code z} of the {@code gItem},
     * it uses {@code binary search} to find the correct position for the {@code gItem}
     * @param gItem An object that inherits {@link GraphicsItem}.
     */
    private static void addAtCorrectPosition(GraphicsItem gItem){
        int left = 0;
        int right = graphicsItemList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (graphicsItemList.get(mid).getZ() == gItem.getZ()) {
                left = mid;
                break;
            }

            if (graphicsItemList.get(mid).getZ() < gItem.getZ()) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        graphicsItemList.add(left, gItem);
    }

    /**
     * Automatically will instantiate all the classes that inherits {@link GraphicsItemAuto}.
     * @param nameOfPackage Relative path for the package that contains the all the {@code GraphicsItemAuto}
     */
    private void autoLoadGraphicsItems(String nameOfPackage){
        if(nameOfPackage == null || nameOfPackage.isBlank()){
            Log.print(Log.WARNING, "There's no package defined for the \"GraphicsItemAuto\"" + 
                " inherited classes.");
            return;
        }
        Log.print(Log.WARNING, "");
        Reflections reflections = new Reflections("src");
        Set<Class<? extends GraphicsItemAuto>> subClasses = reflections.getSubTypesOf(GraphicsItemAuto.class);
        for (Class<? extends GraphicsItemAuto> subClass : subClasses) {
            try {
                subClass.getDeclaredConstructor().newInstance();
                Log.println(Log.SUCCESS, "Instance of: \"" + subClass.getSimpleName() + "\" created.");
            } catch (Exception e) {
                Log.printExceptionError(e);
            }
        }
    }

    /**
     * Create the engine {@code Thread} if not created yet and start.
     */
    public void start(){
        if(engineThread == null){
            engineThread = new Thread(this);
        }
        try {
            engineThread.start();
        } catch (IllegalThreadStateException e) {
            Log.printExceptionWarning(e);
        }
    }

    /**
     * The {@code Engine Thread} will make a last loop and then finish.
     * <h3>Note:</h3>
     * The {@code Thread} can't be started again.
     */
    public static void stop(){
        stop = true;
    }

    /**
     * Check if the {@code anti-aliasing} is enabled.
     * @return {@code true} if anti-aliasing is enabled, else {@code false}.
     */
    public static boolean isAntiAliasingEnabled() {
        return antiAliasingEnabled;
    }

    /**
     * Enable os disable anti-aliasing.
     * @param antiAliasingEnabled {@code true} enables anti-aliasing, {@code false} disables.
     */
    public static void setAntiAliasingEnabled(boolean antiAliasingEnabled) {
        GraphicsPanel.antiAliasingEnabled = antiAliasingEnabled;
    }

    /**
     * Check if the process is paused or not.
     * @return {@code true} if the process of the GraphicsPanel is disabled
     */
    public static boolean isPaused() {
        return paused;
    }

    /**
     * Pause or resume the process of the {@link GraphicsPanel}.
     * @param paused {@code true} pause the process, {@code false} resume the process.
     */
    public static void setPaused(boolean paused) {
        GraphicsPanel.paused = paused;
    }

}