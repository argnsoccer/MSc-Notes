abstract class Farmer extends AbstractItem {

    public Farmer(){

    }


    protected int getStock() {
        return grid.getStockAt(xCoordinate, yCoordinate);
    }


    protected void addToStock(int nutrition) {
        grid.addToStockAt(xCoordinate, yCoordinate, nutrition);
    }


    protected void reduceStock(int nutrition) {
        grid.reduceStockAt(xCoordinate, yCoordinate, nutrition);
    }


}