package StudentGrader;
import javax.swing.* ;
import javax.swing.table.DefaultTableModel;
import java.awt.* ;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent ;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

class Window implements ActionListener {
    static JButton addStudentRecord, searchStudentRecord, displayAll, deleteStudentRecord;
    static CardLayout cardLayout;
    static JPanel p1, p2, base, parentPanel, displayPanel;
    DefaultTableModel tableModel;

// Updates to be done : add only one panel to the base panel and set the layout of that panel to gridbaglayout

    Window() throws SQLException, ClassNotFoundException {

        // create a panel p1
        p1 = new JPanel(new BorderLayout());
        p1.setSize(1000, 1000);
        // creating a label to display welcome message
        JLabel welcomeMessage = new JLabel("WELCOME TO STUDENT GRADER", SwingConstants.CENTER);
        welcomeMessage.setSize(new Dimension(500, 500));
        welcomeMessage.setFont(new Font("MV Boli", Font.BOLD, 50));


        // adding pencil image
        ImageIcon pencil = new ImageIcon("C:\\Users\\AGASTYA SHANKER\\Documents\\ADAVANCE JAVA\\BasicsOFGUI\\src\\StudentGrader\\pencilSlant.png");
        welcomeMessage.setIcon(pencil);
        welcomeMessage.setVerticalTextPosition(JLabel.TOP);
        welcomeMessage.setHorizontalTextPosition(JLabel.CENTER);

        // adding label to panel
        p1.add(welcomeMessage, BorderLayout.CENTER);

        // Creating another label to display action options along with "Choose action" text
        JLabel action = new JLabel("Choose Action : ");
        action.setSize(new Dimension(500, 500));
        action.setFont(new Font("MV Boli", Font.BOLD, 25));
        // adding button for showing student records
        addStudentRecord = new JButton("ADD STUDENT RECORD.");
        addStudentRecord.setPreferredSize(new Dimension(200, 50));
        addStudentRecord.setFocusable(false);
        addStudentRecord.addActionListener(this);

        // adding button for searching student records
        searchStudentRecord = new JButton("SEARCH STUDENT RECORD");
        searchStudentRecord.setPreferredSize(new Dimension(250, 50));
        searchStudentRecord.setFocusable(false);
        searchStudentRecord.addActionListener(this);

        // adding button for displaying all records
        displayAll = new JButton("DISPLAY ALL RECORDS");
        displayAll.setPreferredSize(new Dimension(200, 50));
        displayAll.setFocusable(false);
        displayAll.addActionListener(this);

        // Creating a button for deleting student records
        deleteStudentRecord = new JButton("DELETE STUDENT RECORD");
        deleteStudentRecord.setPreferredSize(new Dimension(250, 50));
        deleteStudentRecord.setFocusable(false);
        deleteStudentRecord.addActionListener(this);

        // Creating panel p2 to add action label and action buttons
        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(action);
        p2.add(addStudentRecord);
        p2.add(searchStudentRecord);
        p2.add(displayAll);
        p2.add(deleteStudentRecord);


        // Creating a parent panel to contain p1 and p2
        parentPanel = new JPanel(new GridLayout(2, 1));
        parentPanel.setSize(2000, 2000);
        parentPanel.add(p1);
        parentPanel.add(p2);

        // creating a card panel to contain all the cards/ windows
        base = new JPanel();
        base.setSize(500, 500);
        cardLayout = new CardLayout();
        base.setLayout(cardLayout);
        base.add(parentPanel, "home");
        new DisplayAllRecords();
        base.add(DisplayAllRecords.displayPanel, "displayAll");

        // Creating a frame
        JFrame frame = new JFrame("Student Grader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // On App exit, close the connection to DB
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    BackendDB.link.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.setLayout(new BorderLayout());
        frame.add(base, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
    // adding functionality to buttons

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentRecord) {
            //New window of student record is created
            new AddStudentRecord();

        }
        if (e.getSource() == searchStudentRecord) {
            // re-directs towards student search window
            new StudentSearcher();
            // The user could click only once
            searchStudentRecord.setEnabled(false);
            // After the student searcher frame is disposed , the button is enabled again

        }
        if (e.getSource() == displayAll) {
            // creates another window to display all records
//            System.out.println("Displaying all student records...");
            cardLayout.show(base, "displayAll");

        }
        if (e.getSource() == deleteStudentRecord) {
            new DeleteStudentRecord();
            deleteStudentRecord.setEnabled(false);
        }

    }


}
public class MainWindow {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Instantiating window
        new Window();
        BackendDB.connect() ;
    }
}
