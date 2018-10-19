public class Beaver extends Consumer {
    
    public Beaver(Grid g, int x, int y) {
        grid = g;
        xCoordinate = x;
        yCoordinate = y;

        grid.registerItem(x, y, this);
    }


    public void process(TimeStep timeStep){
        if(getStock() > 50) {

            reduceStock(5);
            grid.recordConsumption(5);
            if(getStock() > 50) {
                setStockTo(50);
            }
        }
        else if(getStock() > 0 && getStock() < 5) {

            grid.recordConsumption(getStock());
            reduceStock(getStock());
        }
        else if(getStock() > 0) {

            grid.recordConsumption(5);
            reduceStock(5);
        }
    }

    public String toString() {
        String temp = "Beaver(" + getStock() + ")";
        return temp;
    }
    
}