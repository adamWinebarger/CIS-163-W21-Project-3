import javax.swing.JFrame
import javax.swing.JDialog
import java.awt.event.ActionListener
import javax.swing.JTextField
import javax.swing.JComboBox
import javax.swing.JButton
import java.awt.event.ActionEvent
import java.text.SimpleDateFormat
import javax.swing.WindowConstants
import javax.swing.JPanel
import java.awt.GridLayout
import javax.swing.JLabel
import java.awt.BorderLayout
import java.text.ParseException
import java.util.*

class RentGameDialog(parent: JFrame?, private val game: Game) : JDialog(parent, true), ActionListener {
    private val txtRentedName: JTextField
    private val txtDateRentedOn: JTextField
    private val txtDateDueDate: JTextField
    private val txtNameOfGame: JTextField
    private val comBoxConsoleType: JComboBox<ConsoleTypes>
    private val okButton: JButton
    private val cancelButton: JButton

    //apparently, doing our public static final constants like this is less memory consumptive than simply declaring 'val'
    companion object {
        const val OK = 0
        const val CANCEL = 1
    }

    /**
     * declaring closeStatus this way gives us a built-in public getter, but makes
     * the value unmodifiable from outside the class
     */
    var closeStatus: Int
        private set

    /*********************************************************
     * Instantiate a Custom Dialog as 'modal' and wait for the
     * user to provide data and click on a button.
     *
     * @param parent reference to the JFrame application
     * @param game an instantiated object to be filled with data
     */
    init {
        // call parent and create a 'modal' dialog
        title = "Game dialog box"
        closeStatus = CANCEL
        setSize(400, 200)

        // prevent user from closing window
        defaultCloseOperation = DO_NOTHING_ON_CLOSE

        // instantiate and display two text fields
        txtRentedName = JTextField("Judy", 30)
        txtDateRentedOn = JTextField(15)
        txtDateDueDate = JTextField(15)
        txtNameOfGame = JTextField("Game1", 15)
        comBoxConsoleType = JComboBox(ConsoleTypes.values())
        val currentDate = Calendar.getInstance()
        val formatter = SimpleDateFormat("MM/dd/yyyy") //format it as per your requirement
        val dateNow = formatter.format(currentDate.time)
        currentDate.add(Calendar.DATE, 1)
        val datetomorrow = formatter.format(currentDate.time)
        txtDateRentedOn.text = dateNow
        txtDateDueDate.text = datetomorrow
        val textPanel = JPanel()
        textPanel.layout = GridLayout(6, 2)
        textPanel.add(JLabel(""))
        textPanel.add(JLabel(""))
        textPanel.add(JLabel("Name of Renter: "))
        textPanel.add(txtRentedName)
        textPanel.add(JLabel("Date rented on: "))
        textPanel.add(txtDateRentedOn)
        textPanel.add(JLabel("Due date (est.): "))
        textPanel.add(txtDateDueDate)
        textPanel.add(JLabel("Name of the Gamed"))
        textPanel.add(txtNameOfGame)
        textPanel.add(JLabel("ConsoleType"))
        textPanel.add(comBoxConsoleType)
        contentPane.add(textPanel, BorderLayout.CENTER)

        // Instantiate and display two buttons
        okButton = JButton("OK")
        cancelButton = JButton("Cancel")
        val buttonPanel = JPanel()
        buttonPanel.add(okButton)
        buttonPanel.add(cancelButton)
        contentPane.add(buttonPanel, BorderLayout.SOUTH)
        okButton.addActionListener(this)
        cancelButton.addActionListener(this)
        isVisible = true
    }



    /**************************************************************
     * Respond to either button clicks
     * @param e the action event that was just fired
     */
    override fun actionPerformed(e: ActionEvent) {
        val button = e.source as JButton

        // if OK clicked the fill the object
        if (button === okButton) {
            // save the information in the object
            closeStatus = OK
            val df = SimpleDateFormat("MM/dd/yyyy")
            var d1: Date? = null
            var d2: Date? = null
            try {
                val rentonTemp = GregorianCalendar()
                d1 = df.parse(txtDateRentedOn.text)
                rentonTemp.time = d1
                game.setRentedOn(rentonTemp)
                val dueDateTemp = GregorianCalendar()
                d2 = df.parse(txtDateDueDate.text)
                dueDateTemp.time = d2
                game.setDueBack(dueDateTemp)
                game.setNameOfRenter(txtRentedName.text)
                game.nameGame = txtNameOfGame.text
                val type = comBoxConsoleType.selectedItem as ConsoleTypes
                var temp: Consol3? = null
                if (type != ConsoleTypes.NoSelection) temp = Consol3(
                    txtNameOfGame.text,
                    rentonTemp, dueDateTemp, null, type
                )
                game.console = temp
            } catch (e1: ParseException) {
//                  Do some thing good, what that is, I am not sure.
            }
        }

        // make the dialog disappear
        dispose()
    }

}