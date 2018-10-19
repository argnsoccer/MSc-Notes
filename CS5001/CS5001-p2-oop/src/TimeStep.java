/**
 * A class to represent time-steps in the game.
 */
class TimeStep {

    /**
     * Number of ticks (like seconds etc) since the start of the game
     */
    private int ticksFromStart;

    /**
     * We start counting from 1
     */
    public TimeStep() {
        ticksFromStart = 1;
    }

    /**
     * A way to increment the time-step
     */
    public void increment() {
        ticksFromStart++;
    }

    /**
     * A way to retrive the current time-step
     * @return The current time-step
     */
    public int getValue() {
        return  ticksFromStart;
    }
}
