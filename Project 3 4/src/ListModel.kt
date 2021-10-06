import javax.swing.table.AbstractTableModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.lang.RuntimeException
import java.lang.IllegalArgumentException
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.IOException
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.lang.Exception
import java.text.ParseException
import java.util.*
import javax.swing.JOptionPane.showMessageDialog

class ListModel : AbstractTableModel() {
    /**
     * holds all the rentals
     */
    private var listOfRentals: SingleLinkedList

    /**
     * holds only the rentals that are to be displayed
     */
    private var filteredListRentals: SingleLinkedList

    /**
     * current screen being displayed
     */
    private var display = ScreenDisplay.CurrentRentalStatus
    private val columnNamesCurrentRentals = arrayOf(
        "Renter\'s Name", "Est. Cost",
        "Rented On", "Due Date ", "Console", "Name of the Game"
    )
    private val columnNamesforRented = arrayOf(
        "Renter\'s Name", "rented On Date",
        "Due Date", "Actual date returned ", "Est. Cost", " Real Cost"
    )
    private val formatter: DateFormat = SimpleDateFormat("MM/dd/yyyy")
    fun setDisplay(selected: ScreenDisplay) {
        display = selected
        UpdateScreen()
    }

    init {
        display = ScreenDisplay.CurrentRentalStatus
        listOfRentals = SingleLinkedList()
        filteredListRentals = SingleLinkedList()
        UpdateScreen()
        createList()
    }

    private fun UpdateScreen() {
        filteredListRentals = when (display) {
            ScreenDisplay.CurrentRentalStatus -> actualRentals
            ScreenDisplay.RentedItems -> returnedRentals
            else -> throw RuntimeException("upDate is in undefined state: $display")
        }
        fireTableStructureChanged()
    }

    private val returnedRentals: SingleLinkedList
        get() {
            val temp = SingleLinkedList()
            for (i in 0 until listOfRentals.size()) if (listOfRentals[i]!!.actualDateReturned != null) temp.add(
                listOfRentals[i]!!
            )
            return temp
        }
    private val actualRentals: SingleLinkedList
        get() {
            val temp = SingleLinkedList()
            for (i in 0 until listOfRentals.size()) if (listOfRentals[i]!!.actualDateReturned == null) temp.add(
                listOfRentals[i]!!
            )
            return temp
        }

    override fun getColumnName(col: Int): String {
        return when (display) {
            ScreenDisplay.CurrentRentalStatus -> columnNamesCurrentRentals[col]
            ScreenDisplay.RentedItems -> columnNamesforRented[col]
            else -> throw RuntimeException("Undefined state for Col Names: $display")
        }
    }

    override fun getColumnCount(): Int {
        return when (display) {
            ScreenDisplay.CurrentRentalStatus -> columnNamesCurrentRentals.size
            ScreenDisplay.RentedItems -> columnNamesforRented.size
            else -> throw IllegalArgumentException()
        }
    }

    override fun getRowCount(): Int {
        return filteredListRentals.size() // returns number of items in the arraylist
    }

    override fun getValueAt(row: Int, col: Int): Any {
        return when (display) {
            ScreenDisplay.CurrentRentalStatus -> currentParkScreen(row, col)!!
            ScreenDisplay.RentedItems -> rentedOutScreen(row, col)!!
            else -> throw IllegalArgumentException()
        }
    }

    private fun currentParkScreen(row: Int, col: Int): Any? {
        return when (col) {
            0 -> filteredListRentals[row]!!.nameOfRenter
            1 -> filteredListRentals[row]!!.getCost(filteredListRentals[row]!!.dueBack)
            2 -> formatter.format(filteredListRentals[row]!!.rentedOn!!.time)
            3 -> {
                if (filteredListRentals[row]!!.dueBack == null) "-" else formatter.format(
                    filteredListRentals[row]!!.dueBack!!.time
                )
            }
            4 -> {
                if (filteredListRentals[row] is Consol3) return (filteredListRentals[row] as Consol3?)!!.consoleType else {
                    if (filteredListRentals[row] is Game) return if ((filteredListRentals[row] as Game?)!!.console != null) (filteredListRentals[row] as Game?)!!.console!!.consoleType else ""
                }
                if (filteredListRentals[row] is Game) (filteredListRentals[row] as Game?)!!.nameGame else ""
            }
            5 -> if (filteredListRentals[row] is Game) (filteredListRentals[row] as Game?)!!.nameGame else ""
            else -> throw RuntimeException("Row,col out of range: $row $col")
        }
    }

    private fun rentedOutScreen(row: Int, col: Int): Any? {
        return when (col) {
            0 -> filteredListRentals[row]!!.nameOfRenter
            1 -> formatter.format(filteredListRentals[row]!!.rentedOn!!.time)
            2 -> formatter.format(filteredListRentals[row]!!.dueBack!!.time)
            3 -> formatter.format(filteredListRentals[row]!!.actualDateReturned!!.time)
            4 -> filteredListRentals[row]!!.getCost(filteredListRentals[row]!!.dueBack)
            5 -> filteredListRentals[row]!!.getCost(
                filteredListRentals[row]!!.actualDateReturned
            )
            else -> throw RuntimeException("Row,col out of range: $row $col")
        }
    }

    fun add(a: Rental?) {
        listOfRentals.add(a!!)
        UpdateScreen()
    }

    operator fun get(i: Int): Rental? {
        return filteredListRentals[i]
    }

    fun upDate() {
        UpdateScreen()
    }

