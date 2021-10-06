/*This class doesn't actually get used. I just left it in when I started modifying the add
method in my single linked list class on in case what I was doing didn't work/worked more poorly than what
I had here. That way, if I messed things up, I only had to copy/paste this method and I had... like a save point
of sorts.
 */

public class DeprecatedSingleLinkedListMethods {

    Node top;

    //Original add method from before I decided to chunk it out.
    public void add(Rental rental) {
        Node temp = new Node(rental, null), ptr = top;

        // no list
        if (top == null) {
            top = new Node(rental, null);
            return;
        }
//        else {
//            while (temp.getNext() != null) {
//                temp = temp.getNext();
//            }
//           temp.setNext(new Node(rental, null));
//        }

        // rental is a Game, and rental goes on top
        if (rental instanceof Game && top.getData().dueBack.after(rental.dueBack)) {
            top = new Node(rental, top);
            return;
        } else if (rental instanceof Game) {
            while (ptr.getNext() != null && ptr.getData() instanceof Game) {
                if (ptr.getNext().getData().dueBack.after(rental.dueBack)) {
                    temp.setNext(ptr.getNext());
                    ptr.setNext(temp);
                    return;
                }
                //System.out.println("m");
                ptr = ptr.getNext(); //Ok so this works now.
            }
            ptr.setNext(temp); //we just forgot to have it add things when it reached the end of the line.
        } else {
            //  more code goes here.
            Node top2 = top;

            while (ptr.getData() instanceof Game && ptr.getNext() != null) {
                if (ptr.getData() instanceof Game && ptr.getNext().getData() instanceof Consol3) {
                    top2 = ptr; //first instance where ptr.getNext does not yield a game class
                }
                ptr = ptr.getNext();
            }

            if (top2.getNext().getData().dueBack.after(rental.dueBack) && top2.getNext().getData() instanceof Consol3) {
                Node newFirstConsoleNode = new Node(rental, top2.getNext());
                top2.setNext(newFirstConsoleNode);
                ptr = newFirstConsoleNode;
            } //So this works... but we should definitely try to clean it up a bit later if there's time

            while (ptr.getNext() != null) {
                if (ptr.getNext().getData().dueBack.after(rental.dueBack)) {
                    temp.setNext(ptr.getNext());
                    ptr.setNext(temp);
                    return;
                }
                ptr = ptr.getNext();

            }
            //System.out.println(ptr.getData().nameOfRenter);
            ptr.setNext(temp);
        }
    }

    void strayWhileLoopFromAddGame() {

//        while (ptr.getNext() != null && ptr.getNext().getData() instanceof Game) {
//
//            if (ptr.getNext().getData().dueBack.after(rental.dueBack)) {
//                temp.setNext(ptr.getNext());
//                ptr.setNext(temp);
//                return;
//            }
//
//            if (ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
//                sortInstancesOfGameRentalsWithTheSameReturnDate(rental);
//                return;
//            }
//
//            if (ptr.getNext() == null || ptr.getNext().getData() instanceof Consol3) {
//                Node temp2 = ptr.getNext();
//                temp.setNext(temp2);
//                ptr.setNext(temp);
//                return;
//            }
//
//            //System.out.println("m");
//            ptr = ptr.getNext(); //Ok so this works now.
//        }
//
//        if (ptr.getNext() != null) {
//            temp.setNext(ptr.getNext());
//            ptr.setNext(temp);
//        }
//
//        ptr.setNext(temp);
    }

    void oldNonFunctionalAddConsoleMethod() {
        //This just pushes through all of our games
//        while (ptr.getNext() != null && ptr.getNext().getData() instanceof Game) {
//
//            if (ptr.getData() instanceof Game && !(ptr.getNext().getData() instanceof Game)) {
//                top2 = ptr; //top2 is last instance of game, first of non-game next
//                //ptr at this point should be the first console
//            }
//            ptr = ptr.getNext();
//
//        }
//
//        //In case we reach the end of the line and there is no Console
//        if (top2.getNext() == null) {
//            top2.setNext(temp);
//            return;
//        }
//
//        //In theory, this should give us our new top
//        if (top2.getNext().getData().dueBack.after(rental.dueBack)) {
//            temp.setNext(top2.getNext());
//            top2.setNext(temp); //this allows us to insert above the first console rental in the list
//            return;
//        }
//
//
//
//        while (ptr.getNext() != null) {
//            if (ptr.getNext().getData().dueBack.after(rental.dueBack)) {
//                temp.setNext(ptr.getNext());
//                ptr.setNext(temp);
//                return;
//            }
//
//            if (ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
//                sortInstancesOfGameRentalsWithTheSameReturnDate(rental);
//                return;
//            }
//
//            ptr = ptr.getNext();
//        }
//        ptr.setNext(temp);
     }


     /*Broken code from our sort equals console method */

