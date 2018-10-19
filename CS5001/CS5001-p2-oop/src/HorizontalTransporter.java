public class HorizontalTransporter extends Transporter {

    public HorizontalTransporter(Grid g, int x, int y, int amt) {
        grid = g;
        xCoordinate = x;
        yCoordinate = y;
        amount = amt;
        grid.registerItem(x, y, this);
    }

    public void process(TimeStep timeStep){
        for(int i = 0; i < grid.getHeight(); i++) {
            for(int j = 0; j < grid.getWidth(); j++) {
                if(grid.getItem(i,j) instanceof Farmer) {
                    AbstractItem farm = grid.getItem(i,j);
                    for(int x = xCoordinate+1; x < grid.getWidth(); x++) {
                        if(grid.getItem(i,x) instanceof Consumer) {
                            AbstractItem mouth = grid.getItem(i,x);
                            if(farm.getStock() < amount && farm.getStock() > 0) {
                                addToStock(farm.getStock());
                                farm.reduceStock(farm.getStock());
                                mouth.addToStock(farm.getStock());
                            }
                            else if(farm.getStock() >= amount) {
                                addToStock(amount);
                                farm.reduceStock(amount);
                                mouth.addToStock(amount);
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
                else if(grid.getItem(i,j) instanceof Consumer) {
                    AbstractItem mouth = grid.getItem(i,j);
                    for(int x = xCoordinate+1; x < grid.getWidth(); x++) {
                        if(grid.getItem(i,x) instanceof Farmer) {
                            AbstractItem farm = grid.getItem(i,x);
                            if(farm.getStock() < amount && farm.getStock() > 0) {
                                addToStock(farm.getStock());
                                farm.reduceStock(amount);
                                mouth.addToStock(amount);
                            }
                            else if(farm.getStock() >= amount) {
                                addToStock(amount);
                                farm.reduceStock(amount);
                                mouth.addToStock(amount);
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
                else{
                    break;
                }
            }
        }
    
    }

    public String toString() {
        String temp = "HT";
        return temp;
    }

}