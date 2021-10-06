import javax.swing.JFrame
import javax.swing.JDialog
import java.awt.event.ActionListener
import javax.swing.JTextField
import javax.swing.JButton
import java.awt.event.ActionEvent
import java.text.SimpleDateFormat
import javax.swing.WindowConstants
import java.text.DateFormat
import javax.swing.JPanel
import java.awt.GridLayout
import javax.swing.JLabel
import java.awt.BorderLayout
import java.text.ParseException
import java.util.*

class ReturnedOnDialog(parent: JFrame?, private val unit: Rental) : JDialog(parent, true), ActionListener {
    private val txtDate: JTextField
    private val okButton: JButton
    private val cancelButton: JButton

    companion object {
        const val OK = 0
        const val CANCEL = 1
    }

    /**************************************************************
     * Return a String to let the caller know which button
     * was clicked
     *
     * @return an int representing the option OK or CANCEL
     */
    var closeStatus: Int
        private set

    /*********************************************************
     * Instantiate a Custom Dialog as 'modal' and wait for the
     * user to provide data and click on a button.
     *
     * @param parent reference to the JFrame application
     * @param unit an instantiated object to be filled with data
     */
    init {
        // call parent and create a 'modal' dialog
        title = "Returned dialog box"
        closeStatus = CANCEL
        setSize(300, 100)
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        val dateFormat: DateFormat = SimpleDateFormat("MM/dd/yyyy")
        txtDate = JTextField(dateFormat.format(unit.dueBack?.time), 30)
        val textPanel = JPanel()
        textPanel.layout = GridLayout(1, 2)
        textPanel.add(JLabel("Returned Date: "))
        textPanel.add(txtDate)
        contentPane.add(textPanel, BorderLayout.CENTER)
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
        if (button === okButton) {
            // save the information in the object
            closeStatus = OK
            val df = SimpleDateFormat("MM/dd/yyyy")
            val gTemp = GregorianCalendar()
            var d: Date? = null
            try {
                d = df.parse(txtDate.text)
                gTemp.time = d
                unit.actualDateReturned = gTemp
            } catch (e1: ParseException) {
            }
        }

        // make the dialog disappear
        dispose()
    }
}