    //Node top2 = ptr; //This should be our last node of type game if there are any.
    //Also assuming that this won't equal top under any circumstances.
    //...honestly, this variable might not even be necessary at this point.

//        while (ptr.getNext() != null && ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
//
//            if (ptr.getNext().getData().dueBack.equals(rental.dueBack) &&
//                    nameIsBefore(ptr.getNext().getData(), rental)) {
//                temp.setNext(ptr.getNext());
//                ptr.setNext(temp);
//                return;
//            }
//
//            ptr = ptr.getNext();
//        }
//
//        temp.setNext(ptr.getNext());
//        ptr.setNext(temp);


    //More broken code that may come in hangy later
//
//    Node temp = new Node(rental, null),
//            ptr =  top;
    //This puts the pointer at 1 before top which helps to deal with that sorting gap issue we were having

//        if (top == null) {
//        top = temp; //In theory, this shouldn't be possible... but just in case.
//        return;
//    }
//        System.out.println("SIOCRWTSRD");
//
//        while (ptr.getNext() != null && !(ptr.getData() instanceof Consol3)) {
//
//        if(ptr.getData() instanceof Game && ptr.getNext().getData() instanceof Consol3
//                && ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
//            if (nameIsBefore(ptr.getNext().getData(), rental)) {
//                System.out.println("If statement got triggered");
//                temp.setNext(ptr.getNext().getNext());
//                ptr.getNext().setNext(temp);
//            }
//            return;
//        }
//
//        ptr = ptr.getNext();
//    }
//
//        temp.setNext(ptr.getNext());
//        ptr.setNext(temp);

    //Old sort rentals of equal dates method.
    //In reality, there was probably nothing wrong with it.
    //I was just having issues debugging because I accidentally had my add console method
    // calling the sort games with equal dates method. But I'd built a new sort rentals method by the time
    // I figured it out. so this is going here for now.

//    private void sortInstancesOfConsoleRentalsWithTheSameReturnDate(Rental rental) {
//        if (!isConsole(rental)) {
//            return;
//        }
//
//        Node temp = new Node(rental, null), ptr = new Node(new Consol3(), top);
//
//        if (top.getData().dueBack.equals(rental.dueBack) && (nameIsBefore(top.getData(), rental))
//                && top.getData() instanceof Consol3) {
//            temp.setNext(top);
//            top = temp;
//            return;
//        }
//
//        while (ptr.getNext() != null && !(ptr.getNext().getData().dueBack.equals(rental.dueBack))) {
//            ptr = ptr.getNext();
//        }
//
//        while (ptr.getNext() != null && ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
//            if (ptr.getNext().getData().dueBack.equals(rental.dueBack) &&
//                    nameIsBefore(ptr.getNext().getData(), rental)) {
//                temp.setNext(ptr.getNext());
//                ptr.setNext(temp);
//                return;
//            }
//            ptr = ptr.getNext();
//        }
//
//        temp.setNext(ptr.getNext());
//        ptr.setNext(temp);
//
//    }

    //This was my original idea for a combined sortRentalsWithEqualDates method
    //It was getting a bit cumbersome trying to manage both games and consoles. So I chunked it out
    //into two smaller methods. But I left it in the linked list class for a time so I'd have a template
    //two base the other two methods on. Figured I'd keep this here in case I ever needed it/decided to use it


//    //This should probably be relegated to the derpreciated linked list class. But it's still nice to have as a
//    //reference over here.
//    private void sortInstancesOfRentalsWithTheSameReturnDate(Rental rental) {
//        boolean isConsole = isConsole(rental);
//        Node temp = new Node(rental, null), ptr = top;
//
//        if (top.getData().dueBack.equals(rental.dueBack) && nameIsBefore(top.getData(), rental) &&
//                isConsole == isConsole(top.getData())) {
//            temp = new Node(rental, top);
//            top = temp;
//            return; //makes new top node... only seems to work after the second try.
//        }
//
//        while (ptr.getNext() != null && !(ptr.getNext().getData().dueBack.equals(rental.dueBack))) {
//            ptr = ptr.getNext();
//        }
//
//        while (ptr.getNext() != null && ptr.getNext().getData().dueBack.equals(rental.dueBack)) {
//
//            if (ptr.getNext().getData().dueBack.equals(rental.dueBack) && nameIsBefore(ptr.getNext().getData(), rental)) {
//                temp.setNext(ptr.getNext());
//                ptr.setNext(temp);
//                return;
//            }
//
//            ptr = ptr.getNext();
//        }
//
//        temp.setNext(ptr.getNext());
//        ptr.setNext(temp);
//
//    }

    //old remove method
//    public Rental remove(int index) {
//        //  more code goes here.
//        Node ptr = top;
//        Rental r = get(index);
//
//
//
//        for (int i = 0; i < index - 1; i++) {
//            ptr = ptr.getNext(); //in theory, index should never be able to exceed the length of the list.
//        }
//
//        if (ptr.getNext() != null) {
//            if (ptr.getNext().getNext() == null) {
//                ptr.setNext(null);
//                return r;
//            }
//        }
//
//        ptr.setNext(ptr.getNext().getNext());
//        return r;
//
//    }
}
