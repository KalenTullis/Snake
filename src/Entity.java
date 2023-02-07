public class Entity {
    private int xCoord;
    private int yCoord;

    //Construch entity with assigned coordinates
    public Entity(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    //Return xCoord of entity
    public int getXCoord() {
        return xCoord;
    }

    //Return yCoord of entity
    public int getYCoord() {
        return yCoord;
    }
    
    //Set xCoord to new value
    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    //set yCoord to new value
    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
