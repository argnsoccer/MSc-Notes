public class RadishFarmer extends Farmer {

    public RadishFarmer(Grid g, int x, int y) {
        grid = g;
        xCoordinate = x;
        yCoordinate = y;
        grid.registerItem(x, y, this);
    }

    //bounds checking help from https://stackoverflow.com/questions/43816484/finding-the-neighbors-of-2d-array
    public void process(TimeStep timeStep){
        boolean flag = false;
        for(int i = yCoordinate-1; i <= yCoordinate+1 ; i++) {
            for(int j = xCoordinate-1; j <= xCoordinate+1; j++) {
                if (! ((i == yCoordinate) && (j == xCoordinate))) {
                    if(within(i,j)) {

                        if(grid.getItem(j,i) instanceof Farmer) {
                            flag = true;
                        }
                    }
                }
            }
        }
        
        if(!flag) {
            if(timeStep.getValue() % 3 == 0) {
                addToStock(10);
                grid.recordProduction(10);
            }
        }
    }

    //bounds checking help from https://stackoverflow.com/questions/43816484/finding-the-neighbors-of-2d-array
    private boolean within(int y, int x) {
        if ((x < 0) || (y < 0)) {
            return false;
        }
        if( (x >= grid.getHeight()) || (y >= grid.getWidth())) {
            return false;
        }

        return true;
    }

    public String toString() {
        String temp = "Radish(" + getStock() + ")";
        return temp;
    }

}