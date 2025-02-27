import java.util.ArrayList;

/**
 * This class is to represent parking lots
 * It has waiting and ready sections
 * It also has truckLimit, capacityConstraint, and load
 */
public class ParkingLot {
    Queue waiting;
    Queue ready;
    int truckLimit;
    int capacityConstraint;
    int load;

    public ParkingLot(int capacityConstraint, int truckLimit) { // create a new parking lot with given capacity constraint and truck limit
        this.waiting = new Queue();
        this.ready = new Queue();
        this.truckLimit = truckLimit;
        this.capacityConstraint = capacityConstraint;
        this.load = 0;
    }

    public void setReady(){ // set ready a truck
        Truck readyTruck = waiting.removeFirst(); // get the first coming truck
        ready.add(readyTruck); // add it to ready section
    }

    public ArrayList<Truck> distributeLoad(int loadAmount){ //distribute the load among the trucks in ready section
        load = loadAmount;
        ArrayList<Truck> loaded = new ArrayList<>();
        Truck truck = ready.getFirst();
        int count = ready.count;
        for (int i = 0; i < count; i++) { // repeat until all trucks are used or the load is depleted
            if (load == 0)
                return loaded;
            if (load >= capacityConstraint){ // if the load can not fit into one truck
                load -= capacityConstraint; // the truck is loaded with what it can take
                truck.remainingCapacity -= capacityConstraint; // reduce the remaining capacity of truck
                loaded.add(ready.removeFirst()); // add it to loaded and remove it from ready section
            }
            else {
                truck.remainingCapacity -= load; // the truck is loaded with the remaining load
                loaded.add(ready.removeFirst()); // add it to loaded and remove it from ready section
                load = 0; // set the load to zero
            }
            if (truck.remainingCapacity == 0) // if the truck is fully loaded, unload it
                truck.remainingCapacity = truck.maxCapacity;
            truck = ready.getFirst(); // pass the next truck
        }
        return loaded; // return all loaded trucks
    }

}

