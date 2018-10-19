public class Grid extends AbstractGrid {
    private int height;
    private int width;
    private int production;
    private int consumption;
    
    public Grid(int h, int w) {
        height = h;
        width = w;
        production = 0;
        consumption = 0;
        grid = new AbstractItem[h][w];
        stock = new int[h][w];
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                grid[i][j] = null;
                stock[i][j] = 0;
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void registerItem(int xCoordinate, int yCoordinate, AbstractItem item) {
        if(xCoordinate < height && yCoordinate < width) {
            grid[xCoordinate][yCoordinate] = item;
            
        }
        else {
            System.out.println("Your item is out of bounds! Not Registered.");
            
        }
    }

    public AbstractItem getItem(int xCoordinate, int yCoordinate) {
        return grid[xCoordinate][yCoordinate];
    }
    
    public int getStockAt(int xCoordinate, int yCoordinate) {
        return stock[xCoordinate][yCoordinate];
    }

    public void emptyStockAt(int xCoordinate, int yCoordinate) {
        stock[xCoordinate][yCoordinate] = 0;
    }

    public void addToStockAt(int xCoordinate, int yCoordinate, int nutrition){
        stock[xCoordinate][yCoordinate] += nutrition;
    }

    public void reduceStockAt(int xCoordinate, int yCoordinate, int nutrition) {
        stock[xCoordinate][yCoordinate] -= nutrition;
    }

    public void setStockAt(int xCoordinate, int yCoordinate, int nutrition) {
        stock[xCoordinate][yCoordinate] = nutrition;
    }

    public void processItems(TimeStep timeStep) {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                AbstractItem temp = grid[i][j];
                if(temp instanceof Farmer) {
                    temp.process(timeStep);
                }
                else if(temp instanceof Transporter) {
                    temp.process(timeStep);
                }
                else if(temp instanceof Consumer) {
                    temp.process(timeStep);
                }
                
            }
        }
    }

    public void recordProduction(int nutrition) {
        production += nutrition;
    }

    public int getTotalProduction() {
        return production;
    }

    public void recordConsumption(int nutrition) {
        consumption += nutrition;
    }

    public int getTotalConsumption() {
        return consumption;
    }
}