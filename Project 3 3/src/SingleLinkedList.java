import javax.swing.*;
import java.io.Console;
import java.io.Serializable;
import java.util.Random;

public class SingleLinkedList implements Serializable
{
    private Node top;

    public SingleLinkedList() {
        top = null;
    }

    // This method has been provided and you are not permitted to modify
    public int size() {
        if (top == null)
            return 0;

        int total = 0;
        Node temp = top;
        while (temp != null) {
            total++;
            temp = temp.getNext();
        }

        return total;
    }

    // This method has been provided and you are not permitted to modify
    public void clear () {
        Random rand = new Random();
        while (size() > 0) {
            int number = rand.nextInt(size());
            remove(number);
        }
    }

    /********************************************************************************************
     *
     *    Your task is to complete this method.
     *
     *
     *
     * @param rental the unit begin rented
     */
    public void add(Rental rental) {
        //Node temp = new Node(rental, null), ptr = top;

        // no list
        if (top == null) {
            //System.out.println("Nothing in top. Adding to top.");
            top = new Node(rental, null);
            return;
        }

        if (isGame(rental)) {
            addGame(rental);
        } else if (isConsole(rental)){
            //System.out.println("addConsole triggered");
            addConsole(rental);
        } else {
            JOptionPane.showMessageDialog(null, "I don't know how you managed to do this." +
                    "\nBut please do not do it again.");
        }


    }

    private void addGame(Rental rental) {
        Node temp = new Node(rental, null), ptr = new Node(new Game(), top);

        // rental is a Game, and rental goes on top
        if (top.getData().dueBack.after(rental.dueBack)) {
            top = new Node(rental, top); //functions as intended
            //System.out.println("new game inserted at top");
            return;
        }

        if (top.getData().dueBack.equals(rental.dueBack)) {
            sortInstancesOfGameRentalsWithTheSameReturnDate(rental); //functions as intended
            //System.out.println("rental had equal date to top; compared");
            return;
        }

        //Normal criteria; game is somewhere in the list
        while (ptr.getNext() != null && ptr.getNext().getData() instanceof Game) {
            if (ptr.getNext().getData().dueBack.after(rental.dueBack)) {
                temp.setNext(ptr.getNext());
                ptr.setNext(temp);
                //System.out.println("Orange");
                return;
            }

            if (ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
                sortInstancesOfGameRentalsWithTheSameReturnDate(rental);
                return;

            }

            ptr = ptr.getNext(); //Ok so this works now.
        }

        if (ptr.getNext() == null || ptr.getNext().getData() instanceof Consol3) {
            temp.setNext(ptr.getNext());
            ptr.setNext(temp);
            //System.out.println("Last Game");
            return;
        }

        //ptr.setNext(temp);
    }

    private void addConsole2(Rental rental) {
        Node temp = new Node(rental, null), ptr = new Node(new Game(), top);

        // rental is a Game, and rental goes on top
        if (top.getData() instanceof Consol3) {
            if (top.getData().dueBack.after(rental.dueBack)) {
                top = new Node(rental, top); //functions as intended
                //System.out.println("new game inserted at top");
                return;
            }

            if (top.getData().dueBack.equals(rental.dueBack)) {
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental); //functions as intended
                //System.out.println("rental had equal date to top; compared");
                return;
            }
        }

        while (ptr.getNext() != null && !isConsole(ptr.getNext().getData())) {
            ptr = ptr.getNext();
        }

        //Normal criteria; game is somewhere in the list
        while (ptr.getNext() != null && ptr.getNext().getData() instanceof Consol3) {
            if (ptr.getNext().getData().dueBack.after(rental.dueBack)) {
                temp.setNext(ptr.getNext());
                ptr.setNext(temp);
                //System.out.println("Ananas");
                return;
            }

            if (ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental);
                return;

            }

