/**
 * The AbstractGrid class contains abstract methods to capture the required functionality for a Grid implementation.
 */
abstract class AbstractGrid {

    protected AbstractItem[][] grid;
    protected int[][] stock;

    /**
     * Returns the width of the grid.
     *
     * @return The width of the grid
     */
    abstract public int getWidth();

    /**
     * Returns the height of the grid.
     *
     * @return The height of the grid
     */
    abstract public int getHeight();

    /**
     * Registers an item on the grid at the specified location.
     * Caution: this will overwrite whatever was at this location before!
     *
     * @param xCoordinate
     * @param yCoordinate
     * @param item
     */
    abstract public void registerItem(int xCoordinate, int yCoordinate, AbstractItem item);

    /**
     * Return the item at row i and column j
     * Returns null if there isn't an item at the specified location
     * Also returns null for out-of-bounds queries
     *
     * @param xCoordinate the row number
     * @param yCoordinate the column number
     * @return The item that is stored at the specified grid cell, null otherwise
     */
    abstract public AbstractItem getItem(int xCoordinate, int yCoordinate);

    /**
     * The stock (in terms of units of nutrition at the specified location.
     *
     * @param xCoordinate the row number
     * @param yCoordinate the column number
     * @return The stock at the specified location
     */
    abstract public int getStockAt(int xCoordinate, int yCoordinate);

    /**
     * Clear the stock at the specified location. i.e. set it to 0.
     * This is equivalent to setStockAt(x,y,0)
     *
     * @param xCoordinate the row number
     * @param yCoordinate the column number
     */
    abstract public void emptyStockAt(int xCoordinate, int yCoordinate);

    /**
     * Add the specified amount to the stock value stored at the specified location
     *
     * @param xCoordinate the row number
     * @param yCoordinate the column number
     * @param nutrition the amount
     */
    abstract public void addToStockAt(int xCoordinate, int yCoordinate, int nutrition);

    /**
     * Reduce the stock at the specified location by the given amount
     *
     * @param xCoordinate the row number
     * @param yCoordinate the column number
     * @param nutrition the amount
     */
    abstract public void reduceStockAt(int xCoordinate, int yCoordinate, int nutrition);

    /**
     * Update the stock value at the specified location.
     * Methods like emptyStockAt, addToStockAt and reduceStockAt may be easier to use for specific use cases
     *
     * @param xCoordinate the row number
     * @param yCoordinate the column number
     * @param nutrition the amount
     */
    abstract public void setStockAt(int xCoordinate, int yCoordinate, int nutrition);

    /**
     * Process all items. First all farmers, then all transporters, then all consumers.
     *
     * @param timeStep The time step we are at. This value may be used to determine production frequency of farmers.
     */
    abstract public void processItems(TimeStep timeStep);

    /**
     * After every production by every farmer we record the amount of nutrition that is produced,
     * so we can maintain statistics.
     *
     * @param nutrition the amount
     */
    abstract public void recordProduction(int nutrition);

    /**
     * Retrive the total production amount so far.
     *
     * @return amount of production
     */
    abstract public int getTotalProduction();

    /**
     * After every consumption by every consumer we record the amoung of nutrition that is consumed,
     * so we can maintain statistics.
     *
     * @param nutrition the amount
     */
    abstract public void recordConsumption(int nutrition);

    /**
     * Retrieve the total consumption amount so far.
     *
     * @return amount of consumption
     */
    abstract public int getTotalConsumption();

    /**
     * Display the grid to standard output
     * Items need to override their toString methods to work well with this method.
     * Notice: this method assumes fairly short toString methods (up to 14 chars).
     *
     */
    public void display() {
        int columnWidth = 14;
        for (int i = 0; i <= getWidth() * (columnWidth + 1); i++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 0; i < getHeight(); i++) {
            System.out.print("|");
            for (int j = 0; j < getWidth(); j++) {
                if (grid[i][j] != null) {
                    System.out.print(padCenter(grid[i][j].toString(), columnWidth));
                } else {
                    System.out.print(padCenter("", columnWidth));
                }
                if (j != getWidth() - 1) {
                    System.out.print("|");
                } else {
                    System.out.println("|");
                }
            }
        }
        for (int i = 0; i <= getWidth() * (columnWidth + 1); i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Center a string by prepending and appending space characters to either side.
     *
     * @param inp The input string
     * @param len The requested length og the output string
     * @return Whitespace padded version of the input string
     */
    private String padCenter(String inp, int len) {
        int gap = len - inp.length();
        int leftPad = gap / 2;
        int rightPad = gap - leftPad;
        String out = "";
        for (int i = 0; i < leftPad; i++) {
            out += " ";
        }
        out += inp;
        for (int i = 0; i < rightPad; i++) {
            out += " ";
        }
        return out;
    }

}
