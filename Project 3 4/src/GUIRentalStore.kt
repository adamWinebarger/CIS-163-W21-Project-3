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
    private val menus: JMenuBar = JMenuBar()
    private val fileMenu: JMenu = JMenu("File")
    private val actionMenu: JMenu = JMenu("Action")
    private val openSerItem: JMenuItem = JMenuItem("Open File")
    private val exitItem: JMenuItem = JMenuItem("Exit")
    private val saveSerItem: JMenuItem = JMenuItem("Save File")
    private val reserveConsoleItem: JMenuItem = JMenuItem("Reserve a Console")
    private val reserveGameItem: JMenuItem = JMenuItem("Reserve a Game")
    private val returnedOutItem: JMenuItem = JMenuItem("Return Console or Game")
    private val currentRentedScn: JMenuItem = JMenuItem("Current Store log")
    private val rentedOutItemScn: JMenuItem = JMenuItem("Returned screen")
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
            DList.setDisplay(ScreenDisplay.RentedItems)
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
                val dialog = unit?.let { ReturnedOnDialog(this, it) }
                if (unit != null) {
                    JOptionPane.showMessageDialog(
                        null,
                        """  Be sure to thank ${unit.nameOfRenter}
             for renting with us and the price is:  ${unit.getCost(unit.actualDateReturned)} dollars"""
                    )
                }
                DList.upDate()
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