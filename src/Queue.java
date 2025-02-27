/**
 * This class represents the queues such as ready and waiting sections of parking lots
 * Each queue has a head and tail truck, which are plain trucks used to simplify the implementation.
 * Also, each queue has truck count
 */
public class Queue {
    Truck head;
    Truck tail;
    int count;

    public Queue() { // construct a new queue
        this.head = new Truck();
        this.tail = new Truck();
        head.successor = tail;
        tail.predecessor = head;
        this.count = 0;
    }
    public void add(Truck truck){ // add a new truck
        tail.predecessor.successor= truck; // it is placed between the tail and the former predecessor of the tail
        truck.predecessor = tail.predecessor;  // link the truck to its predecessor
        truck.successor = tail; // truck is in front of tail
        tail.predecessor = truck;
        count += 1; // increment the count

    }
    public Truck removeFirst(){ // remove the first truck in queue
        Truck leavingTruck = head.successor;
        head.successor = leavingTruck.successor ; // link the predecessor and successor of leavingTruck
        leavingTruck.successor.predecessor = head;
        count -= 1; // decrement the count
        return leavingTruck;
    }
    public Truck getLast(){
        return tail.predecessor;
    }
    public Truck getFirst(){
        return head.successor;
    }


}
