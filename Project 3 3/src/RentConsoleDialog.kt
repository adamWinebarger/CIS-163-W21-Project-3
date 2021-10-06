import javax.swing.JFrame
import javax.swing.JDialog
import java.awt.event.ActionListener
import javax.swing.JTextField
import javax.swing.JComboBox
import javax.swing.JButton
import java.awt.event.ActionEvent
import java.text.SimpleDateFormat
import javax.swing.JOptionPane
import javax.swing.WindowConstants
import javax.swing.JPanel
import java.awt.GridLayout
import javax.swing.JLabel
import java.awt.BorderLayout
import java.text.ParseException
import java.util.*

class RentConsoleDialog(parent: JFrame, private val console: Consol3) : JDialog(parent, true), ActionListener {
    private val txtRenterName: JTextField
    private val txtDateRentedOn: JTextField
    private val txtDateDueDate: JTextField
    private val comBoxConsoleType: JComboBox<ConsoleTypes>
    private val okButton: JButton
    private val cancelButton: JButton

    companion object {
        const val OK = 0
        const val CANCEL = 1
    }

    var closeStatus : Int
        private set //ok... interesting. So since variables default to public,
        //this just makes it so it can't be modified from outside the class


    /*********************************************************
     * Instantiate a Custom Dialog as 'modal' and wait for the
     * user to provide data and click on a button.
     *
     * @param parent reference to the JFrame application
     * @param console an instantiated object to be filled with data
     */
    init {
        // call parent and create a 'modal' dialog
        title = "Console dialog box"
        closeStatus = CANCEL
        setSize(500, 200)

        // prevent user from closing window
        defaultCloseOperation = DO_NOTHING_ON_CLOSE

        // instantiate and display two text fields
        txtRenterName = JTextField("Roger", 30)
        txtDateRentedOn = JTextField(25)
        txtDateDueDate = JTextField(25)
        comBoxConsoleType = JComboBox(ConsoleTypes.values())
        val currentDate = Calendar.getInstance()
        val formatter = SimpleDateFormat("MM/dd/yyyy")
        val dateNow = formatter.format(currentDate.time)
        currentDate.add(Calendar.DATE, 1)
        val datetomorrow = formatter.format(currentDate.time)
        txtDateRentedOn.text = dateNow
        txtDateDueDate.text = datetomorrow
        val textPanel = JPanel()
        textPanel.layout = GridLayout(4, 2)
        textPanel.add(JLabel("Name of Renter: "))
        textPanel.add(txtRenterName)
        textPanel.add(JLabel("Date Rented On "))
        textPanel.add(txtDateRentedOn)
        textPanel.add(JLabel("Date Due (est.): "))
        textPanel.add(txtDateDueDate)
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
     * Return a String to let the caller know which button
     * was clicked
     *
     * @return an int representing the option OK or CANCEL
     */

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
                var gregTemp = GregorianCalendar()
                d1 = df.parse(txtDateRentedOn.text)
                gregTemp.time = d1
                console.setRentedOn(gregTemp)
                gregTemp = GregorianCalendar()
                d2 = df.parse(txtDateDueDate.text)
                gregTemp.time = d2
                console.setDueBack(gregTemp)
            } catch (e1: ParseException) {
//                  Do some thing good, what that is, I am not sure.
            }
            console.setNameOfRenter(txtRenterName.text)
            console.consoleType = comBoxConsoleType.selectedItem as ConsoleTypes
            if (comBoxConsoleType.selectedItem as ConsoleTypes == ConsoleTypes.NoSelection) {
                JOptionPane.showMessageDialog(null, "Select Console.")
                closeStatus = CANCEL
            }
        }

        // make the dialog disappear
        dispose()
    }
}