public class Rabbit extends Consumer {

    public Rabbit(Grid g, int x, int y) {
        grid = g;
        xCoordinate = x;
        yCoordinate = y;
        grid.registerItem(x, y, this);
    }

    public void process(TimeStep timeStep){
        if(getStock() >= 8) {
            grid.recordConsumption(8);
            setStockTo(0);
        }
        else if(getStock() > 0 && getStock() < 8) {
            grid.recordConsumption(getStock());
            setStockTo(0);
        }
    
    }

    public String toString() {
        String temp = "Rabbit(" + getStock() + ")";
        return temp;
    }
}