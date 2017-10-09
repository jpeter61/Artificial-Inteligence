import java.util.LinkedList;

//Solves Furniture Problem
public class FurnitureSolver {

    //Truck class
    private class Truck{
        private Furniture[] shipment;           //shipment list
        private int weight;                     //total weight
        private int value;                      //total value
        private int lastChecked;                //last checked item

        //Constructor
        private Truck(int size, int[][] listOfFurniture){
            shipment = new Furniture[size];
            for(int i = 0; i < size; i++)
                shipment[i] = new Furniture(listOfFurniture[i][1], listOfFurniture[i][2]);

            weight = 0;
            value = 0;
            lastChecked = -1;                   //-1 means not checked
        }

        //Copy constructor
        private Truck(Truck other){
            this.shipment = new Furniture[other.shipment.length];
            for(int i = 0; i < this.shipment.length; i++)
                this.shipment[i] = new Furniture(other.shipment[i]);

            this.weight = other.weight;
            this.value = other.value;
            this.lastChecked = other.lastChecked;
        }

        public int getValue() {
            return value;
        }

        public int getLastChecked() {
            return lastChecked;
        }

        public void setLastChecked(int lastChecked) {
            this.lastChecked = lastChecked;
        }

        public void setActive(int toBeSet){
            shipment[toBeSet].setActive(true);

            weight += shipment[toBeSet].getWeight();
            value += shipment[toBeSet].getValue();
        }

        public int getWeight() {
            return weight;
        }

        public void display(){
            System.out.print("Furniture: ");
            for(int i = 0; i < shipment.length; i++)
                if(shipment[i].isActive())
                    System.out.print(i+1 + " ");
            System.out.println("\n");
            System.out.println("Weight: " + weight + "\n");
            System.out.println("Value: " + value + "\n");

        }
    }

    private int size;
    private int weightLimit;
    private int[][] listOfFurniture;

    //Constructor
    public FurnitureSolver(int size,int weightLimit, int[][] listOfFurniture){
        this.size = size;
        this.listOfFurniture = listOfFurniture;             //Only copies reference
        this.weightLimit = weightLimit;
    }

    //solves furniture problem
    public void solve(){
        LinkedList<Truck> list = new LinkedList<>();        //List of trucks
        int maxValue = Integer.MIN_VALUE;                   //max value
        Truck highestValueTruck = null;

        Truck truck = new Truck(size, listOfFurniture);
        list.addFirst(truck);                               //Create and add first

        while (!list.isEmpty()){                            //While has trucks
            truck = list.removeFirst();                     //Remove first

            if(complete(truck)){                            //If complete truck
                if(truck.getValue() > maxValue){            //If highest value
                    maxValue = truck.getValue();            //Update max
                    highestValueTruck = truck;
                }
            }
            else{                                           //If incomplete
                //generate children
                LinkedList<Truck> children = generate(truck);

                for (int i = 0; i < children.size(); i++)
                    list.addFirst(children.get(i));         //Add children to list
            }
        }
        if (highestValueTruck == null)
            System.out.println("No Solution");              //If no solution
        else
            highestValueTruck.display();        //Display highest value truck
    }

    //Generates Children
    private LinkedList<Truck> generate(Truck truck){
        LinkedList<Truck> children = new LinkedList<>();    //children list

        int lastChecked = truck.getLastChecked();

        //Add this furniture
        Truck childPositive = new Truck(truck);
        //Update last checked
        childPositive.setLastChecked(lastChecked+1);
        //set furniture active
        childPositive.setActive(lastChecked+1);

        //add if not over weight limit
        if(childPositive.getWeight() < weightLimit)
            children.addLast(childPositive);

        //Don't add this furniture
        Truck childNegative = new Truck(truck);
        //Update last checked
        childNegative.setLastChecked(lastChecked+1);
        //Don't need to check weight limit
        children.addLast(childNegative);

        return children;
    }

    //checks if truck is complete
    boolean complete(Truck truck){
        if (truck.getLastChecked() == size-1)
            return true;
        return false;
    }
}
