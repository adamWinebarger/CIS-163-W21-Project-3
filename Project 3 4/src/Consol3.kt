
import java.util.GregorianCalendar
import java.util.Calendar

class Consol3 : Rental {
    /** Represents the type of console player, see enum type.  */
    var consoleType: ConsoleTypes? = null

    constructor(
        nameOfRenter: String?,
        rentedOn: GregorianCalendar?,
        dueBack: GregorianCalendar?,
        actualDateReturned: GregorianCalendar?,
        consoleType: ConsoleTypes?
    ) : super(nameOfRenter, rentedOn, dueBack, actualDateReturned) {
        this.consoleType = consoleType
    }

    constructor()

    override fun getCost(checkOut: GregorianCalendar?): Double {

        // Do not use this approach.
        //String dateBeforeString = "2017-05-24";
        //	String dateAfterString = "2017-07-29";
        //
        //	//Parsing the date
        //	LocalDate dateBefore = LocalDate.parse(dateBeforeString);
        //	LocalDate dateAfter = LocalDate.parse(dateAfterString);
        //
        //	//calculating number of days in between
        //	long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        //
        //	//displaying the number of days
        //	System.out.println(noOfDaysBetween);
        //
        var gTemp = GregorianCalendar()
        var cost = 5.0
        //        Date d = dueBack.getTime();
        //        gTemp.setTime(d);
        gTemp = checkOut!!.clone() as GregorianCalendar //  gTemp = dueBack;  does not work!!
        for (days in 0..6) gTemp.add(Calendar.DATE, -1)
        while (gTemp.after(rentedOn)) {
            if (consoleType === ConsoleTypes.NintendoSwitch ||
                consoleType === ConsoleTypes.PlayStation4Pro ||
                consoleType === ConsoleTypes.SegaGenesisMini
            ) cost += 1.5
            if (consoleType === ConsoleTypes.PlayStation4 ||
                consoleType === ConsoleTypes.XBoxOneS
            ) cost += 1.0
            gTemp.add(Calendar.DATE, -1)
        }
        return cost
    }

    override fun toString(): String {
        return "Console{" +
                " consoleType=" + consoleType + " " + super.toString() +
                '}'
    }
}