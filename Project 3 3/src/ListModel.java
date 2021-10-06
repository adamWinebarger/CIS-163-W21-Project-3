import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;


public class ListModel extends AbstractTableModel {

    /**
     * holds all the rentals
     */
    private SingleLinkedList listOfRentals;

    /**
     * holds only the rentals that are to be displayed
     */
    private SingleLinkedList filteredListRentals;

    /**
     * current screen being displayed
     */
    private ScreenDisplay display = ScreenDisplay.CurrentRentalStatus;

    private final String[] columnNamesCurrentRentals = {"Renter\'s Name", "Est. Cost",
            "Rented On", "Due Date ", "Console", "Name of the Game"};

    private final String[] columnNamesforRented = {"Renter\'s Name", "rented On Date",
            "Due Date", "Actual date returned ", "Est. Cost", " Real Cost"};

    private final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public ListModel() {
        display = ScreenDisplay.CurrentRentalStatus;
        listOfRentals = new SingleLinkedList();
        filteredListRentals = new SingleLinkedList();

        UpdateScreen();
        createList();
    }

    public void setDisplay(ScreenDisplay selected) {
        display = selected;
        UpdateScreen();
    }

    private void UpdateScreen() {

        switch (display) {
            case CurrentRentalStatus -> filteredListRentals = getActualRentals();
            case RetendItems -> filteredListRentals = getReturnedRentals();
            default -> throw new RuntimeException("upDate is in undefined state: " + display);
        }
        fireTableStructureChanged();
    }

    private SingleLinkedList getReturnedRentals() {
        SingleLinkedList temp = new SingleLinkedList();
        for (int i = 0; i < listOfRentals.size(); i++)
            if (listOfRentals.get(i).getActualDateReturned() != null)
                temp.add(listOfRentals.get(i));
        return temp;
    }

    private SingleLinkedList getActualRentals() {
        SingleLinkedList temp = new SingleLinkedList();
        for (int i = 0; i < listOfRentals.size(); i++)
            if (listOfRentals.get(i).getActualDateReturned() == null)
                temp.add(listOfRentals.get(i));
        return temp;
    }
    @Override
    public String getColumnName(int col) {
        return switch (display) {
            case CurrentRentalStatus -> columnNamesCurrentRentals[col];
            case RetendItems -> columnNamesforRented[col];
            default -> throw new RuntimeException("Undefined state for Col Names: " + display);
        };
    }

    @Override
    public int getColumnCount() {
        return switch (display) {
            case CurrentRentalStatus -> columnNamesCurrentRentals.length;
            case RetendItems -> columnNamesforRented.length;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public int getRowCount() {
        return filteredListRentals.size();     // returns number of items in the arraylist
    }

    @Override
    public Object getValueAt(int row, int col) {
        return switch (display) {
            case CurrentRentalStatus -> currentParkScreen(row, col);
            case RetendItems -> rentedOutScreen(row, col);
            default -> throw new IllegalArgumentException();
        };
    }

    private Object currentParkScreen(int row, int col) {
        switch (col) {
            case 0:
                return (filteredListRentals.get(row).nameOfRenter);

            case 1:
                return (filteredListRentals.get(row).getCost(filteredListRentals.
                        get(row).dueBack));

            case 2:
                return (formatter.format(filteredListRentals.get(row).rentedOn.getTime()));

            case 3:
                if (filteredListRentals.get(row).dueBack == null)
                    return "-";

                return (formatter.format(filteredListRentals.get(row).dueBack.getTime()));

            case 4:
                if (filteredListRentals.get(row) instanceof Consol3)
                    return (((Consol3) filteredListRentals.get(row)).getConsoleType());
                else {
                    if (filteredListRentals.get(row) instanceof Game)
                        if (((Game) filteredListRentals.get(row)).getConsole() != null)
                            return ((Game) filteredListRentals.get(row)).getConsole().getConsoleType();
                        else
                            return "";
                }

            case 5:
                if (filteredListRentals.get(row) instanceof Game)
                    return (((Game) filteredListRentals.get(row)).getNameGame());
                else
                    return "";
            default:
                throw new RuntimeException("Row,col out of range: " + row + " " + col);
        }
    }

    private Object rentedOutScreen(int row, int col) {
        return switch (col) {
            case 0 -> (filteredListRentals.get(row).nameOfRenter);
            case 1 -> (formatter.format(filteredListRentals.get(row).rentedOn.
                    getTime()));
            case 2 -> (formatter.format(filteredListRentals.get(row).dueBack.
                    getTime()));
            case 3 -> (formatter.format(filteredListRentals.get(row).
                    actualDateReturned.getTime()));
            case 4 -> (filteredListRentals.
                    get(row).getCost(filteredListRentals.get(row).dueBack));
            case 5 -> (filteredListRentals.
                    get(row).getCost(filteredListRentals.get(row).
                    actualDateReturned
            ));
            default -> throw new RuntimeException("Row,col out of range: " + row + " " + col);
        };
    }

    public void add(Rental a) {
        listOfRentals.add(a);
        UpdateScreen();
    }

    public Rental get(int i) {
        return filteredListRentals.get(i);
    }

    public void upDate(int index, Rental unit) {
        UpdateScreen();
    }

    public void saveDatabase(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(listOfRentals);
//            Node index = filteredListRentals.getTop();
//            while (index != null) {
//                if (index.getData() instanceof Game) {
//                    Game g = (Game) index.getData();
//                    String gs = formattedGameString(g);
//                    os.writeObject(gs);
//                } else if (index.getData() instanceof Consol3) {
//                    Consol3 c = (Consol3) index.getData();
//                    String cs = formattedConsoleString(c);
//                    os.writeObject(cs);
//                }
//                index = index.getNext();
//            }

            os.close();
        } catch (IOException ex) {
            throw new RuntimeException("Saving problem! " + display);
        }
    }

    public void loadDatabase(String filename) {
        listOfRentals.clear();

        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            listOfRentals = (SingleLinkedList) is.readObject();
            UpdateScreen();
            is.close();
        } catch (Exception ex) {
            throw new RuntimeException("Loading problem: " + display);

        }
    }


