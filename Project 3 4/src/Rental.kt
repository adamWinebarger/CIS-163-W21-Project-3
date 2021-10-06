import java.io.Serializable
import java.util.GregorianCalendar
import java.text.DateFormat
import java.text.SimpleDateFormat

abstract class Rental : Serializable {
    /** The Name of person that is reserving the Rental */
    @JvmField
    var nameOfRenter: String? = null

    /** The date the Rental was rented on  */
    @JvmField
    var rentedOn: GregorianCalendar? = null

    /** The date the Rental was dueBack on  */
    @JvmField
    var dueBack: GregorianCalendar? = null

    /** The actual date the Rental was returned on  */
    @JvmField
    var actualDateReturned: GregorianCalendar? = null


    abstract fun getCost(checkOut: GregorianCalendar?): Double

    constructor(
        nameOfRenter: String?,
        rentedOn: GregorianCalendar?,
        dueBack: GregorianCalendar?,
        actualDateReturned: GregorianCalendar?
    ) {
        this.nameOfRenter = nameOfRenter
        this.rentedOn = rentedOn
        this.dueBack = dueBack
        this.actualDateReturned = actualDateReturned
    }

    constructor()

    // following code used for debugging only
    // IntelliJ using the toString for displaying in debugger.
    override fun toString(): String {
        val formatter: DateFormat = SimpleDateFormat("MM/dd/yyyy")
        val rentedOnStr: String = if (rentedOn == null) "" else formatter.format(rentedOn!!.time)
        val estdueBackStr: String = if (dueBack == null) "" else formatter.format(dueBack!!.time)
        val acutaulDateReturnedStr: String = if (actualDateReturned == null) "" else formatter.format(
            actualDateReturned!!.time
        )
        return """
RentUnit{
guestName='$nameOfRenter 
rentedOn =$rentedOnStr
dueBack =$estdueBackStr
actualDateReturned =$acutaulDateReturnedStr}"""
    }

    companion object {
        /** What is the purpose of this variable (search google)  */
        private const val serialVersionUID = 1L
    }
}