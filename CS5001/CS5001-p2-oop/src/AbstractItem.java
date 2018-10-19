/**
 * The AbstractItem class contains abstract methods to capture the
 * required functionality for the implementation of item classes
 */
abstract class AbstractItem {

    protected AbstractGrid grid;
    protected int xCoordinate;
    protected int yCoordinate;

    /**
     * Process an item.
     * For a farmer this means (potentially, depending on the time-step) producing products,
     * for a transporter this means carrying products between a farmer and a consumer,
     * for a consumer this means consuming.
     * @param timeStep The current time-step
     */
    public abstract void process(TimeStep timeStep);

    /**
     * Retrieve the stock that is stored at this item's location. This is in nutrition units.
     * Can be queried via the grid field of this class.
     *
     * @return The stock
     */
    abstract protected int getStock();

    /**
     * Add to the stock of this item's location.
     * Via the grid field of this class.
     *
     * @param nutrition The amount of nutrition to add
     */
    abstract protected void addToStock(int nutrition);

    /**
     * Reduce the stock of this item's location.
     * Via the grid field of this class.
     *
     * @param nutrition The amount of nutrition to subtract
     */
    abstract protected void reduceStock(int nutrition);

}