    public void createList() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar g1 = new GregorianCalendar();
        GregorianCalendar g2 = new GregorianCalendar();
        GregorianCalendar g3 = new GregorianCalendar();
        GregorianCalendar g4 = new GregorianCalendar();
        GregorianCalendar g5 = new GregorianCalendar();
        GregorianCalendar g6 = new GregorianCalendar();
        GregorianCalendar g7 = new GregorianCalendar();
        GregorianCalendar g8 = new GregorianCalendar();

        try {
            Date d1 = df.parse("1/20/2020");
            g1.setTime(d1);
            Date d2 = df.parse("12/22/2020");
            g2.setTime(d2);
            Date d3 = df.parse("12/20/2019");
            g3.setTime(d3);
            Date d4 = df.parse("7/02/2020");
            g4.setTime(d4);
            Date d5 = df.parse("1/20/2010");
            g5.setTime(d5);
            Date d6 = df.parse("9/29/2020");
            g6.setTime(d6);
            Date d7 = df.parse("7/25/2020");
            g7.setTime(d7);
            Date d8 = df.parse("7/29/2020");
            g8.setTime(d8);

            Consol3 console1 = new Consol3("console1", g4, g6, null, ConsoleTypes.PlayStation4);
            Consol3 console2 = new Consol3("console2", g5, g3, null, ConsoleTypes.PlayStation4);
            Consol3 console3 = new Consol3("console3", g4, g8, null, ConsoleTypes.SegaGenesisMini);
            Consol3 console4 = new Consol3("console4", g4, g7, null, ConsoleTypes.SegaGenesisMini);
            Consol3 console5 = new Consol3("console5", g5, g4, g3, ConsoleTypes.XBoxOneS);

            Game game1 = new Game("Joe", g3, g2, null, "title1",
                    new Consol3("Joe", g3, g2, null, ConsoleTypes.PlayStation4));
            Game game2 = new Game("Jim", g3, g1, null, "title2",
                    new Consol3("Jim", g3, g1, null, ConsoleTypes.PlayStation4));
            Game game3 = new Game("Hambone", g5, g3, null, "title2",
                    new Consol3("Hambone", g5, g3, null, ConsoleTypes.SegaGenesisMini));
            Game game4 = new Game("FlippityFlop", g4, g8, null, "title2", null);
            Game game5 = new Game("Jon Tompson", g3, g1, g1, "title2",
                    new Consol3("John Tompson", g3, g1, g1, ConsoleTypes.XBoxOneS));
            Game game6 = new Game("k---", g4, g7, null, "title1",
                    new Consol3("k---", g4, g7, null, ConsoleTypes.NintendoSwich));
            Game game7 = new Game("Person5", g4, g8, null, "title1",
                    new Consol3("Person5", g4, g8, null, ConsoleTypes.NintendoSwich));

            add(game1);
            add(game4);
            add(game5);
            add(game2);
            add(game3); //This is the one???
            add(game6);
            add(game7);

            add(console1);
            add(console2);
            add(console5);
            add(console3);
            add(console4);


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
            int count = 0;
            Random rand = new Random(13);
            String guest = null;

            while (count < 20) {
                Date date = df.parse("7/" + (rand.nextInt(10) + 2) + "/2020");
                GregorianCalendar g = new GregorianCalendar();
                g.setTime(date);
                if (rand.nextBoolean()) {
                    guest = "Game" + rand.nextInt(5);
                    Game game;
                    if (count % 2 == 0)
                        game = new Game(guest, g4, g, null, "title2",
                                new Consol3(guest, g1, g, null, getOneRandom(rand)));
                    else
                        game = new Game(guest, g4, g, null, "title2", null);
                    add(game);

                } else {
                    guest = "Console" + rand.nextInt(5);
                    date = df.parse("7/" + (rand.nextInt(20) + 2) + "/2020");
                    g.setTime(date);
                    Consol3 console = new Consol3(guest, g4, g, null, getOneRandom(rand));

                    add(console);


                }

                count++;
            }
            System.out.println(listOfRentals.size());


        } catch (ParseException e) {
            throw new RuntimeException("Error in testing, creation of list");
        }
    }

    public ConsoleTypes getOneRandom(Random rand) {

        int number = rand.nextInt(ConsoleTypes.values().length - 1);
        return switch (number) {
            case 0 -> ConsoleTypes.PlayStation4;
            case 1 -> ConsoleTypes.XBoxOneS;
            case 2 -> ConsoleTypes.PlayStation4Pro;
            case 3 -> ConsoleTypes.NintendoSwich;
            default -> ConsoleTypes.SegaGenesisMini;
        };
    }
}