import java.awt.Color
import javax.swing.JFrame
import java.awt.event.ActionListener
import javax.swing.JMenuBar
import javax.swing.JMenu
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.JScrollPane
import java.awt.event.ActionEvent
import javax.swing.JFileChooser
import java.util.GregorianCalendar
import javax.swing.JOptionPane
import java.awt.Dimension

class GUIRentalStore : JFrame(), ActionListener {
    private val menus: JMenuBar
    private val fileMenu: JMenu
    private val actionMenu: JMenu
    private val openSerItem: JMenuItem
    private val exitItem: JMenuItem
    private val saveSerItem: JMenuItem
    private val reserveConsoleItem: JMenuItem
    private val reserveGameItem: JMenuItem
    private val returnedOutItem: JMenuItem
    private val currentRentedScn: JMenuItem
    private val rentedOutItemScn: JMenuItem
    private val panel: JPanel
    private val DList: ListModel
    private val jTable: JTable
    private val scrollList: JScrollPane

    /*****************************************************************
     *
     * A constructor that starts a new GUI1024 for the rental store
     *
     */
    init {
        //adding menu bar and menu items
        menus = JMenuBar()
        fileMenu = JMenu("File")
        actionMenu = JMenu("Action")
        openSerItem = JMenuItem("Open File")
        exitItem = JMenuItem("Exit")
        saveSerItem = JMenuItem("Save File")
        reserveConsoleItem = JMenuItem("Reserve a Console")
        reserveGameItem = JMenuItem("Reserve a Game")
        returnedOutItem = JMenuItem("Return Console or Game")
        currentRentedScn = JMenuItem("Current Store log")
        rentedOutItemScn = JMenuItem("Returned screen")

        //adding items to bar
        fileMenu.add(openSerItem)
        fileMenu.add(saveSerItem)
        fileMenu.addSeparator()
        fileMenu.addSeparator()
        fileMenu.add(exitItem)
        fileMenu.addSeparator()
        fileMenu.add(currentRentedScn)
        fileMenu.add(rentedOutItemScn)
        actionMenu.add(reserveConsoleItem)
        actionMenu.add(reserveGameItem)
        actionMenu.addSeparator()
        actionMenu.add(returnedOutItem)
        menus.add(fileMenu)
        menus.add(actionMenu)
        openSerItem.addActionListener(this)
        saveSerItem.addActionListener(this)
        exitItem.addActionListener(this)
        reserveConsoleItem.addActionListener(this)
        reserveGameItem.addActionListener(this)
        returnedOutItem.addActionListener(this)
        currentRentedScn.addActionListener(this)
        rentedOutItemScn.addActionListener(this)
        jMenuBar = menus
        defaultCloseOperation = EXIT_ON_CLOSE
        panel = JPanel()
        DList = ListModel()
        jTable = JTable(DList)

        jTable.setShowGrid(false)
        jTable.showHorizontalLines = true
        jTable.gridColor = Color.BLUE

        scrollList = JScrollPane(jTable)
        panel.add(scrollList)
        add(panel)
        scrollList.preferredSize = Dimension(800, 600)
        isVisible = true
        setSize(950, 850)
    }

    override fun actionPerformed(e: ActionEvent) {
        val comp = e.source
        returnedOutItem.isEnabled = true
        if (currentRentedScn === comp) DList.setDisplay(ScreenDisplay.CurrentRentalStatus)
        if (rentedOutItemScn === comp) {
            DList.setDisplay(ScreenDisplay.RetendItems)
            returnedOutItem.isEnabled = false
        }
        if (openSerItem === comp) {
            val chooser = JFileChooser()
            val status = chooser.showOpenDialog(null)
            if (status == JFileChooser.APPROVE_OPTION) {
                val filename = chooser.selectedFile.absolutePath
                if (openSerItem === comp) DList.loadDatabase(filename)
            }
        }
        if (saveSerItem === comp) {
            val chooser = JFileChooser()
            val status = chooser.showSaveDialog(null)
            if (status == JFileChooser.APPROVE_OPTION) {
                val filename = chooser.selectedFile.absolutePath
                if (saveSerItem === e.source) DList.saveDatabase(filename)
            }
        }
        if (e.source === exitItem) {
            System.exit(1)
        }
        if (e.source === reserveConsoleItem) {
            val Console = Consol3()
            val dialog = RentConsoleDialog(this, Console)
            if (dialog.closeStatus == RentConsoleDialog.OK) {
                DList.add(Console)
            }
        }
        if (e.source === reserveGameItem) {
            val game = Game()
            val dialog = RentGameDialog(this, game)
            if (dialog.closeStatus == RentGameDialog.OK) {
                DList.add(game)
            }
        }
        if (returnedOutItem === e.source) {
            val index = jTable.selectedRow
            if (index != -1) {
                val dat = GregorianCalendar()
                val unit = DList[index]
                val dialog = ReturnedOnDialog(this, unit)
                JOptionPane.showMessageDialog(
                    null,
                    """  Be sure to thank ${unit.getNameOfRenter()}
 for renting with us and the price is:  ${unit.getCost(unit.getActualDateReturned())} dollars"""
                )
                DList.upDate(index, unit)
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GUIRentalStore()
        }
    }
}