            ptr = ptr.getNext(); //Ok so this works now.
        }

        ptr.setNext(temp);
    }

    private void addConsole(Rental rental) {
        Node temp = new Node(rental, null), ptr = top;

        //Just making the assumption that there are no game rentals inside of our linked list at this point
        if (top.getData().dueBack.after(rental.dueBack) && isConsole(top.getData())) {
            top = new Node(rental, top);
            //System.out.println("something");
            return;
        }

        //checking dates on equal values at top... assuming that they were only consoles in this list.
        if (top.getData().dueBack.equals(rental.dueBack) && isConsole(top.getData())) {
            sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental);
            //System.out.println("something else");
            return;
        }

        while (ptr.getData() instanceof Game && ptr.getNext() != null) {
            ptr = ptr.getNext();
            //System.out.println("Grey goosse");
            //adds the first Console to the list if there are none
            if (ptr.getData() instanceof Game && ptr.getNext() == null) {
                ptr.setNext(temp);
                return;
            }

            //checks if the top console's return date is after the new rental return date
            if (ptr.getData() instanceof Game && ptr.getNext().getData() instanceof Consol3 &&
                    ptr.getNext().getData().dueBack.after(rental.dueBack)) {
                temp.setNext(ptr.getNext());
                ptr.setNext(temp);
                return;
            }

            //checks to see if the two dates are equal and then sorts them by name
            if (ptr.getData() instanceof Game && ptr.getNext().getData() instanceof Consol3 &&
                    ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental);
                //System.out.println("Sort at first console");
                return;
            }
        }

        //Mark: -- this should be the first point where ptr's data is of type Consol3

        while (ptr.getNext() != null) {
            //This code was ripped from our addGame method.
            if (ptr.getNext().getData().dueBack.after(rental.dueBack)) {
                temp.setNext(ptr.getNext());
                ptr.setNext(temp);
                //System.out.println("Orange");
                return;
            }

            if (ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
                //System.out.println("Sort in main console list");
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental);
                return;

            }

            ptr = ptr.getNext(); //Ok so this works now.
        }


        //System.out.println("Is this getting hit");
        //If none of the conditions are met in the above conditional statements, then temp should be added at the
        // very bottom.
        ptr.setNext(temp);

    }

    private void sortInstancesOfGameRentalsWithTheSameReturnDate(Rental rental) {
        if (isConsole(rental)) {
            return;
        }
        Node temp = new Node(rental, null), ptr = new Node(new Consol3(), top);

        if (top.getData().dueBack.equals(rental.dueBack) && (nameIsBefore(top.getData(), rental))) {
            temp.setNext(top);
            top = temp;
            return;
        }

        while (ptr.getNext() != null && !(ptr.getNext().getData().dueBack.equals(rental.dueBack))) {
            ptr = ptr.getNext();
        }

        while (ptr.getNext() != null && ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
            if (ptr.getNext().getData().dueBack.equals(rental.dueBack) &&
                nameIsBefore(ptr.getNext().getData(), rental)) {
                temp.setNext(ptr.getNext());
                ptr.setNext(temp);
                return;
            }
            ptr = ptr.getNext();
        }

        temp.setNext(ptr.getNext());
        ptr.setNext(temp);
    }

    private void sortInstancesOfConsoleRentalsWithTheSameReturnDate2(Rental rental) {
        if (!isConsole(rental)) {
            return;
        }

        Node temp = new Node(rental, null), ptr = new Node(null, top);

        //In the event that top is a console.
        if (top != null && top.getData() instanceof Consol3) {
            if (top.getData().dueBack.equals(rental.dueBack) && nameIsBefore(top.getData(), rental)) {
                temp.setNext(top);
                top = temp;
                return;
            }
        }

        //This should cycle ptr through the linked list until the last instance where ptr is a Game;
        //The first instance where ptr is a console.
        while (ptr.getNext() != null && !(ptr.getNext().getData() instanceof Consol3)) {
            ptr = ptr.getNext();
        }

        Node top2 = ptr.getNext(); //first top of type Consol3

        //Check to see if the name is the date is the same in top2
        if (top2 != null && top2.getData().dueBack.equals(rental.dueBack)
                && nameIsBefore(top2.getData(), rental)) {
            temp.setNext(top2);
            ptr.setNext(temp); //replaces top2 if so
            return;
        }

        //cycles through all instances where ptr dates aren't identical
        while (ptr.getNext() != null && !(ptr.getNext().getData().dueBack.equals(rental.dueBack))) {
            ptr = ptr.getNext();
        }

        //cycles through instances where ptr dates are identical
        while (ptr.getNext() != null && ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
            if (ptr.getNext().getData().dueBack.equals(rental.dueBack) &&
                    nameIsBefore(ptr.getNext().getData(), rental)) {
                temp.setNext(ptr.getNext());
                ptr.setNext(temp);
                return;
            }
            ptr = ptr.getNext();
        }

        temp.setNext(ptr.getNext());
        ptr.setNext(temp);
    }

    public Rental remove(int index) {
        //  more code goes here.
        Node ptr = top;
        Rental r = get(index);

        //for removing the top
        if (index == 0) {
            if (top.getNext() != null) {
                top = top.getNext();
            } else {
                top = null;
            }
            return r;
        }

        for (int i = 0; i < index - 1; i++) {
            ptr = ptr.getNext();
        }

        if (ptr.getNext() != null) {
            if (ptr.getNext().getNext() == null) {
                ptr.setNext(null);
            } else {
                ptr.setNext(ptr.getNext().getNext());
            }
        }
        return r;
    }

    public Rental get(int index) {
        if (top == null)
            return null;

        //  more code goes here.
        Node ptr = top;

        for (int i = 0; i < index; i++) {
            ptr = ptr.getNext(); //in theory, index/ptr shouldn't be able to exceed the list length
        }

        // needs to be changed - (This instruction is here for the method to compile)
        return ptr.getData();
    }

    public void display() {
        Node temp = top; //I wonder what this does?
        while (temp != null) {
            System.out.println(temp.getData()); //ohhh it's a testing/debug method.
            temp = temp.getNext(); //Ok, I guess we'll leave it.
        }
    }

    @Override
    public String toString() {
        return "LL {" +
                "top=" + top +
                ", size=" + size() +
                '}';
    }

    public Node getTop() {
        return top;
    }

    private boolean nameIsBefore(Rental rentalInList, Rental newRental) {
        String renterNAmeInList = rentalInList.nameOfRenter.toLowerCase(),
                newRenterName = newRental.nameOfRenter.toLowerCase();
        int comparator = renterNAmeInList.compareTo(newRenterName);

        return comparator > 0; //new renter is before renter in the list
    }

        /**
         * These two methods probably would've been more useful if I had used the single
         * method for checking instances of rentals with equal dates. Still ended up using them in the smaller
         * methods though.
        */
    private boolean isGame(Rental rental) {
        return rental instanceof Game;
    }

    private boolean isConsole(Rental rental) {
        return rental instanceof Consol3;
    }
}