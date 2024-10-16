package engine.listener.timer;

import java.util.EventListener;

/**
 * Interface for the class {@link Timer}.
 */
public interface TimerListener extends EventListener{

    /**
     * Method that is called when the {@code timeLeft} of {@link Timer} reachs 0 second.
     */
    void timeout();

}
