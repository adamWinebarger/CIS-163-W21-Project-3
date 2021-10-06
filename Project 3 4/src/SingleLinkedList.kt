import java.io.Serializable
import java.util.*
import javax.swing.JOptionPane

class SingleLinkedList : Serializable {
    var top: Node? = null
        private set

    // This method has been provided and you are not permitted to modify
    fun size(): Int {
        if (top == null) return 0
        var total = 0
        var temp = top
        while (temp != null) {
            total++
            temp = temp.next
        }
        return total
    }

    // This method has been provided and you are not permitted to modify
    fun clear() {
        val rand = Random()
        while (size() > 0) {
            val number = rand.nextInt(size())
            remove(number)
        }
    }

    /********************************************************************************************
     *
     * Your task is to complete this method.
     *
     *
     *
     * @param rental the unit begin rented
     */
    fun add(rental: Rental) {
        //Node temp = new Node(rental, null), ptr = top;

        // no list
        if (top == null) {
            //System.out.println("Nothing in top. Adding to top.");
            top = Node(rental, null)
            return
        }
        if (isGame(rental)) {
            addGame(rental)
        } else if (isConsole(rental)) {
            //System.out.println("addConsole triggered");
            addConsole(rental)
        } else {
            JOptionPane.showMessageDialog(
                null, """
     I don't know how you managed to do this.
     But please do not do it again.
     """.trimIndent()
            )
        }
    }

    private fun addGame(rental: Rental) {
        val temp = Node(rental, null)
        var ptr: Node? = Node(Game(), top)

        // rental is a Game, and rental goes on top
        if (top!!.data!!.dueBack!!.after(rental.dueBack)) {
            top = Node(rental, top) //functions as intended
            //System.out.println("new game inserted at top");
            return
        }
        if (top!!.data!!.dueBack == rental.dueBack) {
            sortInstancesOfGameRentalsWithTheSameReturnDate(rental) //functions as intended
            //System.out.println("rental had equal date to top; compared");
            return
        }

        //Normal criteria; game is somewhere in the list
        while (ptr!!.next != null && ptr.next!!.data is Game) {
            if (ptr.next!!.data?.dueBack!!.after(rental.dueBack)) {
                temp.next = ptr.next
                ptr.next = temp
                //System.out.println("Orange");
                return
            }
            if (ptr.next!!.data?.dueBack == rental.dueBack) {
                sortInstancesOfGameRentalsWithTheSameReturnDate(rental)
                return
            }
            ptr = ptr.next //Ok so this works now.
        }
        if (ptr.next == null || ptr.next!!.data is Consol3) {
            temp.next = ptr.next
            ptr.next = temp
            //System.out.println("Last Game");
            return
        }

        //ptr.setNext(temp);
    }

    private fun addConsole2(rental: Rental) {
        val temp = Node(rental, null)
        var ptr: Node? = Node(Game(), top)

        // rental is a Game, and rental goes on top
        if (top!!.data is Consol3) {
            if (top!!.data?.dueBack!!.after(rental.dueBack)) {
                top = Node(rental, top) //functions as intended
                //System.out.println("new game inserted at top");
                return
            }
            if (top!!.data?.dueBack == rental.dueBack) {
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental) //functions as intended
                //System.out.println("rental had equal date to top; compared");
                return
            }
        }
        while (ptr!!.next != null && !isConsole(ptr.next!!.data)) {
            ptr = ptr.next
        }

