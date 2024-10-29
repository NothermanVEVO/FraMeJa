package engine.util;

import java.util.ArrayList;

import engine.listener.timer.TimerListener;

/**
 * Class that will count {@code x} seconds, after that time, an {@code Event} (timeout) will occur.
 */
public class Timer {

    // Flag to check if the timer is stopped.
    public boolean stop = true;

    // Flag to check if the timer should stop after the timeout.
    public boolean oneTime = false;

    // The time, in seconds, that an event will occur.
    public double waitTime = 0;

    // The time, in seconds, that's left for an event to occur.
    private double timeLeft = 0;

    // Store all the listeners.
    private ArrayList<TimerListener> listeners = new ArrayList<TimerListener>();

    /**
     * Create an instance of Timer.
     * Automatically will add a {@code timer} inside {@link GraphicsPanel}.
     */
    public Timer(){
        GraphicsPanel.addTimer(this);
    }

    /**
     * Start the timer.
     * <p>The update() will be called every frame inside {@link GraphicsPanel}.<p>
     * @param waitTime The time, in seconds, that should be waited, once the time passes, 
     * calls for the {@code timeout()}.
     */
    public void start(double waitTime){
        this.waitTime = waitTime;
        this.timeLeft = waitTime;
        this.stop = false;
    }

    /**
     * The update won't be processed until the {@code resume()} is called.
     */
    public void stop(){
        stop = true;
    }

    /**
     * The update will be processed again.
     */
    public void resume(){
        stop = false;
    }

    /**
     * The time left will be reseted to the original value ({@code waitTime}).
     */
    public void reset(){
        timeLeft = waitTime;
    }

    /**
     * Called every frame and will count until time left is less or equal to 0.
     * @param deltaTime The variance between the last frame and the current frame.
     */
    protected void update(double deltaTime){
        timeLeft -= deltaTime;
        if(timeLeft <= 0){
            emitEvent();
            if(oneTime){
                stop = true;
                return;
            }
            timeLeft += waitTime;
        }
    }

    /**
     * Get the time left, in seconds.
     * @return Time left, in seconds.
     */
    public double getTimeLeft() {
        return timeLeft;
    }

    /**
     * Add a {@code listener} that will receive the event when time left is less or equal to 0.
     * @param l The {@link TimerListener}.
     */
    public void addListeners(TimerListener l) {
        listeners.add(l);
    }

    /**
     * Remove a {@code listener}, it'll not receive the event anymore..
     * @param l The {@link TimerListener}.
     */
    public void removeListener(TimerListener l) {
        listeners.remove(l);
    }

    /**
     * Method that will emit the event for all the {@link TimerListener}'s.
     */
    private void emitEvent(){
        for(TimerListener listener : listeners){
            listener.timeout();
        }
    }

    /**
     * Remove {@code this} from the timers inside {@link GraphicsPanel}.
     * If there's another variable referencing {@code this}, it'll only be removed from the 
     * {@link GraphicsPanel} but not freed in the memory.
     */
    public void free(){
        GraphicsPanel.removerTimer(this);
    }

}