    fun saveDatabase(filename: String?) {
        try {
            val fos = FileOutputStream(filename as String)
            val os = ObjectOutputStream(fos)
            os.writeObject(listOfRentals)
            os.close()
        } catch (ex: IOException) {
            showMessageDialog(null, "Saving problem! $display")
            throw RuntimeException("Saving problem! $display")

        }
    }

    fun loadDatabase(filename: String?) {
        listOfRentals.clear()
        try {
            val fis = FileInputStream(filename as String)
            val `is` = ObjectInputStream(fis)
            listOfRentals = `is`.readObject() as SingleLinkedList
            UpdateScreen()
            `is`.close()
        } catch (ex: Exception) {
            showMessageDialog(null, "Loading problem: $display")
            throw RuntimeException("Loading problem: $display")
        }
    }

    private fun createList() {
        val df = SimpleDateFormat("MM/dd/yyyy")
        val g1 = GregorianCalendar()
        val g2 = GregorianCalendar()
        val g3 = GregorianCalendar()
        val g4 = GregorianCalendar()
        val g5 = GregorianCalendar()
        val g6 = GregorianCalendar()
        val g7 = GregorianCalendar()
        val g8 = GregorianCalendar()
        try {
            val d1 = df.parse("1/20/2020")
            g1.time = d1
            val d2 = df.parse("12/22/2020")
            g2.time = d2
            val d3 = df.parse("12/20/2019")
            g3.time = d3
            val d4 = df.parse("7/02/2020")
            g4.time = d4
            val d5 = df.parse("1/20/2010")
            g5.time = d5
            val d6 = df.parse("9/29/2020")
            g6.time = d6
            val d7 = df.parse("7/25/2020")
            g7.time = d7
            val d8 = df.parse("7/29/2020")
            g8.time = d8
            val console1 = Consol3("console1", g4, g6, null, ConsoleTypes.PlayStation4)
            val console2 = Consol3("console2", g5, g3, null, ConsoleTypes.PlayStation4)
            val console3 = Consol3("console3", g4, g8, null, ConsoleTypes.SegaGenesisMini)
            val console4 = Consol3("console4", g4, g7, null, ConsoleTypes.SegaGenesisMini)
            val console5 = Consol3("console5", g5, g4, g3, ConsoleTypes.XBoxOneS)
            val game1 = Game(
                "Joe", g3, g2, null, "title1",
                Consol3("Joe", g3, g2, null, ConsoleTypes.PlayStation4)
            )
            val game2 = Game(
                "Jim", g3, g1, null, "title2",
                Consol3("Jim", g3, g1, null, ConsoleTypes.PlayStation4)
            )
            val game3 = Game(
                "Hambone", g5, g3, null, "title2",
                Consol3("Hambone", g5, g3, null, ConsoleTypes.SegaGenesisMini)
            )
            val game4 = Game("FlippityFlop", g4, g8, null, "title2", null)
            val game5 = Game(
                "Jon Tompson", g3, g1, g1, "title2",
                Consol3("John Tompson", g3, g1, g1, ConsoleTypes.XBoxOneS)
            )
            val game6 = Game(
                "k---", g4, g7, null, "title1",
                Consol3("k---", g4, g7, null, ConsoleTypes.NintendoSwitch)
            )
            val game7 = Game(
                "Person5", g4, g8, null, "title1",
                Consol3("Person5", g4, g8, null, ConsoleTypes.NintendoSwitch)
            )
            add(game1)
            add(game4)
            add(game5)
            add(game2)
            add(game3) //This is the one???
            add(game6)
            add(game7)
            add(console1)
            add(console2)
            add(console5)
            add(console3)
            add(console4)


            //These commented out code is to help with debugging for step 2 and Step 3

//            add(game1);
//            add(game4);
//            add(console1);
//            listOfRentals.remove(0);
//            add(console4);
//            add(game5);
//            add(game2);
//            listOfRentals.remove(listOfRentals.size()-1);
//            listOfRentals.remove(2);
//            add(game3);
//            add(console5);
//            add(game6);
//            add(console3);
//            listOfRentals.remove(listOfRentals.size()-1);
//            add(game7);
//            add(console2);
//            for (int i = 0; i < listOfRentals.size(); i++)
//                System.out.println(listOfRentals.get(i).toString());


            // create a bunch of them.
            var count = 0
            val rand = Random(13)
            var guest: String?
            while (count < 20) {
                var date = df.parse("7/" + (rand.nextInt(10) + 2) + "/2020")
                val g = GregorianCalendar()
                g.time = date
                if (rand.nextBoolean()) {
                    guest = "Game" + rand.nextInt(5)
                    val game: Game = if (count % 2 == 0) Game(
                        guest, g4, g, null, "title2",
                        Consol3(guest, g1, g, null, getOneRandom(rand))
                    ) else Game(guest, g4, g, null, "title2", null)
                    add(game)
                } else {
                    guest = "Console" + rand.nextInt(5)
                    date = df.parse("7/" + (rand.nextInt(20) + 2) + "/2020")
                    g.time = date
                    val console = Consol3(guest, g4, g, null, getOneRandom(rand))
                    add(console)
                }
                count++
            }
            println(listOfRentals.size())
        } catch (e: ParseException) {
            throw RuntimeException("Error in testing, creation of list")
        }
    }

    private fun getOneRandom(rand: Random): ConsoleTypes {
        return when (rand.nextInt(ConsoleTypes.values().size - 1)) {
            0 -> ConsoleTypes.PlayStation4
            1 -> ConsoleTypes.XBoxOneS
            2 -> ConsoleTypes.PlayStation4Pro
            3 -> ConsoleTypes.NintendoSwitch
            else -> ConsoleTypes.SegaGenesisMini
        }
    }
}