        //Normal criteria; game is somewhere in the list
        while (ptr?.next != null && ptr.next!!.data is Consol3) {
            if (ptr.next!!.data?.dueBack!!.after(rental.dueBack)) {
                temp.next = ptr.next
                ptr.next = temp
                //System.out.println("Ananas");
                return
            }
            if (ptr.next?.data?.dueBack == rental.dueBack) {
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental)
                return
            }
            ptr = ptr.next //Ok so this works now.
        }
        if (ptr != null) {
            ptr.next = temp
        }
    }

    private fun addConsole(rental: Rental) {
        val temp = Node(rental, null)
        var ptr = top

        //Just making the assumption that there are no game rentals inside of our linked list at this point
        if (top!!.data!!.dueBack!!.after(rental.dueBack) && isConsole(top!!.data)) {
            top = Node(rental, top)
            //System.out.println("something");
            return
        }

        //checking dates on equal values at top... assuming that they were only consoles in this list.
        if (top!!.data!!.dueBack == rental.dueBack && isConsole(top!!.data)) {
            sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental)
            //System.out.println("something else");
            return
        }
        while (ptr!!.data is Game && ptr.next != null) {
            ptr = ptr.next
            //System.out.println("Grey goosse");
            //adds the first Console to the list if there are none
            if (ptr!!.data is Game && ptr.next == null) {
                ptr.next = temp
                return
            }

            //checks if the top console's return date is after the new rental return date
            if (ptr.data is Game && ptr.next!!.data is Consol3 &&
                ptr.next!!.data?.dueBack!!.after(rental.dueBack)
            ) {
                temp.next = ptr.next
                ptr.next = temp
                return
            }

            //checks to see if the two dates are equal and then sorts them by name
            if (ptr.data is Game && ptr.next!!.data is Consol3 && ptr.next!!.data?.dueBack == rental.dueBack) {
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental)
                //System.out.println("Sort at first console");
                return
            }
        }

        //Mark: -- this should be the first point where ptr's data is of type Consol3
        while (ptr!!.next != null) {
            //This code was ripped from our addGame method.
            if (ptr.next!!.data!!.dueBack!!.after(rental.dueBack)) {
                temp.next = ptr.next
                ptr.next = temp
                //System.out.println("Orange");
                return
            }
            if (ptr.next!!.data!!.dueBack == rental.dueBack) {
                //System.out.println("Sort in main console list");
                sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental)
                return
            }
            ptr = ptr.next //Ok so this works now.
        }


        //System.out.println("Is this getting hit");
        //If none of the conditions are met in the above conditional statements, then temp should be added at the
        // very bottom.
        ptr.next = temp
    }

    private fun sortInstancesOfGameRentalsWithTheSameReturnDate(rental: Rental) {
        if (isConsole(rental)) {
            return
        }
        val temp = Node(rental, null)
        var ptr: Node? = Node(Consol3(), top)
        if (top!!.data!!.dueBack == rental.dueBack && nameIsBefore(top!!.data, rental)) {
            temp.next = top
            top = temp
            return
        }
        while (ptr!!.next != null && ptr.next!!.data!!.dueBack != rental.dueBack) {
            ptr = ptr.next
        }
        while (ptr!!.next != null && ptr.next!!.data!!.dueBack == rental.dueBack) {
            if (ptr.next!!.data!!.dueBack == rental.dueBack &&
                nameIsBefore(ptr.next!!.data, rental)
            ) {
                temp.next = ptr.next
                ptr.next = temp
                return
            }
            ptr = ptr.next
        }
        temp.next = ptr.next
        ptr.next = temp
    }

    private fun sortInstancesOfConsoleRentalsWithTheSameReturnDate2(rental: Rental) {
        if (!isConsole(rental)) {
            return
        }
        val temp = Node(rental, null)
        var ptr: Node? = Node(null, top)

        //In the event that top is a console.
        if (top != null && top!!.data is Consol3) {
            if (top!!.data?.dueBack == rental.dueBack && nameIsBefore(top!!.data, rental)) {
                temp.next = top
                top = temp
                return
            }
        }

        //This should cycle ptr through the linked list until the last instance where ptr is a Game;
        //The first instance where ptr is a console.
        while (ptr!!.next != null && ptr.next!!.data !is Consol3) {
            ptr = ptr.next
        }
        val top2 = ptr.next //first top of type Consol3

        //Check to see if the name is the date is the same in top2
        if (top2 != null && top2.data!!.dueBack == rental.dueBack && nameIsBefore(top2.data, rental)) {
            temp.next = top2
            ptr.next = temp //replaces top2 if so
            return
        }

        //cycles through all instances where ptr dates aren't identical
        while (ptr!!.next != null && ptr.next!!.data!!.dueBack != rental.dueBack) {
            ptr = ptr.next
        }

        //cycles through instances where ptr dates are identical
        while (ptr!!.next != null && ptr.next!!.data!!.dueBack == rental.dueBack) {
            if (ptr.next!!.data!!.dueBack == rental.dueBack &&
                nameIsBefore(ptr.next!!.data, rental)
            ) {
                temp.next = ptr.next
                ptr.next = temp
                return
            }
            ptr = ptr.next
        }
        temp.next = ptr.next
        ptr.next = temp
    }

    private fun remove(index: Int): Rental? {
        //  more code goes here.
        var ptr = top
        val r = get(index)

        //for removing the top
        if (index == 0) {
            top = if (top!!.next != null) {
                top!!.next
            } else {
                null
            }
            return r
        }
        for (i in 0 until index - 1) {
            ptr = ptr!!.next
        }
        if (ptr!!.next != null) {
            if (ptr.next!!.next == null) {
                ptr.next = null
            } else {
                ptr.next = ptr.next!!.next
            }
        }
        return r
    }

    operator fun get(index: Int): Rental? {
        if (top == null) return null

        //  more code goes here.
        var ptr = top
        for (i in 0 until index) {
            ptr = ptr!!.next //in theory, index/ptr shouldn't be able to exceed the list length
        }

        // needs to be changed - (This instruction is here for the method to compile)
        return ptr!!.data
    }

    fun display() {
        var temp = top //I wonder what this does?
        while (temp != null) {
            println(temp.data) //ohhh it's a testing/debug method.
            temp = temp.next //Ok, I guess we'll leave it.
        }
    }

    override fun toString(): String {
        return "LL {" +
                "top=" + top +
                ", size=" + size() +
                '}'
    }

    private fun nameIsBefore(rentalInList: Rental?, newRental: Rental): Boolean {
        val renterNAmeInList = rentalInList!!.nameOfRenter!!.toLowerCase()
        val newRenterName = newRental.nameOfRenter!!.toLowerCase()
        val comparator = renterNAmeInList.compareTo(newRenterName)
        return comparator > 0 //new renter is before renter in the list
    }

    /**
     * These two methods probably would've been more useful if I had used the single
     * method for checking instances of rentals with equal dates. Still ended up using them in the smaller
     * methods though.
     */
    private fun isGame(rental: Rental): Boolean {
        return rental is Game
    }

    private fun isConsole(rental: Rental?): Boolean {
        return rental is Consol3
    }
}