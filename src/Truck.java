/**
 * This class is to represent the trucks
 * All trucks have ID, max capacity, and remaining capacity
 * Also for queues they have predecessors and successors
 */
public class Truck {
    Truck predecessor;
    Truck successor;
    int ID;
    int maxCapacity;
    int remainingCapacity;

    public Truck() {
    }

    public Truck(int ID, int maxCapacity) {
        this.ID = ID;
        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
    }

}
