import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0])); // read the input file
        try(BufferedWriter output = new BufferedWriter(new FileWriter(args[1]))) {
            Hash simulator = new Hash(); // create a hash table
            AVLTree simulatorTree = new AVLTree(); // create an avl tree
            String command; // create a string to assign the lines
            while((command = reader.readLine()) != null){
                String[] commandArray = command.split(" "); //split the line by spaces into array
                switch (commandArray[0]){// choose the command
                    case("create_parking_lot"): // if the command is create_parking_lot
                        simulator.insert(new ParkingLot(Integer.parseInt(commandArray[1]),Integer.parseInt(commandArray[2]))); // add the parking lot to hash table
                        simulatorTree.insert(Integer.parseInt(commandArray[1])); // add the capacity constraint of the parking lot to avl
                        break;
                    case("delete_parking_lot"): // if the command is delete_parking_lot
                        simulator.delete(Integer.parseInt(commandArray[1])); // delete the parking lot of given capacity
                        simulatorTree.delete(Integer.parseInt(commandArray[1])); // delete the parking lot from avl too
                        break;
                    case("add_truck"):// if the command is add_truck
                        int capacity = simulator.addATruck(simulatorTree.root,Integer.parseInt(commandArray[1]),Integer.parseInt(commandArray[2])); // add truck to a parking lot according to its remaining capacity and return the capacity of located parking lot
                        output.write(Integer.toString(capacity)); // write the capacity of new parking lot
                        output.write('\n');
                        break;

                    case("ready"):// if the command is ready
                        int IDAndCapacity = simulator.ready(simulatorTree.root,Integer.parseInt(commandArray[1])); // set ready the truck in waiting queue of given capacity ant return its capacity
                        if (IDAndCapacity == -1){ // if the command can not find a truck to set ready
                            output.write(Integer.toString(-1));
                            output.write('\n');
                        }
                        else {
                            output.write((simulator.table[IDAndCapacity].ready.getLast().ID+ " " +IDAndCapacity)); // write ID of truck and capacity of its parking lot which it is located
                            output.write('\n');
                        }
                        break;

                    case("load"): // if the command is load
                        simulator.load = Integer.parseInt(commandArray[2]); // set the total load
                        ArrayList<int[]> IDs = simulator.load(simulatorTree.root,simulatorTree.root,Integer.parseInt(commandArray[1])); // store the trucks that were used and their new parking lots
                        if (IDs.isEmpty()){  // if there is no truck that was used
                            output.write(Integer.toString(-1)); // write -1
                            output.write('\n');
                        }
                        else { // write all the trucks that were used
                            for (int i = 0; i < IDs.size()-1; i++) {
                                output.write((Integer.toString(IDs.get(i)[0]) + " " + Integer.toString(IDs.get(i)[1]) +  " - "));
                            }
                            output.write((Integer.toString(IDs.getLast()[0]) + " " + Integer.toString(IDs.getLast()[1])));
                            output.write('\n');
                        }
                        break;
                    case ("count"): // if the command is count
                        int count = simulator.count(simulatorTree.root,Integer.parseInt(commandArray[1]));  // count all trucks that are greater than given capacity
                        output.write(Integer.toString(count)); // write the count
                        output.write('\n');
                }
            }
            System.out.println("the code has worked");
        }
        catch (IOException e){ // if there is problem with the files
            System.err.println("An error occurred: " + e.getMessage()); // throw error
        }
    }
}

