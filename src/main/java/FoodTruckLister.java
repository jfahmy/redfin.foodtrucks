import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Stores our truck objects and prints them to the user.
 * Encapsulates a child class for the individual foodtruck objects, which implements comparator so that we can easily
 * alphabetize them and insert them in the proper place in the truckList using Java's Collection.binarySearch.
 */
public class FoodTruckLister {
    List<FoodTruck> truckList = new ArrayList<>();
    int listIndex = 0;

    public void truckBuilder(String name, String address) {
        FoodTruck foodTruck = new FoodTruck(name, address);
        insertTruck(foodTruck);
    }

    /* Searching for the appropriate index to insert the truck into our truckList and then executing that insert */
    public void insertTruck(FoodTruck newTruck) {
        int index = Collections.binarySearch(truckList, newTruck);
        if (index < 0) {
            index = (index * -1) - 1;
        }

        truckList.add(index, newTruck);
    }

    public void printList() {
        System.out.println("NAME  |  ADDRESS");
        for(int i=listIndex; i < listIndex + 10 && i < truckList.size(); i++) {
            FoodTruck truck = truckList.get(i);
            System.out.println(truck.name + " | " + truck.address);
        }
        listIndex += 10;

        /* Checking whether we are out of trucks to list so we know to exit the program. */
        if (listIndex >= truckList.size()-1) {
            System.out.println("\nYou've reached the end of the list. Exiting...");
        } else {
            getUserInput();
        }
    }

    /* Method continues to call printList() until the user gives us an exit command*/
    public void getUserInput() {
        System.out.println("Hit enter to see more results or exit to end program.");
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        if (!userInput.equalsIgnoreCase("exit")) {
            printList();
        } else {
            System.out.println("Exiting...");
        }
    }

    public class FoodTruck implements Comparable<FoodTruck> {
        private String name;
        private String address;

        public FoodTruck(String name, String address) {
            this.name = name;
            this.address = address;
        }

        /**
        * Implementing comparator here so that we can sort trucks alphabetically based on their name value.
        * (Instead I could have used Socrata's Order by soSQL option and then response list would come back alphabetized,
        * realized this while reading doc on soSQL at the last minute.)
        */
        @Override
        public int compareTo(FoodTruck otherTruck) {
            return this.name.compareToIgnoreCase(otherTruck.name);
        }
    }
}
