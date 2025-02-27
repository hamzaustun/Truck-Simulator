import java.util.ArrayList;

public class Hash {
    ParkingLot[] table; // define table
    int load; // define load

    public Hash() {
        this.table = new ParkingLot[100]; // create array and assign it to table
        this.load = 0; // set initial load to 0
    }
    public void insert(ParkingLot parkingLot){
        if (table.length > parkingLot.capacityConstraint) { // if parking lot can be inserted
            table[parkingLot.capacityConstraint] = parkingLot; // insert it
        }
        else resize(parkingLot); // resize and insert it
    }
    private void resize(ParkingLot parkingLot){ // to resize the hash table
        while (parkingLot.capacityConstraint >= table.length){
            ParkingLot[] tmp = new ParkingLot[2*table.length]; // create a new table which is 2 times larger than the former one
            for (int i = 0; i < table.length; i++) { // copy all the elements in the former table to the latter
                ParkingLot currentParkingLot = table[i];
                tmp[i] = currentParkingLot;
            }
            table = tmp; // assign the new table
        }
        table[parkingLot.capacityConstraint] = parkingLot; // add the given parking lot
    }
    public void delete(int capacityConstraint){ // to delete elements in table
        table[capacityConstraint] = null;
    }

    public int addATruck(AvlNode node, int truckID, int truckCapacity){ // to add a truck to a suitable position
        if (node != null){
            if (node.key <= truckCapacity){ // to choose which child to go
                if (node.key != truckCapacity) { // go right until the truck capacity is equal to parking lot capacity
                    int right = addATruck(node.right,truckID,truckCapacity); // go right
                    if (right != -1) return right; // if suitable position is found, place the truck there
                }
                ParkingLot parkingLot = table[node.key]; // Retrieve the ParkingLot associated with the given node key
                if (parkingLot.truckLimit > parkingLot.waiting.count + parkingLot.ready.count) { // check if there is a place for a new truck
                    parkingLot.waiting.add(new Truck(truckID,truckCapacity)); // add the truck
                    return parkingLot.capacityConstraint; // return the capacity of parking lot that the truck is placed
                }
            }
            int left = addATruck(node.left, truckID, truckCapacity); // search the left subtree
            if (left != -1) return left; // // if suitable position is found, place the truck there

        }
        return -1; // if no position is found, return -1
    }
    public int ready(AvlNode node, int capacity){ // to get the truck ready
        if (node != null){ // if node is null, the leaf is already reached so time to return
            if (node.key >= capacity){ // to choose which child to go
                if (node.key != capacity) { // go left until the truck capacity is equal to parking lot capacity
                    int left = ready(node.left,capacity);// go left
                    if (left != -1) return left;// if  a truck found, set it ready
                }
                ParkingLot parkingLot = table[node.key]; // Retrieve the ParkingLot associated with the given node key
                if (parkingLot.waiting.count !=0){ // if there is a truck in waiting
                    parkingLot.setReady(); // set it ready
                    return parkingLot.capacityConstraint;
                }
            }
            int right = ready(node.right,capacity);// search the right subtree
            if (right!= -1) return right;
        }
        return -1;// if no truck is found, return -1
    }

    /**
     * This function is similar to addATruck, but it adds an already existing truck.
     */
    public int addATruckForLoad(AvlNode node,Truck truck){
        if (node != null){
            if (node.key <= truck.remainingCapacity){
                if (node.key != truck.remainingCapacity) {
                    int right = addATruckForLoad(node.right,truck);
                    if (right != -1) return right;
                }
                ParkingLot parkingLot = table[node.key];
                if (parkingLot.truckLimit > parkingLot.waiting.count + parkingLot.ready.count) {
                    parkingLot.waiting.add(truck);
                    return parkingLot.capacityConstraint;
                }
            }
            int left = addATruckForLoad(node.left, truck);
            if (left != -1) return left;
        }
        return -1;
    }

    /**
     * This function distribute the given load among the trucks that are available
     * @param root the root of the avl tree
     * @param node current node
     * @param capacity capacity of loaded parking lot
     * @return all the used trucks and their new parking lot
     */
    public ArrayList<int[]> load(AvlNode root, AvlNode node, int capacity){
        ArrayList<int[]> all = new ArrayList<>(); // all used trucks
        ArrayList<Truck> loaded; // loaded trucks in the current node
        if (node != null){
            if (node.key >= capacity){ //choose which child to go
                if (node.key != capacity) {
                    all.addAll(load(root, node.left, capacity)); // traverse left subtree to gather all the used trucks in one list
                }
                ParkingLot parkingLot = table[node.key]; // Retrieve the ParkingLot associated with the given node key
                loaded = parkingLot.distributeLoad(load); // use the trucks in current node
                load = parkingLot.load; // update load
                for (Truck t : loaded){
                    all.add(new int[] {t.ID,addATruckForLoad(root,t)}); // add every ID and their new parking lot of the loaded truck
                }
            }
            all.addAll(load(root,node.right,capacity));// traverse right subtree
        }
        return all;
    }

    /**
     * Counts all the trucks that are placed in a parking lot such that its capacity is greater than given capacity
     * @param node current node
     * @param capacity given capacity
     * @return count of trucks
     */
    public int count(AvlNode node,int capacity){
        int count = 0;
        if (node != null){
          if (node.key > capacity){ // the trucks in this node are suitable to count
                ParkingLot parkingLot = table[node.key]; // retrieve parking lot
                count += count(node.left,capacity) + parkingLot.ready.count + parkingLot.waiting.count; // increase the count with all suitable trucks in the left subtree and the number of trucks in the current parking lot
          }
          count += count(node.right,capacity); // increase the count with all suitable trucks in the right subtree
        }
        return count;
    }
}












