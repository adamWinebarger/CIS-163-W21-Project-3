import java.util.GregorianCalendar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class Game : Rental {
    /** represents the name of the game  */
    var nameGame: String? = null

    /**
     * Represents the player the person rented to play the game,
     * null if no console was rented.
     */
    var console: Consol3? = null

    constructor(
        nameOfRenter: String?,
        rentedOn: GregorianCalendar?,
        dueBack: GregorianCalendar?,
        actualDateReturned: GregorianCalendar?,
        nameGame: String?,
        console: Consol3?
    ) : super(nameOfRenter, rentedOn, dueBack, actualDateReturned) {
        this.nameGame = nameGame
        this.console = console
    }

    constructor()


    override fun getCost(checkOut: GregorianCalendar?): Double {

        // Do not use this approach
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
        var cost = 0.0
        if (console != null) {
            val temp = Consol3(nameOfRenter, rentedOn, this.dueBack, actualDateReturned, console!!.consoleType)
            cost += temp.getCost(checkOut)
        }
        var gTemp = GregorianCalendar()
        cost += 3.0

        //        Date d = dueBack.getTime();
        //        gTemp.setTime(d);
        gTemp = checkOut!!.clone() as GregorianCalendar //       gTemp = dueBack;  does not work!!
        val formatter: DateFormat = SimpleDateFormat("MM/dd/yyyy")
        //        System.out.println(formatter.format(gTemp.getTime()));
        for (days in 0..6) gTemp.add(Calendar.DATE, -1)

//        System.out.println(formatter.format(gTemp.getTime()));
//        System.out.println(formatter.format(rentedOn.getTime()));
        while (gTemp.after(rentedOn)) {
            cost += .5
            gTemp.add(Calendar.DATE, -1)
        }
        return cost
    }

    override fun toString(): String {
        return "Game{" +
                "name='" + nameGame + '\'' +
                ", player=" + console + super.toString() +
                '}'
    }
}