public class Furniture {

    private int weight;             //Individual Array
    private int value;              //Individual Value
    private boolean active;         //If in truck

    //Constructor
    public Furniture(int weight, int value){
        this.weight = weight;
        this.value = value;
        active = false;
    }

    //Copy Constructor
    public Furniture(Furniture other){
        this.weight = other.getWeight();
        this.value = other.getValue();
        this.active = other.isActive();
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
